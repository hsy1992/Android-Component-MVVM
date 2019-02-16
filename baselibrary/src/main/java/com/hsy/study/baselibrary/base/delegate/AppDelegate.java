package com.hsy.study.baselibrary.base.delegate;

import android.app.Application;
import android.content.Context;

import com.hsy.study.baselibrary.base.App;
import com.hsy.study.baselibrary.dagger.component.AppComponent;
import com.hsy.study.baselibrary.utils.Preconditions;

import androidx.annotation.NonNull;

/**
 * {@link Application} 代理类 代理类
 * @author haosiyuan
 * @date 2019/2/14 1:48 PM
 */
public class AppDelegate implements AppLifecycle, App {

    private AppComponent mAppComponent;

    @Override
    public void attachBaseContext(@NonNull Context base) {

    }

    @Override
    public void onCreate(@NonNull Application application) {

    }

    @Override
    public void onTerminate(@NonNull Application application) {

    }

    /**
     * 提供{@link AppComponent}
     * @return
     */
    @NonNull
    @Override
    public AppComponent getAppComponent() {
        Preconditions.checkNotNull(mAppComponent,"AppComponent is null");
        return mAppComponent;
    }
}
