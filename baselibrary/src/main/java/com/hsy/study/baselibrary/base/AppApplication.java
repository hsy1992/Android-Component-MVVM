package com.hsy.study.baselibrary.base;

import android.app.Application;

import com.hsy.study.baselibrary.dagger.component.AppComponent;
import com.hsy.study.baselibrary.dagger.component.DaggerAppComponent;
import com.hsy.study.baselibrary.dagger.module.GlobalConfigModule;
import com.hsy.study.baselibrary.http.log.RequestInterceptor;

/**
 * @author haosiyuan
 * @date 2019/1/28 3:20 PM
 */
public class AppApplication extends Application {

    public static AppComponent mAppComponent;
    @Override
    public void onCreate() {
        super.onCreate();

        mAppComponent = DaggerAppComponent
                .builder()
                .application(this)
                .globalConfigModule(new GlobalConfigModule.Builder()
                .logLevel(RequestInterceptor.LogLevel.ALL)
                .build()).build();
        mAppComponent.inject(this);
    }

    public static AppComponent getmAppComponent() {
        return mAppComponent;
    }


}
