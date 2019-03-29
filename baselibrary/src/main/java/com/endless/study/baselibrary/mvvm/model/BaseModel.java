package com.endless.study.baselibrary.mvvm.model;

import android.app.Application;

import com.endless.study.baselibrary.common.executor.AppExecutors;
import com.endless.study.baselibrary.config.AppConfig;
import com.endless.study.baselibrary.database.AppDatabase;
import com.endless.study.baselibrary.utils.UtilCommon;
import com.endless.study.baselibrary.repository.IRepositoryManager;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

/**
 * @author haosiyuan
 * @date 2019/3/4 5:18 PM
 */
public abstract class BaseModel implements IModel, LifecycleObserver {

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
    /**
     * 线程池
     */
    protected AppExecutors appExecutors;

    public BaseModel(IRepositoryManager mRepositoryManager, Application mApplication) {
        this.mRepositoryManager = mRepositoryManager;
        this.mAppDatabase = UtilCommon.getAppComponent(mApplication).getAppDatabase();
        this.appExecutors = UtilCommon.getAppComponent(mApplication).getAppExecutors();
    }

    @Override
    public void onDestroy() {
        mRepositoryManager = null;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onDestroy(LifecycleOwner owner) {
        owner.getLifecycle().removeObserver(this);
    }
}
