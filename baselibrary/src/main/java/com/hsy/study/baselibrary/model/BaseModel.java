package com.hsy.study.baselibrary.model;

import android.app.Application;

import com.hsy.study.baselibrary.config.AppConfig;
import com.hsy.study.baselibrary.database.AppDatabase;
import com.hsy.study.baselibrary.database.dao.UserDao;
import com.hsy.study.baselibrary.database.entity.User;
import com.hsy.study.baselibrary.utils.CommonUtil;
import com.hsy.study.baselibrary.viewmodel.IRepositoryManager;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author haosiyuan
 * @date 2019/3/4 5:18 PM
 */
public abstract class BaseModel implements IModel {

    /**
     * 仓库
     */
    protected IRepositoryManager mRepositoryManager;
    /**
     * 默认页码
     */
    protected int pageSize = AppConfig.PAGE_SIZE;
    /**
     * 数据库
     */
    protected AppDatabase mAppDatabase;

    public BaseModel(IRepositoryManager mRepositoryManager, Application mApplication) {
        this.mRepositoryManager = mRepositoryManager;
        this.mAppDatabase = CommonUtil.getAppComponent(mApplication).getAppDatabase();
    }

    protected <T> T getData(String url) {
        //TODO 获取数据库 数据库 -》 HTTP请求 -》数据库 -》更新UI    缓存-》HTTP -》数据库 -》更新UI

        mAppDatabase.userDao()
                .loadAllUsersByRxJava()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<User>>() {
                    @Override
                    public void accept(List<User> users) throws Exception {
                        if (users.size() > 0) {

                        }
                    }
                });


//        UserDao RetrofitService CacheService




        return null;
    }


    @Override
    public void onDestroy() {
        mRepositoryManager = null;
    }

}
