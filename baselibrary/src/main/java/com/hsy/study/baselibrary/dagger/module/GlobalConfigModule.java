package com.hsy.study.baselibrary.dagger.module;

import android.app.Application;

import com.hsy.study.baselibrary.cache.local.ICache;
import com.hsy.study.baselibrary.cache.local.DefaultCacheType;
import com.hsy.study.baselibrary.cache.local.IntelligentCache;
import com.hsy.study.baselibrary.common.executor.AppExecutors;
import com.hsy.study.baselibrary.dagger.interfaces.IGsonConfiguration;
import com.hsy.study.baselibrary.dagger.interfaces.IOkHttpConfiguration;
import com.hsy.study.baselibrary.dagger.interfaces.IRetrofitConfiguration;
import com.hsy.study.baselibrary.dagger.interfaces.IRxCacheConfiguration;
import com.hsy.study.baselibrary.http.IBaseUrl;
import com.hsy.study.baselibrary.http.IGlobalHttpHandler;
import com.hsy.study.baselibrary.http.log.DefaultFormatPrinter;
import com.hsy.study.baselibrary.http.log.IFormatPrinter;
import com.hsy.study.baselibrary.http.log.RequestInterceptor;
import com.hsy.study.baselibrary.utils.UtilFile;
import com.hsy.study.baselibrary.utils.UtilPreconditions;
import com.hsy.study.baselibrary.common.toast.IToastConfiguration;
import com.hsy.study.baselibrary.common.toast.SystemToast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

import javax.inject.Singleton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.migration.Migration;
import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;

/**
 * 全局配置
 * @author haosiyuan
 * @date 2019/1/20 8:27 PM
 */
@Module
public class GlobalConfigModule {

    private IRetrofitConfiguration retrofitConfiguration;
    private IOkHttpConfiguration okHttpConfiguration;
    private IRxCacheConfiguration rxCacheConfiguration;
    private IGsonConfiguration gsonConfiguration;
    private File cacheFile;
    @RequestInterceptor.LogLevel
    private int logLevel;
    private ExecutorService executorService;
    private IFormatPrinter formatPrinter;
    private ICache.Factory cacheFactory;
    private HttpUrl apiUrl;
    private IBaseUrl baseUrl;
    private IGlobalHttpHandler handler;
    private List<Interceptor> mInterceptors;
    private IToastConfiguration iToastConfiguration;
    private List<Migration> migrations;

    public GlobalConfigModule(Builder builder) {
        this.retrofitConfiguration = builder.retrofitConfiguration;
        this.okHttpConfiguration = builder.okHttpConfiguration;
        this.rxCacheConfiguration = builder.rxCacheConfiguration;
        this.cacheFile = builder.cacheFile;
        this.logLevel = builder.logLevel;
        this.executorService = builder.executorService;
        this.formatPrinter = builder.formatPrinter;
        this.gsonConfiguration = builder.gsonConfiguration;
        this.cacheFactory = builder.cacheFactory;
        this.apiUrl = builder.apiUrl;
        this.baseUrl = builder.baseUrl;
        this.handler = builder.handler;
        this.mInterceptors = builder.mInterceptors;
        this.iToastConfiguration = builder.iToastConfiguration;
        this.migrations = builder.migrations;
    }

    /**
     * 提供http 请求url 优先{@link IBaseUrl}
     * @return
     */
    @Singleton
    @Provides
    HttpUrl provideHttpUrl() {
        if (baseUrl != null) {
            HttpUrl httpUrl = baseUrl.url();
            if (httpUrl != null){
                return httpUrl;
            }
        }
        UtilPreconditions.checkNotNull(apiUrl,"HttpUrl can not be null");
        return apiUrl;
    }

    /**
     * 提供 {@link IGlobalHttpHandler} Http管理类
     * @return
     */
    @Singleton
    @Provides
    @NonNull
    IGlobalHttpHandler provideGlobalHttpHandler() {
        return handler == null ? IGlobalHttpHandler.EMPTY : handler;
    }

    @Singleton
    @Provides
    @Nullable
    IOkHttpConfiguration provideOkHttpConfiguration() {
        return okHttpConfiguration;
    }

    @Singleton
    @Provides
    @Nullable
    List<Interceptor> provideHttpInterceptors() {
        return mInterceptors;
    }

    @Singleton
    @Provides
    @Nullable
    IRxCacheConfiguration provideRxCacheConfiguration() {
        return rxCacheConfiguration;
    }

    @Singleton
    @Provides
    @Nullable
    IRetrofitConfiguration provideRetrofitConfiguration() {
        return retrofitConfiguration;
    }

    @Singleton
    @Provides
    @Nullable
    IGsonConfiguration provideGsonConfiguration() {
        return gsonConfiguration;
    }

    @Singleton
    @Provides
    File provideCacheFile(Application application) {
        return cacheFile == null ? UtilFile.getCacheFile(application) : cacheFile;
    }

