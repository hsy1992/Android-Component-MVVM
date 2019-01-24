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

import androidx.sqlite.db.SimpleSQLiteQuery;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteQuery;
import androidx.sqlite.db.SupportSQLiteStatement;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteTransactionListener;
import androidx.annotation.CheckResult;
import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;

import java.io.Closeable;
import java.lang.annotation.Retention;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import io.reactivex.ObservableTransformer;
import io.reactivex.Scheduler;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

import static android.database.sqlite.SQLiteDatabase.CONFLICT_ABORT;
import static android.database.sqlite.SQLiteDatabase.CONFLICT_FAIL;
import static android.database.sqlite.SQLiteDatabase.CONFLICT_IGNORE;
import static android.database.sqlite.SQLiteDatabase.CONFLICT_NONE;
import static android.database.sqlite.SQLiteDatabase.CONFLICT_REPLACE;
import static android.database.sqlite.SQLiteDatabase.CONFLICT_ROLLBACK;
import static com.hsy.study.myproject.test.QueryObservable.QUERY_OBSERVABLE;
import static java.lang.annotation.RetentionPolicy.SOURCE;
import static java.util.Collections.singletonList;

/**
 * A lightweight wrapper around {@link SupportSQLiteOpenHelper} which allows for continuously
 * observing the result of a query. Create using a {@link SqlBrite} instance.
 */
public final class BriteDatabase implements Closeable {
  private final SupportSQLiteOpenHelper helper;
  private final SqlBrite.Logger logger;
  private final ObservableTransformer<SqlBrite.Query, SqlBrite.Query> queryTransformer;

  // Package-private to avoid synthetic accessor method for 'transaction' instance.
  final ThreadLocal<SqliteTransaction> transactions = new ThreadLocal<>();
  private final Subject<Set<String>> triggers = PublishSubject.create();

  private final Transaction transaction = new Transaction() {
    @Override public void markSuccessful() {
      if (logging) log("TXN SUCCESS %s", transactions.get());
      getWritableDatabase().setTransactionSuccessful();
    }

    @Override public boolean yieldIfContendedSafely() {
      return getWritableDatabase().yieldIfContendedSafely();
    }

    @Override public boolean yieldIfContendedSafely(long sleepAmount, TimeUnit sleepUnit) {
      return getWritableDatabase().yieldIfContendedSafely(sleepUnit.toMillis(sleepAmount));
    }

    @Override public void end() {
      SqliteTransaction transaction = transactions.get();
      if (transaction == null) {
        throw new IllegalStateException("Not in transaction.");
      }
      SqliteTransaction newTransaction = transaction.parent;
      transactions.set(newTransaction);
      if (logging) log("TXN END %s", transaction);
      getWritableDatabase().endTransaction();
      // Send the triggers after ending the transaction in the DB.
      if (transaction.commit) {
        sendTableTrigger(transaction);
      }
    }

    @Override public void close() {
      end();
    }
  };
  private final Consumer<Object> ensureNotInTransaction = new Consumer<Object>() {
    @Override public void accept(Object ignored) throws Exception {
      if (transactions.get() != null) {
        throw new IllegalStateException("Cannot subscribe to observable query in a transaction.");
      }
    }
  };

  private final Scheduler scheduler;

  // Package-private to avoid synthetic accessor method for 'transaction' instance.
  volatile boolean logging;

  BriteDatabase(SupportSQLiteOpenHelper helper, SqlBrite.Logger logger, Scheduler scheduler,
                ObservableTransformer<SqlBrite.Query, SqlBrite.Query> queryTransformer) {
    this.helper = helper;
    this.logger = logger;
    this.scheduler = scheduler;
    this.queryTransformer = queryTransformer;
  }

  public void setLoggingEnabled(boolean enabled) {
    logging = enabled;
  }

  @NonNull @CheckResult @WorkerThread
  public SupportSQLiteDatabase getReadableDatabase() {
    return helper.getReadableDatabase();
  }

  @NonNull @CheckResult @WorkerThread
  public SupportSQLiteDatabase getWritableDatabase() {
    return helper.getWritableDatabase();
  }

  void sendTableTrigger(Set<String> tables) {
    SqliteTransaction transaction = transactions.get();
    if (transaction != null) {
      transaction.addAll(tables);
    } else {
      if (logging) log("TRIGGER %s", tables);
      triggers.onNext(tables);
    }
  }

  @CheckResult @NonNull
  public Transaction newTransaction() {
    SqliteTransaction transaction = new SqliteTransaction(transactions.get());
    transactions.set(transaction);
    if (logging) log("TXN BEGIN %s", transaction);
    getWritableDatabase().beginTransactionWithListener(transaction);

    return this.transaction;
  }

  @CheckResult @NonNull
  public Transaction newNonExclusiveTransaction() {
    SqliteTransaction transaction = new SqliteTransaction(transactions.get());
    transactions.set(transaction);
    if (logging) log("TXN BEGIN %s", transaction);
    getWritableDatabase().beginTransactionWithListenerNonExclusive(transaction);

    return this.transaction;
  }

