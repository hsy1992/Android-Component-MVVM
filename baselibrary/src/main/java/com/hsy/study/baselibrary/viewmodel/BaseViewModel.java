package com.hsy.study.baselibrary.viewmodel;

import android.app.Application;

import com.hsy.study.baselibrary.base.delegate.IViewModel;
import com.hsy.study.baselibrary.cache.ICache;
import com.hsy.study.baselibrary.cache.DefaultCacheType;
import com.hsy.study.baselibrary.model.IModel;
import com.hsy.study.baselibrary.ui.IView;
import com.hsy.study.baselibrary.utils.CommonUtil;
import com.hsy.study.baselibrary.utils.Preconditions;


import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModel;

/**
 * @author haosiyuan
 * @date 2019/1/28 5:09 PM
 */
public abstract class BaseViewModel<V extends IView, M extends IModel> extends ViewModel implements IViewModel,
        LifecycleObserver {

    protected final String TAG = this.getClass().getSimpleName();
    private ICache<String, Object> cache;
    protected Application mApplication;

    /**
     * 视图控制接口
     */
    protected V rootView;
    /**
     * 数据接口
     */
    protected M model;

    /**
     * 不需要数据的页面
     * @param application
     * @param rootView
     */
    public BaseViewModel(Application application, V rootView) {
        this.mApplication = application;
        this.rootView = rootView;
        bindLifecycleObserver();
    }

    public BaseViewModel(Application application, V rootView, M model) {
        Preconditions.checkNotNull(model, "%s cannot be null", IModel.class.getName());
        Preconditions.checkNotNull(rootView, "%s cannot be null", IView.class.getName());
        this.mApplication = application;
        this.rootView = rootView;
        this.model = model;
    }

    public void bindLifecycleObserver() {
        //绑定view的生命周期
        if (rootView != null && rootView instanceof LifecycleOwner) {
            ((LifecycleOwner)rootView).getLifecycle().addObserver(this);
            //为model绑定声明周期
            if (model != null && model instanceof LifecycleOwner) {
                ((LifecycleOwner)rootView).getLifecycle().addObserver((LifecycleObserver) model);
            }
        }
    }

    @Override
    public ICache<String, Object> provideCache() {
        if (cache == null) {
            cache = CommonUtil.getAppComponent(mApplication).cacheFactory().build(new DefaultCacheType());
        }
        return cache;
    }

    /**
     * rxJava 生命周期绑定
     */
    @Override
    public void bindLifecycle() {
        CommonUtil.getAppComponent(mApplication).observer().bindLifecycle();
    }

    /**
     * 清除
     */
    @Override
    protected void onCleared() {
        if (cache != null) {
            cache.clear();
            cache = null;
        }
        mApplication = null;
        rootView = null;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onDestroy() {
        ((LifecycleOwner)rootView).getLifecycle().removeObserver(this);
    }
}
