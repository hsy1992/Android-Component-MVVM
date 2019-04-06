package com.endless.study.myproject.database;

import com.endless.study.myproject.database.dao.TestDao;
import com.endless.study.myproject.database.entity.Test;

import androidx.room.Database;
import androidx.room.RoomDatabase;

/**
 * 应用 登录用户的数据库
 * @author haosiyuan
 * @date 2019/3/22 11:43 AM
 */
@Database(entities = Test.class, version = 1, exportSchema = false)
public abstract class LocalDataBase extends RoomDatabase {

    public abstract TestDao getTestDao();

}
