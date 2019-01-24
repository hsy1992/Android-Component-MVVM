package com.hsy.study.myproject.db;

import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

/**
 * @author haosiyuan
 * @date 2019/1/6 下午7:52
 */
public class DbCallBack extends SupportSQLiteOpenHelper.Callback {

    private static final int VERSION = 1;

    /**
     * Creates a new Callback to get database lifecycle events.
     *
     */
    public DbCallBack() {
        super(VERSION);
    }

    @Override
    public void onCreate(SupportSQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SupportSQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
