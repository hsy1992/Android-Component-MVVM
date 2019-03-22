package com.hsy.study.baselibrary.base;

import android.content.Context;

import com.hsy.study.baselibrary.base.delegate.IActivity;
import com.hsy.study.baselibrary.cache.local.ICache;
import com.hsy.study.baselibrary.cache.local.DefaultCacheType;
import com.hsy.study.baselibrary.mvvm.view.IView;
import com.hsy.study.baselibrary.utils.UtilCommon;
import com.hsy.study.baselibrary.mvvm.viewmodel.BaseViewModel;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

/**
 * {@link AppCompatActivity } 基类
 * @author haosiyuan
 * @date 2019/2/19 2:19 PM
 */
public abstract class BaseActivity<M extends BaseViewModel> extends AppCompatActivity implements IActivity, IView {

    protected final String TAG = this.getClass().getSimpleName();

    private ICache<String, Object> mCache;

    @Nullable
    protected M viewModel;

    @Inject
    protected ViewModelProvider.Factory viewModelFactory;

    @NonNull
    @Override
    public ICache<String, Object> getCacheData() {
        if (mCache == null) {
            mCache = UtilCommon.getAppComponent(this).cacheFactory().build(new DefaultCacheType());
        }
        return mCache;
    }

    @Override
    public void showToast(String message) {
        UtilCommon.getAppComponent(this).toast().toast(this, message,false);
    }

    @Override
    public Context getContext() {
        return this;
    }
}
