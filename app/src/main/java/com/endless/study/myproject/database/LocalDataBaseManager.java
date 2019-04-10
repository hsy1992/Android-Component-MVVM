package com.endless.study.myproject.database;

import android.app.Application;
import android.os.Environment;
import android.text.TextUtils;

import com.endless.study.baselibrary.config.AppConfig;
import com.endless.study.baselibrary.database.AppDatabase;
import com.endless.study.baselibrary.database.entity.UserEntity;
import com.endless.study.baselibrary.utils.UtilApp;

import java.util.List;

import androidx.room.Room;
import androidx.room.migration.Migration;

/**
 * 登录成功时调用
 * 更新后需要调用 更新数据库结构
 * @author haosiyuan
 * @date 2019/4/4 5:09 PM
 */
public class LocalDataBaseManager {

    /**
     * 是否初始化
     */
    private static volatile boolean isInitialization = false;
    /**
     * 文件地址
     */
    private String filePah = Environment.getExternalStorageState();

    private LocalDataBase localDataBase;

    private static class Instance {
        private static LocalDataBaseManager manager = new LocalDataBaseManager();
    }

    public static LocalDataBaseManager getInstance() {
        return Instance.manager;
    }

    public synchronized void build(Application application, AppDatabase appDatabase, String userId) {

        if (isInitialization) {
            throw new RuntimeException("LocalDataBase is already Initialization");
        }

        if (TextUtils.isEmpty(userId)) {
            throw new RuntimeException("LocalDataBaseManager build userId can not be null");
        }

        //登录成功 插入系统数据库登录状态
        List<UserEntity> users = appDatabase.userDao().loadAllUsers();

        //将之前用户至为未登录
        appDatabase.beginTransaction();
        for (UserEntity user : users) {
            user.isLogin = UserEntity.UN_LOGIN;
            appDatabase.userDao().updateUsers(user);
        }

        appDatabase.userDao()
                .insertUsers(new UserEntity(userId, "","", UtilApp.getVersionName(application), UserEntity.LOGINED));

        appDatabase.endTransaction();

        //解析XML获取数据库更新

        Migration[] migrations = new Migration[1];
        localDataBase = Room.databaseBuilder(application, LocalDataBase.class,filePah + "/" + userId + "/" + AppConfig.DATABASE_NAME)
                            .allowMainThreadQueries()
                            .addMigrations(migrations)
                            .build();
        isInitialization = true;
    }

    /**
     * 获得数据库
     * @return
     */
    public LocalDataBase getDatabase() {

        if (localDataBase == null || !isInitialization) {
            throw new RuntimeException("LocalDataBase == null");
        }

        return localDataBase;
    }

}
