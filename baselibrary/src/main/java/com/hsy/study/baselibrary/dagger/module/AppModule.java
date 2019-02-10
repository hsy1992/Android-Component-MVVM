package com.hsy.study.baselibrary.dagger.module;

import com.google.gson.Gson;
import com.hsy.study.baselibrary.lifecycle.GlobalLifecycleHandler;
import com.hsy.study.baselibrary.lifecycle.GlobalLifecycleObserver;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

/**
 * app module
 * @author haosiyuan
 * @date 2019/1/29 2:53 PM
 */
@Module
public abstract class AppModule {

    @Binds
    @Named("GlobalLifecycleObserver")
    abstract GlobalLifecycleObserver bindLifecycleObserver(GlobalLifecycleObserver globalLifecycleObserver);

    @Binds
    @Named("GlobalLifecycleHandler")
    abstract GlobalLifecycleHandler bindGlobalLifecycleHandler(GlobalLifecycleHandler globalLifecycleHandler);

    @Singleton
    @Provides
    static Gson provideGson() {
        return new Gson();
    }
}
