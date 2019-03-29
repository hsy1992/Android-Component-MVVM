package com.endless.study.baselibrary.base;

import android.app.Application;
import android.content.Context;

import com.endless.study.baselibrary.base.delegate.AppDelegate;
import com.endless.study.baselibrary.base.delegate.IAppLifecycle;
import com.endless.study.baselibrary.dagger.component.AppComponent;
import com.endless.study.baselibrary.utils.UtilPreconditions;

import androidx.annotation.NonNull;

/**
 * @author haosiyuan
 * @date 2019/1/28 3:20 PM
 */
public class AppApplication extends Application implements IApp {

    private IAppLifecycle mAppDelegate;

    /**
     * 这里会在 {@link AppApplication#onCreate} 之前被调用,可以做一些较早的初始化
     * 常用于 MultiDex 以及插件化框架的初始化
     *
     * @param base
     */
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        if (mAppDelegate == null) {
            mAppDelegate = new AppDelegate(base);
        }
        mAppDelegate.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (mAppDelegate != null) {
            mAppDelegate.onCreate(this);
        }
    }

    /**
     * 生命周期终止
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
        if (mAppDelegate != null) {
            mAppDelegate.onTerminate(this);
        }
    }

    @NonNull
    @Override
    public AppComponent getAppComponent() {
        UtilPreconditions.checkNotNull(mAppDelegate, "%s can not be null", AppDelegate.class.getName());
        UtilPreconditions.checkState(mAppDelegate instanceof IApp, "%s must be implements %s", mAppDelegate.getClass().getName(), IApp.class.getName());
        return ((IApp)mAppDelegate).getAppComponent();
    }
}
