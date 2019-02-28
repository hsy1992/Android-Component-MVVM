package com.hsy.study.baselibrary.dagger.module;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hsy.study.baselibrary.cache.ICache;
import com.hsy.study.baselibrary.cache.ICacheType;
import com.hsy.study.baselibrary.cache.DefaultCacheType;
import com.hsy.study.baselibrary.dagger.interfaces.IGsonConfiguration;
import com.hsy.study.baselibrary.lifecycle.ActivityLifecycle;
import com.hsy.study.baselibrary.lifecycle.FragmentLifecycle;
import com.hsy.study.baselibrary.lifecycle.GlobalLifecycleHandler;
import com.hsy.study.baselibrary.lifecycle.GlobalLifecycleObserver;
import com.hsy.study.baselibrary.viewmodel.IRepositoryManager;
import com.hsy.study.baselibrary.viewmodel.RepositoryManager;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import androidx.annotation.Nullable;
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
     * @param application
     * @param configuration
     * @return
     */
    @Singleton
    @Provides
    static Gson provideGson(Application application, @Nullable IGsonConfiguration configuration) {
        GsonBuilder builder = new GsonBuilder();
        if (configuration != null) {
            configuration.configGson(application, builder);
        }
        return builder.create();
    }

    /**
     * 提供 FragmentLifecycleCallbacks
     * @param fragmentLifecycle
     * @return
     */
    @Binds
    abstract FragmentManager.FragmentLifecycleCallbacks bindFragmentLifecycle(FragmentLifecycle fragmentLifecycle);

    /**
     * 提供 ActivityLifecycleCallbacks
     * @param activityLifecycle
     * @return
     */
    @Binds
    @Named("ActivityLifecycle")
    abstract Application.ActivityLifecycleCallbacks bindActivityLifecycle(ActivityLifecycle activityLifecycle);

    /**
     * {@link FragmentLifecycle } 集合
     * @return
     */
    @Singleton
    @Provides
    static List<FragmentManager.FragmentLifecycleCallbacks> provideFragmentLifecycleList() {
        return new ArrayList<>();
    }

    /**
     * 提供缓存 供使用
     * @param cacheFactory
     * @param cacheType
     * @return
     */
    @Singleton
    @Provides
    static ICache<String, Object> provideExtras(ICache.Factory cacheFactory, ICacheType cacheType) {
        return cacheFactory.build(cacheType);
    }

    @Singleton
    @Provides
    static ICacheType provideDefaultCacheType() {
        return new DefaultCacheType();
    }

    @Binds
    abstract IRepositoryManager bindRepositoryManager(RepositoryManager repositoryManager);

}
