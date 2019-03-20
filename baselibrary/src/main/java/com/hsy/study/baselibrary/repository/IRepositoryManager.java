package com.hsy.study.baselibrary.repository;

import android.content.Context;

import androidx.annotation.NonNull;

/**
 * 管理网络数据层、缓存层、数据库请求层 提供给ViewModel 必要的 Api 做数据处理
 * @author haosiyuan
 * @date 2019/1/20 8:09 PM
 */
public interface IRepositoryManager {

    /**
     * 传入对应的 Retrofit service
     * @param serviceClass retrofit class
     * @param <T> Retrofit service 类型
     * @return
     */
    @NonNull
    <T> T obtainRetrofitService(@NonNull Class<T> serviceClass);

    /**
     * 传入对应的 RxCache service
     * @param cacheClass
     * @param <T>
     * @return
     */
    @NonNull
    <T> T obtainRxCacheService(@NonNull Class<T> cacheClass);

    /**
     * 传入对应的 Database Dao
     * @param dao
     * @param <T>
     * @return
     */
    @NonNull
    <T> T obtainDatabaseService(@NonNull Class<T> dao);

    /**
     * 清理所有缓存
     */
    void clearAllCache();

    /**
     * 获取 context
     */
    Context getContext();
}
