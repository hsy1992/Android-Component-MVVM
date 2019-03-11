package com.hsy.study.baselibrary.database;

import com.hsy.study.baselibrary.database.dao.UserDao;
import com.hsy.study.baselibrary.database.entity.User;

import androidx.room.Database;
import androidx.room.RoomDatabase;

/**
 * 数据库
 * @author haosiyuan
 * @date 2019/3/5 10:46 AM
 */
@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    /**
     * 用户表
     * @return
     */
    public abstract UserDao userDao();
}