  @Override public void close() {
    helper.close();
  }

  @CheckResult @NonNull
  public QueryObservable createQuery(@NonNull final String table, @NonNull String sql,
                                     @NonNull Object... args) {
    return createQuery(new DatabaseQuery(singletonList(table), new SimpleSQLiteQuery(sql, args)));
  }

  @CheckResult @NonNull
  public QueryObservable createQuery(@NonNull final Iterable<String> tables, @NonNull String sql,
                                     @NonNull Object... args) {
    return createQuery(new DatabaseQuery(tables, new SimpleSQLiteQuery(sql, args)));
  }

  @CheckResult @NonNull
  public QueryObservable createQuery(@NonNull final String table,
                                     @NonNull SupportSQLiteQuery query) {
    return createQuery(new DatabaseQuery(singletonList(table), query));
  }

  /**
   * See {@link #createQuery(String, SupportSQLiteQuery)} for usage. This overload allows for
   * monitoring multiple tables for changes.
   *
   * @see SupportSQLiteDatabase#query(SupportSQLiteQuery)
   */
  @CheckResult @NonNull
  public QueryObservable createQuery(@NonNull final Iterable<String> tables,
                                     @NonNull SupportSQLiteQuery query) {
    return createQuery(new DatabaseQuery(tables, query));
  }

  @CheckResult @NonNull
  private QueryObservable createQuery(DatabaseQuery query) {
    if (transactions.get() != null) {
      throw new IllegalStateException("Cannot create observable query in transaction. "
          + "Use query() for a query inside a transaction.");
    }

    return triggers //
        .filter(query) // DatabaseQuery filters triggers to on tables we care about.
        .map(query) // DatabaseQuery maps to itself to save an allocation.
        .startWith(query) //
        .observeOn(scheduler) //
        .compose(queryTransformer) // Apply the user's query transformer.
        .doOnSubscribe(ensureNotInTransaction)
        .to(QUERY_OBSERVABLE);
  }

  @CheckResult @WorkerThread
  public Cursor query(@NonNull String sql, @NonNull Object... args) {
    Cursor cursor = getReadableDatabase().query(sql, args);
    if (logging) {
      log("QUERY\n  sql: %s\n  args: %s", indentSql(sql), Arrays.toString(args));
    }

    return cursor;
  }

  @CheckResult @WorkerThread
  public Cursor query(@NonNull SupportSQLiteQuery query) {
    Cursor cursor = getReadableDatabase().query(query);
    if (logging) {
      log("QUERY\n  sql: %s", indentSql(query.getSql()));
    }

    return cursor;
  }

  @WorkerThread
  public long insert(@NonNull String table, @ConflictAlgorithm int conflictAlgorithm,
      @NonNull ContentValues values) {
    SupportSQLiteDatabase db = getWritableDatabase();

    if (logging) {
      log("INSERT\n  table: %s\n  values: %s\n  conflictAlgorithm: %s", table, values,
          conflictString(conflictAlgorithm));
    }
    long rowId = db.insert(table, conflictAlgorithm, values);

    if (logging) log("INSERT id: %s", rowId);

    if (rowId != -1) {
      // Only send a table trigger if the insert was successful.
      sendTableTrigger(Collections.singleton(table));
    }
    return rowId;
  }

  @WorkerThread
  public int delete(@NonNull String table, @Nullable String whereClause,
      @Nullable String... whereArgs) {
    SupportSQLiteDatabase db = getWritableDatabase();

    if (logging) {
      log("DELETE\n  table: %s\n  whereClause: %s\n  whereArgs: %s", table, whereClause,
          Arrays.toString(whereArgs));
    }
    int rows = db.delete(table, whereClause, whereArgs);

    if (logging) log("DELETE affected %s %s", rows, rows != 1 ? "rows" : "row");

    if (rows > 0) {
      // Only send a table trigger if rows were affected.
      sendTableTrigger(Collections.singleton(table));
    }
    return rows;
  }

  @WorkerThread
  public int update(@NonNull String table, @ConflictAlgorithm int conflictAlgorithm,
      @NonNull ContentValues values, @Nullable String whereClause, @Nullable String... whereArgs) {
    SupportSQLiteDatabase db = getWritableDatabase();

    if (logging) {
      log("UPDATE\n  table: %s\n  values: %s\n  whereClause: %s\n  whereArgs: %s\n  conflictAlgorithm: %s",
          table, values, whereClause, Arrays.toString(whereArgs),
          conflictString(conflictAlgorithm));
    }
    int rows = db.update(table, conflictAlgorithm, values, whereClause, whereArgs);

    if (logging) log("UPDATE affected %s %s", rows, rows != 1 ? "rows" : "row");

    if (rows > 0) {
      // Only send a table trigger if rows were affected.
      sendTableTrigger(Collections.singleton(table));
    }
    return rows;
  }

  @WorkerThread
  public void execute(String sql) {
    if (logging) log("EXECUTE\n  sql: %s", indentSql(sql));

    getWritableDatabase().execSQL(sql);
  }

