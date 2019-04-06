package com.endless.study.baselibrary.database;

import com.endless.study.baselibrary.database.dao.UserDao;
import com.endless.study.baselibrary.database.entity.SystemUser;

import androidx.room.Database;
import androidx.room.RoomDatabase;

/**
 * 数据库
 * @author haosiyuan
 * @date 2019/3/5 10:46 AM
 */
@Database(entities = {SystemUser.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    /**
     * 用户表
     * @return
     */
    public abstract UserDao userDao();
}
