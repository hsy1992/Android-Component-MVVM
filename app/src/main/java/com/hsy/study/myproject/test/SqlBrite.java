package com.hsy.study.myproject.test;

import androidx.sqlite.db.SupportSQLiteOpenHelper;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Build;
import androidx.annotation.CheckResult;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.WorkerThread;
import android.util.Log;

import java.util.List;
import java.util.Optional;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableOperator;
import io.reactivex.ObservableTransformer;
import io.reactivex.Scheduler;
import io.reactivex.functions.Function;

/**
 * @author haosiyuan
 * @date 2019/1/14 8:45 PM
 */
public class SqlBrite {
    static final Logger DEFAULT_LOGGER = new Logger() {
        @Override public void log(String message) {
            Log.d("SqlBrite", message);
        }
    };
    static final ObservableTransformer<Query, Query> DEFAULT_TRANSFORMER =
            new ObservableTransformer<Query, Query>() {
                @Override public Observable<Query> apply(Observable<Query> queryObservable) {
                    return queryObservable;
                }
            };

    public static final class Builder {
        private Logger logger = DEFAULT_LOGGER;
        private ObservableTransformer<Query, Query> queryTransformer = DEFAULT_TRANSFORMER;

        @CheckResult
        public Builder logger(@NonNull Logger logger) {
            if (logger == null) throw new NullPointerException("logger == null");
            this.logger = logger;
            return this;
        }

        @CheckResult
        public Builder queryTransformer(@NonNull ObservableTransformer<Query, Query> queryTransformer) {
            if (queryTransformer == null) throw new NullPointerException("queryTransformer == null");
            this.queryTransformer = queryTransformer;
            return this;
        }

        @CheckResult
        public SqlBrite build() {
            return new SqlBrite(logger, queryTransformer);
        }
    }

    final Logger logger;
    final ObservableTransformer<Query, Query> queryTransformer;

    SqlBrite(@NonNull Logger logger, @NonNull ObservableTransformer<Query, Query> queryTransformer) {
        this.logger = logger;
        this.queryTransformer = queryTransformer;
    }

    @CheckResult @NonNull public BriteDatabase wrapDatabaseHelper(
            @NonNull SupportSQLiteOpenHelper helper,
            @NonNull Scheduler scheduler) {
        return new BriteDatabase(helper, logger, scheduler, queryTransformer);
    }

    /**
     * Wrap a {@link ContentResolver} for observable queries.
     *
     * @param scheduler The {@link Scheduler} on which items from
     * {@link BriteContentResolver#createQuery} will be emitted.
     */
    @CheckResult @NonNull public BriteContentResolver wrapContentProvider(
            @NonNull ContentResolver contentResolver, @NonNull Scheduler scheduler) {
        return new BriteContentResolver(contentResolver, logger, scheduler, queryTransformer);
    }

    /** An executable query. */
    public static abstract class Query {

        @CheckResult @NonNull //
        public static <T> ObservableOperator<T, Query> mapToOne(@NonNull Function<Cursor, T> mapper) {
            return new QueryToOneOperator<>(mapper, null);
        }

        @SuppressWarnings("ConstantConditions") // Public API contract.
        @CheckResult @NonNull
        public static <T> ObservableOperator<T, Query> mapToOneOrDefault(
                @NonNull Function<Cursor, T> mapper, @NonNull T defaultValue) {
            if (defaultValue == null) throw new NullPointerException("defaultValue == null");
            return new QueryToOneOperator<>(mapper, defaultValue);
        }

        @RequiresApi(Build.VERSION_CODES.N) //
        @CheckResult @NonNull //
        public static <T> ObservableOperator<Optional<T>, Query> mapToOptional(
                @NonNull Function<Cursor, T> mapper) {
            return new QueryToOptionalOperator<>(mapper);
        }

        @CheckResult @NonNull
        public static <T> ObservableOperator<List<T>, Query> mapToList(
                @NonNull Function<Cursor, T> mapper) {
            return new QueryToListOperator<>(mapper);
        }

        @CheckResult @WorkerThread
        @Nullable
        public abstract Cursor run();

        @CheckResult
        @NonNull
        public final <T> Observable<T> asRows(final Function<Cursor, T> mapper) {
            return Observable.create(new ObservableOnSubscribe<T>() {
                @Override public void subscribe(ObservableEmitter<T> e) throws Exception {
                    Cursor cursor = run();
                    if (cursor != null) {
                        try {
                            while (cursor.moveToNext() && !e.isDisposed()) {
                                e.onNext(mapper.apply(cursor));
                            }
                        } finally {
                            cursor.close();
                        }
                    }
                    if (!e.isDisposed()) {
                        e.onComplete();
                    }
                }
            });
        }
    }

    public interface Logger {
        void log(String message);
    }
}
