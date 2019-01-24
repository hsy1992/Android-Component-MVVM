/*
 * Copyright (C) 2015 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hsy.study.myproject.test;

import android.content.ContentResolver;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import androidx.annotation.CheckResult;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Arrays;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableTransformer;
import io.reactivex.Scheduler;
import io.reactivex.functions.Cancellable;

import static com.hsy.study.myproject.test.QueryObservable.QUERY_OBSERVABLE;
import static java.lang.System.nanoTime;
import static java.util.concurrent.TimeUnit.NANOSECONDS;

/**
 * A lightweight wrapper around {@link ContentResolver} which allows for continuously observing
 * the result of a query. Create using a {@link SqlBrite} instance.
 */
public final class BriteContentResolver {
  final Handler contentObserverHandler = new Handler(Looper.getMainLooper());

  final ContentResolver contentResolver;
  private final SqlBrite.Logger logger;
  private final Scheduler scheduler;
  private final ObservableTransformer<SqlBrite.Query, SqlBrite.Query> queryTransformer;

  volatile boolean logging;

  BriteContentResolver(ContentResolver contentResolver, SqlBrite.Logger logger, Scheduler scheduler,
                       ObservableTransformer<SqlBrite.Query, SqlBrite.Query> queryTransformer) {
    this.contentResolver = contentResolver;
    this.logger = logger;
    this.scheduler = scheduler;
    this.queryTransformer = queryTransformer;
  }

  /** Control whether debug logging is enabled. */
  public void setLoggingEnabled(boolean enabled) {
    logging = enabled;
  }

  @CheckResult @NonNull
  public QueryObservable createQuery(@NonNull final Uri uri, @Nullable final String[] projection,
                                     @Nullable final String selection, @Nullable final String[] selectionArgs, @Nullable
      final String sortOrder, final boolean notifyForDescendents) {
    final SqlBrite.Query query = new SqlBrite.Query() {
      @Override public Cursor run() {
        long startNanos = nanoTime();
        Cursor cursor = contentResolver.query(uri, projection, selection, selectionArgs, sortOrder);

        if (logging) {
          long tookMillis = NANOSECONDS.toMillis(nanoTime() - startNanos);
          log("QUERY (%sms)\n  uri: %s\n  projection: %s\n  selection: %s\n  selectionArgs: %s\n  "
                  + "sortOrder: %s\n  notifyForDescendents: %s", tookMillis, uri,
              Arrays.toString(projection), selection, Arrays.toString(selectionArgs), sortOrder,
              notifyForDescendents);
        }

        return cursor;
      }
    };
    Observable<SqlBrite.Query> queries = Observable.create(new ObservableOnSubscribe<SqlBrite.Query>() {
      @Override public void subscribe(final ObservableEmitter<SqlBrite.Query> e) throws Exception {
        final ContentObserver observer = new ContentObserver(contentObserverHandler) {
          @Override public void onChange(boolean selfChange) {
            if (!e.isDisposed()) {
              e.onNext(query);
            }
          }
        };
        contentResolver.registerContentObserver(uri, notifyForDescendents, observer);
        e.setCancellable(new Cancellable() {
          @Override public void cancel() throws Exception {
            contentResolver.unregisterContentObserver(observer);
          }
        });

        if (!e.isDisposed()) {
          e.onNext(query); // Trigger initial query.
        }
      }
    });
    return queries //
        .observeOn(scheduler) //
        .compose(queryTransformer) // Apply the user's query transformer.
        .to(QUERY_OBSERVABLE);
  }

  void log(String message, Object... args) {
    if (args.length > 0) message = String.format(message, args);
    logger.log(message);
  }
}
