package com.hsy.study.myproject.dbt;


import android.database.sqlite.SQLiteTransactionListener;

import com.squareup.sqldelight.Transacter;

import java.io.Closeable;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

import androidx.annotation.CheckResult;
import androidx.annotation.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * @author haosiyuan
 * @date 2019/1/15 9:25 PM
 */
public final class EndlessDatabase implements Closeable, DbAction {

    private SqlWrapper.Logger logger;

    volatile boolean logging;

    private final Subject<Set<String>> triggers = PublishSubject.create();
    /**
     * 多线程事务
     */
    final ThreadLocal<SqliteTransaction> transactions = new ThreadLocal<>();

    /**
     * rxJava判断事务
     */
    private final Consumer<Object> ensureNotInTransaction = new Consumer<Object>() {
        @Override public void accept(Object ignored) throws Exception {
            if (transactions.get() != null) {
                throw new IllegalStateException("Cannot subscribe to observable query in a transaction.");
            }
        }
    };


    public EndlessDatabase(SqlWrapper.Logger logger) {
        this.logger = logger;
    }

    public void setLoggingEnabled(boolean enabled) {
        this.logging = enabled;
    }


    @Override
    public void close() throws IOException {

    }

    @Override
    public void createQuery(Transacter dbController) {
    }

    @Override
    public void createObservableQuery(Transacter dbController) {

    }

    @Override
    public void executeUpdate(Transacter dbController) {

    }

    @Override
    public void executeInsert(Transacter dbController) {

    }

    @Override
    public void executeDelete(Transacter dbController) {

    }

    static final class SqliteTransaction extends LinkedHashSet<String> implements SQLiteTransactionListener {
        final SqliteTransaction parent;
        boolean commit;

        SqliteTransaction(SqliteTransaction parent) {
            this.parent = parent;
        }

        @Override
        public void onBegin() {
        }

        @Override
        public void onCommit() {
            commit = true;
        }

        @Override
        public void onRollback() {
        }

        @Override
        public String toString() {
            String name = String.format("%08x", System.identityHashCode(this));
            return parent == null ? name : name + " [" + parent.toString() + ']';
        }
    }
}