  @WorkerThread
  public void execute(String sql, Object... args) {
    if (logging) log("EXECUTE\n  sql: %s\n  args: %s", indentSql(sql), Arrays.toString(args));

    getWritableDatabase().execSQL(sql, args);
  }

  @WorkerThread
  public void executeAndTrigger(String table, String sql) {
    executeAndTrigger(Collections.singleton(table), sql);
  }

  @WorkerThread
  public void executeAndTrigger(Set<String> tables, String sql) {
    execute(sql);

    sendTableTrigger(tables);
  }

  @WorkerThread
  public void executeAndTrigger(String table, String sql, Object... args) {
    executeAndTrigger(Collections.singleton(table), sql, args);
  }

  @WorkerThread
  public void executeAndTrigger(Set<String> tables, String sql, Object... args) {
    execute(sql, args);

    sendTableTrigger(tables);
  }

  @WorkerThread
  public int executeUpdateDelete(String table, SupportSQLiteStatement statement) {
    return executeUpdateDelete(Collections.singleton(table), statement);
  }

  @WorkerThread
  public int executeUpdateDelete(Set<String> tables, SupportSQLiteStatement statement) {
    if (logging) log("EXECUTE\n %s", statement);

    int rows = statement.executeUpdateDelete();
    if (rows > 0) {
      // Only send a table trigger if rows were affected.
      sendTableTrigger(tables);
    }
    return rows;
  }

  @WorkerThread
  public long executeInsert(String table, SupportSQLiteStatement statement) {
    return executeInsert(Collections.singleton(table), statement);
  }

  @WorkerThread
  public long executeInsert(Set<String> tables, SupportSQLiteStatement statement) {
    if (logging) log("EXECUTE\n %s", statement);

    long rowId = statement.executeInsert();
    if (rowId != -1) {
      // Only send a table trigger if the insert was successful.
      sendTableTrigger(tables);
    }
    return rowId;
  }

  public interface Transaction extends Closeable {

    @WorkerThread
    void end();

    @WorkerThread
    void markSuccessful();

    @WorkerThread
    boolean yieldIfContendedSafely();

    @WorkerThread
    boolean yieldIfContendedSafely(long sleepAmount, TimeUnit sleepUnit);

    /**
     * Equivalent to calling {@link #end()}
     */
    @WorkerThread
    @Override void close();
  }

  @IntDef({
      CONFLICT_ABORT,
      CONFLICT_FAIL,
      CONFLICT_IGNORE,
      CONFLICT_NONE,
      CONFLICT_REPLACE,
      CONFLICT_ROLLBACK
  })
  @Retention(SOURCE)
  private @interface ConflictAlgorithm {
  }

  static String indentSql(String sql) {
    return sql.replace("\n", "\n       ");
  }

  void log(String message, Object... args) {
    if (args.length > 0) message = String.format(message, args);
    logger.log(message);
  }

  private static String conflictString(@ConflictAlgorithm int conflictAlgorithm) {
    switch (conflictAlgorithm) {
      case CONFLICT_ABORT:
        return "abort";
      case CONFLICT_FAIL:
        return "fail";
      case CONFLICT_IGNORE:
        return "ignore";
      case CONFLICT_NONE:
        return "none";
      case CONFLICT_REPLACE:
        return "replace";
      case CONFLICT_ROLLBACK:
        return "rollback";
      default:
        return "unknown (" + conflictAlgorithm + ')';
    }
  }

  static final class SqliteTransaction extends LinkedHashSet<String>
      implements SQLiteTransactionListener {
    final SqliteTransaction parent;
    boolean commit;

    SqliteTransaction(SqliteTransaction parent) {
      this.parent = parent;
    }

    @Override public void onBegin() {
    }

    @Override public void onCommit() {
      commit = true;
    }

    @Override public void onRollback() {
    }

    @Override public String toString() {
      String name = String.format("%08x", System.identityHashCode(this));
      return parent == null ? name : name + " [" + parent.toString() + ']';
    }
  }

  final class DatabaseQuery extends SqlBrite.Query
      implements Function<Set<String>, SqlBrite.Query>, Predicate<Set<String>> {
    private final Iterable<String> tables;
    private final SupportSQLiteQuery query;

    DatabaseQuery(Iterable<String> tables, SupportSQLiteQuery query) {
      this.tables = tables;
      this.query = query;
    }

    @Override public Cursor run() {
      if (transactions.get() != null) {
        throw new IllegalStateException("Cannot execute observable query in a transaction.");
      }

      Cursor cursor = getReadableDatabase().query(query);

      if (logging) {
        log("QUERY\n  tables: %s\n  sql: %s", tables, indentSql(query.getSql()));
      }

      return cursor;
    }

    @Override public String toString() {
      return query.getSql();
    }

    @Override public SqlBrite.Query apply(Set<String> ignored) {
      return this;
    }

    @Override public boolean test(Set<String> strings) {
      for (String table : tables) {
        if (strings.contains(table)) {
          return true;
        }
      }
      return false;
    }
  }
}
