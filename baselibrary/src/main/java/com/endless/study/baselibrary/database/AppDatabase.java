package com.endless.study.baselibrary.database;

import com.endless.study.baselibrary.database.dao.DownloadDao;
import com.endless.study.baselibrary.database.dao.UserDao;
import com.endless.study.baselibrary.database.entity.DownloadEntity;
import com.endless.study.baselibrary.database.entity.UserEntity;

import androidx.room.Database;
import androidx.room.RoomDatabase;

/**
 * 数据库
 * @author haosiyuan
 * @date 2019/3/5 10:46 AM
 */
@Database(entities = {UserEntity.class, DownloadEntity.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    /**
     * 用户表
     * @return
     */
    public abstract UserDao userDao();

    /**
     * 下载表
     * @return
     */
    public abstract DownloadDao download();
}