    @Singleton
    @Provides
    int providePrintHttpLogLevel() {
        return logLevel >= 0 ? logLevel : RequestInterceptor.LogLevel.ALL;
    }

    @Singleton
    @Provides
    IFormatPrinter provideFormatPrinter() {
        return formatPrinter == null ? new DefaultFormatPrinter() : formatPrinter;
    }

    @Singleton
    @Provides
    ICache.Factory provideCacheFactory(Application application) {
        return cacheFactory == null ? type ->
                new IntelligentCache(new DefaultCacheType().calculateCacheSize(application)) : null;
    }

    /**
     * 提示
     * @return
     */
    @Singleton
    @Provides
    IToastConfiguration provideToastConfiguration() {
        return iToastConfiguration == null ? new SystemToast() : iToastConfiguration;
    }

    /**
     * 返回一个全局公用的线程池
     * @return {@link ExecutorService}
     */
    @Singleton
    @Provides
    ExecutorService provideExecutorService(AppExecutors appExecutors) {
        return executorService == null ? appExecutors.getNetWorkIO() : executorService;
    }

    /**
     * 数据库升级情况
     * @return
     */
    @Singleton
    @Provides
    List<Migration> provideMMigrations() {
        return migrations == null ? new ArrayList<>() : migrations;
    }

    public static final class Builder{

        private HttpUrl apiUrl;
        private IBaseUrl baseUrl;
        private IGlobalHttpHandler handler;
        private File cacheFile;
        private IRetrofitConfiguration retrofitConfiguration;
        private IOkHttpConfiguration okHttpConfiguration;
        private IRxCacheConfiguration rxCacheConfiguration;
        private IGsonConfiguration gsonConfiguration;
        @RequestInterceptor.LogLevel
        private int logLevel;
        private IToastConfiguration iToastConfiguration;
        /**
         * 拦截器
         */
        private List<Interceptor> mInterceptors;
        /**
         * 缓存
         */
        private ICache.Factory cacheFactory;
        /**
         * 请求打印
         */
        private IFormatPrinter formatPrinter;
        /**
         * 线程池
         */
        private ExecutorService executorService;
        /**
         * 数据库版本升级
         */
        private List<Migration> migrations;

        public Builder retrofitConfiguration(IRetrofitConfiguration retrofitConfiguration) {
            this.retrofitConfiguration = retrofitConfiguration;
            return this;
        }

        public Builder okhttpConfiguration(IOkHttpConfiguration okHttpConfiguration) {
            this.okHttpConfiguration = okHttpConfiguration;
            return this;
        }

        public Builder rxCacheConfiguration(IRxCacheConfiguration rxCacheConfiguration) {
            this.rxCacheConfiguration = rxCacheConfiguration;
            return this;
        }

        public Builder gsonConfiguration(IGsonConfiguration gsonConfiguration) {
            this.gsonConfiguration = gsonConfiguration;
            return this;
        }

        public Builder logLevel(@RequestInterceptor.LogLevel int logLevel) {
            this.logLevel = logLevel;
            return this;
        }

        public Builder executorService(ExecutorService executorService) {
            this.executorService = executorService;
            return this;
        }

        public Builder formatPrinter(IFormatPrinter formatPrinter) {
            this.formatPrinter = formatPrinter;
            return this;
        }

        public Builder cacheFile(File cacheFile) {
            this.cacheFile = cacheFile;
            return this;
        }

        public Builder cacheFactory(ICache.Factory cacheFile) {
            this.cacheFactory = cacheFactory;
            return this;
        }

        /**
         * APP 基础url
         * @param baseUrl
         * @return
         */
        public Builder baseUrl(String baseUrl) {
            UtilPreconditions.checkNotNull(baseUrl,"baseUrl can not be null");
            this.apiUrl = HttpUrl.parse(baseUrl);
            return this;
        }

        public Builder baseUrl(IBaseUrl baseUrl) {
            UtilPreconditions.checkNotNull(baseUrl,"baseUrl can not be null");
            this.baseUrl = baseUrl;
            return this;
        }

        /**
         * Http 拦截器
         * @param mInterceptors
         * @return
         */
        public Builder httpInterceptors(List<Interceptor> mInterceptors) {
            this.mInterceptors = mInterceptors;
            return this;
        }

        /**
         * 提示
         * @return
         */
        public Builder toastConfiguration(IToastConfiguration toastConfiguration) {
            this.iToastConfiguration = toastConfiguration;
            return this;
        }

        /**
         * http 全局处理
         * @param handler
         * @return
         */
        public Builder iGlobalHttpHandler(IGlobalHttpHandler handler) {
            this.handler = handler;
            return this;
        }

        /**
         * 数据库版本升级
         * @return
         */
        public Builder migrations(List<Migration> migrations) {
            this.migrations = migrations;
            return this;
        }


        public GlobalConfigModule build() {
            return new GlobalConfigModule(this);
        }
    }
}
