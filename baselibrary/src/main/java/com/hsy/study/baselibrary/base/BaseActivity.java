package com.hsy.study.baselibrary.base;

import com.hsy.study.baselibrary.base.delegate.IActivity;
import com.hsy.study.baselibrary.cache.ICache;
import com.hsy.study.baselibrary.cache.DefaultCacheType;
import com.hsy.study.baselibrary.cache.ICacheType;
import com.hsy.study.baselibrary.utils.CommonUtil;
import com.hsy.study.baselibrary.viewmodel.BaseViewModel;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * {@link AppCompatActivity } 基类
 * @author haosiyuan
 * @date 2019/2/19 2:19 PM
 */
public abstract class BaseActivity<M extends BaseViewModel>  extends AppCompatActivity implements IActivity {

    protected final String TAG = this.getClass().getSimpleName();

    private ICache<String, Object> mCache;
    @Inject
    @Nullable
    protected  M viewModel;
    @Inject
    protected ICacheType type;

    @NonNull
    @Override
    public ICache<String, Object> getCacheData() {
        if (mCache == null) {
            mCache = CommonUtil.getAppComponent(this).cacheFactory().build(type);
        }
        return mCache;
    }

    @Override
    public void showToast(String message) {
        CommonUtil.getAppComponent(this).toast().toast(this, message,false);
    }
}
