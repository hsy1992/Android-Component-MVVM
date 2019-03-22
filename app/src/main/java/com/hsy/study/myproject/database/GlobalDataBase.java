package com.hsy.study.myproject.database;

import android.os.Environment;

import com.hsy.study.myproject.database.dao.TestDao;
import com.hsy.study.myproject.database.entity.Test;

import androidx.room.Database;
import androidx.room.RoomDatabase;

/**
 * 应用层数据库
 * @author haosiyuan
 * @date 2019/3/22 11:43 AM
 */
@Database(entities = Test.class, version = 1, exportSchema = false)
public abstract class GlobalDataBase extends RoomDatabase {

    public String filePaht = Environment.getExternalStorageState();

    public abstract TestDao getTestDao();

}
