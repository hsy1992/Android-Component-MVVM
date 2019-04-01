package com.endless.study.baselibrary.mvvm.viewmodel;

import android.app.Application;

import com.endless.study.baselibrary.base.delegate.IViewModel;
import com.endless.study.baselibrary.cache.local.ICache;
import com.endless.study.baselibrary.cache.local.DefaultCacheType;
import com.endless.study.baselibrary.mvvm.model.IModel;
import com.endless.study.baselibrary.mvvm.view.IView;
import com.endless.study.baselibrary.utils.UtilCommon;
import com.endless.study.baselibrary.utils.UtilPreconditions;


import javax.inject.Inject;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModel;

/**
 * {@link ViewModel} 与页面不一样的生命周期
 * @author haosiyuan
 * @date 2019/1/28 5:09 PM
 */
public abstract class BaseViewModel<M extends IModel> extends AndroidViewModel implements IViewModel,
        LifecycleObserver {

    protected final String TAG = this.getClass().getSimpleName();
    private ICache<String, Object> cache;

    /**
     * 数据接口
     */
    protected M model;

    @Inject
    protected Application mApplication;

    public BaseViewModel(Application mApplication, M model) {
        super(mApplication);
        UtilPreconditions.checkNotNull(model, "%s cannot be null", IModel.class.getName());
        this.model = model;
    }

    @Override
    public ICache<String, Object> provideCache() {
        if (cache == null) {
            cache = UtilCommon.getAppComponent(mApplication).cacheFactory().build(new DefaultCacheType());
        }
        return cache;
    }

    /**
     * rxJava 生命周期绑定
     */
    @Override
    public void bindLifecycle() {
        UtilCommon.getAppComponent(mApplication).observer().bindLifecycle();
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
    }

}
