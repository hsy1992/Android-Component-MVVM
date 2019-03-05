package com.hsy.study.baselibrary.viewmodel;

import android.app.Application;

import com.hsy.study.baselibrary.base.delegate.IActivity;
import com.hsy.study.baselibrary.base.delegate.IViewModel;
import com.hsy.study.baselibrary.cache.ICache;
import com.hsy.study.baselibrary.cache.DefaultCacheType;
import com.hsy.study.baselibrary.ui.IView;
import com.hsy.study.baselibrary.utils.CommonUtil;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

/**
 * @author haosiyuan
 * @date 2019/1/28 5:09 PM
 */
public abstract class BaseViewModel<V extends IView> extends AndroidViewModel implements IViewModel,
        LifecycleObserver {

    protected final String TAG = this.getClass().getSimpleName();
    private ICache<String, Object> cache;
    protected Application mApplication;

    /**
     * 视图控制接口
     */
    protected V rootView;

    public BaseViewModel(@NonNull Application application) {
        super(application);
        this.mApplication = application;
    }

    @Override
    public void setRootView(IView iView) {
        this.rootView = (V) iView;
        //绑定view的生命周期
        if (rootView != null && rootView instanceof LifecycleOwner) {
            ((LifecycleOwner)rootView).getLifecycle().addObserver(this);
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
