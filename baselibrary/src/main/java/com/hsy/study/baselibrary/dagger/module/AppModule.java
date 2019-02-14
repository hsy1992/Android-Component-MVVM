package com.hsy.study.baselibrary.dagger.module;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hsy.study.baselibrary.base.AppApplication;
import com.hsy.study.baselibrary.dagger.interfaces.GsonConfiguration;
import com.hsy.study.baselibrary.lifecycle.ActivityLifecycle;
import com.hsy.study.baselibrary.lifecycle.FragmentLifecycle;
import com.hsy.study.baselibrary.lifecycle.GlobalLifecycleHandler;
import com.hsy.study.baselibrary.lifecycle.GlobalLifecycleObserver;

import javax.inject.Named;
import javax.inject.Singleton;

import androidx.fragment.app.FragmentManager;
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

    /**
     * 提供gson
     * @param appApplication
     * @param configuration
     * @return
     */
    @Singleton
    @Provides
    static Gson provideGson(AppApplication appApplication, GsonConfiguration configuration) {
        GsonBuilder builder = new GsonBuilder();
        if (configuration != null){
            configuration.configGson(appApplication, builder);
        }
        return builder.create();
    }

    /**
     * 提供 FragmentLifecycleCallbacks
     * @param fragmentLifecycle
     * @return
     */
    @Binds
    @Named("FragmentLifecycle")
    abstract FragmentManager.FragmentLifecycleCallbacks bindFragmentLifecycle(FragmentLifecycle fragmentLifecycle);

    /**
     * 提供 ActivityLifecycleCallbacks
     * @param activityLifecycle
     * @return
     */
    @Binds
    @Named("ActivityLifecycle")
    abstract Application.ActivityLifecycleCallbacks bindActivityLifecycle(ActivityLifecycle activityLifecycle);

}
