package com.endless.study.baselibrary.dagger.module;

import android.app.Application;

import com.endless.study.baselibrary.common.download.DownloadManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.endless.study.baselibrary.cache.local.ICache;
import com.endless.study.baselibrary.cache.local.ICacheType;
import com.endless.study.baselibrary.cache.local.DefaultCacheType;
import com.endless.study.baselibrary.common.executor.AppExecutors;
import com.endless.study.baselibrary.config.AppConfig;
import com.endless.study.baselibrary.dagger.interfaces.IGsonConfiguration;
import com.endless.study.baselibrary.database.AppDatabase;
import com.endless.study.baselibrary.lifecycle.ActivityLifecycle;
import com.endless.study.baselibrary.lifecycle.FragmentLifecycle;
import com.endless.study.baselibrary.lifecycle.GlobalLifecycleHandler;
import com.endless.study.baselibrary.lifecycle.GlobalLifecycleObserver;
import com.endless.study.baselibrary.repository.IRepositoryManager;
import com.endless.study.baselibrary.repository.RepositoryManager;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.room.Room;
import androidx.room.migration.Migration;
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

    @Singleton
    @Provides
    static AppDatabase provideAppDatabase(Application mApplication, List<Migration> migrationList) {
        Migration[] migrations = new Migration[migrationList.size()];
        return Room.databaseBuilder(mApplication, AppDatabase.class, AppConfig.DATABASE_NAME)
                   .allowMainThreadQueries()
                   .addMigrations(migrationList.toArray(migrations))
                   .build();
    }

    @Singleton
    @Provides
    static AppExecutors provideAppExecutors() {
        return new AppExecutors();
    }

    @Singleton
    @Provides
    static DownloadManager provideDownloadManager(Application application, AppDatabase appDatabase) {
        return new DownloadManager(application, appDatabase);
    }
}
