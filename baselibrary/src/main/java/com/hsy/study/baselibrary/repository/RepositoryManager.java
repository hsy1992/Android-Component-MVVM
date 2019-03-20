package com.hsy.study.baselibrary.repository;

import android.app.Application;
import android.content.Context;

import com.hsy.study.baselibrary.cache.local.DefaultCacheType;
import com.hsy.study.baselibrary.cache.local.ICache;
import com.hsy.study.baselibrary.utils.PreconditionsUtil;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.Callable;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.annotation.NonNull;
import dagger.Lazy;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.schedulers.Schedulers;
import io.rx_cache2.internal.RxCache;
import retrofit2.Retrofit;

/**
 * 仓库管理 提供网络连接，缓存连接
 * @author haosiyuan
 * @date 2019/2/25 2:14 PM
 */
@Singleton
public class RepositoryManager implements IRepositoryManager {

    @Inject
    Lazy<Retrofit> mRetrofit;
    @Inject
    Lazy<RxCache> mRxCache;
    @Inject
    Application mApplication;
    @Inject
    ICache.Factory mCacheFactory;
    private ICache<String, Object> mRetrofitServiceCache;
    private ICache<String, Object> mRxCacheServiceCache;

    @Inject
    public RepositoryManager() {}

    /**
     * 根据传入的
     * @param serviceClass retrofit class
     * @param <T>
     * @return
     */
    @NonNull
    @Override
    public <T> T obtainRetrofitService(@NonNull Class<T> serviceClass) {
        return createWrapperService(serviceClass);
    }

    /**
     * 创建包装Retrofit Service 生成代理类
     * @param serviceClass
     * @param <T>
     * @return
     */
    private <T> T createWrapperService(Class<T> serviceClass) {
        PreconditionsUtil.checkNotNull(serviceClass,"serviceClass is null");
        // 通过二次代理，对 Retrofit 代理方法的调用包进新的 Observable 里在 io 线程执行。
        return (T) Proxy.newProxyInstance(serviceClass.getClassLoader(),
                new Class<?>[]{serviceClass}, (proxy, method, args) -> {

                    if (method.getReturnType() == Observable.class) {
                        //方法返回类型是 Observable，包一层返回
                        return Observable.defer((Callable<ObservableSource<?>>) () -> {
                            T service = getRetrofitService(serviceClass);
                            return ((Observable)getRetrofitMethod(service, method)
                                    .invoke(service, args))
                                    .subscribeOn(Schedulers.io());
                        }).subscribeOn(Schedulers.single());
                    }

                    // 返回值不是 Observable 的话不处理
                    T service = getRetrofitService(serviceClass);
                    return getRetrofitMethod(service, method).invoke(service, args);
                });
    }

    /**
     * 根据传入的Class 获取对应的 Retrofit Service
     * @param serviceClass
     * @param <T>
     * @return
     */
    private <T> T getRetrofitService(Class<T> serviceClass) {
        if (mRetrofitServiceCache == null) {
            mRetrofitServiceCache = mCacheFactory.build(new DefaultCacheType());
        }

        PreconditionsUtil.checkNotNull(mRetrofitServiceCache,
                "Cannot return null from a Cache.Factory#build(int) method");

        T retrofitService = (T) mRetrofitServiceCache.get(serviceClass.getCanonicalName());
        //缓存中没有 则创建加入缓存
        if (retrofitService == null) {
            retrofitService = mRetrofit.get().create(serviceClass);
            mRetrofitServiceCache.put(serviceClass.getCanonicalName(), retrofitService);
        }
        return retrofitService;
    }

    /**
     * 获取 Retrofit 动态代理的方法
     * @param service
     * @param method
     * @param <T>
     * @return
     * @throws NoSuchMethodException
     */
    private <T> Method getRetrofitMethod(T service, Method method) throws NoSuchMethodException {
        return service.getClass().getMethod(method.getName(), method.getParameterTypes());

    }

    /**
     * 根据传入的cacheClass 获取相应的 RxCache service
     * @param cacheClass
     * @param <T>
     * @return
     */
    @NonNull
    @Override
    public <T> T obtainRxCacheService(@NonNull Class<T> cacheClass) {
        PreconditionsUtil.checkNotNull(cacheClass, "cacheClass == null");
        if (mRxCacheServiceCache == null) {
            mRxCacheServiceCache = mCacheFactory.build(new DefaultCacheType());
        }
        PreconditionsUtil.checkNotNull(mRxCacheServiceCache,
                "Cannot return null from a Cache.Factory#build(int) method");
        T cacheService = (T) mRxCacheServiceCache.get(cacheClass.getCanonicalName());
        if (cacheService == null) {
            cacheService = mRxCache.get().using(cacheClass);
            mRxCacheServiceCache.put(cacheClass.getCanonicalName(), cacheClass);
        }
        return cacheService;
    }

    @NonNull
    @Override
    public <T> T obtainDatabaseService(@NonNull Class<T> dao) {
        return null;
    }

    @Override
    public void clearAllCache() {
        mRxCache.get().evictAll().subscribe();
    }

    @Override
    public Context getContext() {
        return mApplication;
    }
}
