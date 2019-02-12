package com.hsy.study.baselibrary.dagger.module;

import android.app.Application;

import com.hsy.study.baselibrary.dagger.interfaces.GsonConfiguration;
import com.hsy.study.baselibrary.dagger.interfaces.OkHttpConfiguration;
import com.hsy.study.baselibrary.dagger.interfaces.RetrofitConfiguration;
import com.hsy.study.baselibrary.dagger.interfaces.RxCacheConfiguration;
import com.hsy.study.baselibrary.http.log.DefaultFormatPrinter;
import com.hsy.study.baselibrary.http.log.FormatPrinter;
import com.hsy.study.baselibrary.http.log.RequestInterceptor;
import com.hsy.study.baselibrary.utils.FileUtils;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import androidx.annotation.Nullable;
import dagger.Module;
import dagger.Provides;
import okhttp3.internal.Util;

/**
 * 全局配置
 * @author haosiyuan
 * @date 2019/1/20 8:27 PM
 */
@Module
public class GlobalConfigModule {

    private RetrofitConfiguration retrofitConfiguration;
    private OkHttpConfiguration okhttpConfiguration;
    private RxCacheConfiguration rxCacheConfiguration;
    private GsonConfiguration gsonConfiguration;
    private File cacheFile;
    @RequestInterceptor.LogLevel
    private int logLevel;
    private ExecutorService executorService;
    private FormatPrinter formatPrinter;

    public GlobalConfigModule(Builder builder) {
        this.retrofitConfiguration = builder.retrofitConfiguration;
        this.okhttpConfiguration = builder.okhttpConfiguration;
        this.rxCacheConfiguration = builder.rxCacheConfiguration;
        this.cacheFile = builder.cacheFile;
        this.logLevel = builder.logLevel;
        this.executorService = builder.executorService;
        this.formatPrinter = builder.formatPrinter;
        this.gsonConfiguration = builder.gsonConfiguration;
    }

    @Singleton
    @Provides
    @Nullable
    OkHttpConfiguration provideOkhttpConfiguration() {
        return okhttpConfiguration;
    }

    @Singleton
    @Provides
    @Nullable
    RxCacheConfiguration provideRxCacheConfiguration() {
        return rxCacheConfiguration;
    }

    @Singleton
    @Provides
    @Nullable
    RetrofitConfiguration provideRetrofitConfiguration() {
        return retrofitConfiguration;
    }

    @Singleton
    @Provides
    @Nullable
    GsonConfiguration gsonConfiguration() {
        return gsonConfiguration;
    }

    @Singleton
    @Provides
    @Nullable
    File provideCacheFile(Application application) {
        return cacheFile == null ? FileUtils.getCacheFile(application) : cacheFile;
    }

    @Singleton
    @Provides
    int providePrintHttpLogLevel() {
        return logLevel >= 0 ? logLevel : RequestInterceptor.LogLevel.ALL;
    }

    @Singleton
    @Provides
    FormatPrinter provideFormatPrinter() {
        return formatPrinter == null ? new DefaultFormatPrinter() : formatPrinter;
    }

    /**
     * 返回一个全局公用的线程池
     * @return {@link ExecutorService}
     */
    @Singleton
    @Provides
    ExecutorService provideExecutorService() {
        return executorService == null ? new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60, TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>(), Util.threadFactory("Executor", false)) : executorService;
    }

    public static final class Builder{

        private File cacheFile;
        private RetrofitConfiguration retrofitConfiguration;
        private OkHttpConfiguration okhttpConfiguration;
        private RxCacheConfiguration rxCacheConfiguration;
        private GsonConfiguration gsonConfiguration;
        @RequestInterceptor.LogLevel
        private int logLevel;
        /**
         * 请求打印
         */
        private FormatPrinter formatPrinter;
        /**
         * 线程池
         */
        private ExecutorService executorService;


        public Builder retrofitConfiguration(RetrofitConfiguration retrofitConfiguration){
            this.retrofitConfiguration = retrofitConfiguration;
            return this;
        }

        public Builder okhttpConfiguration(OkHttpConfiguration okhttpConfiguration){
            this.okhttpConfiguration = okhttpConfiguration;
            return this;
        }

        public Builder rxCacheConfiguration(RxCacheConfiguration rxCacheConfiguration){
            this.rxCacheConfiguration = rxCacheConfiguration;
            return this;
        }

        public Builder gsonConfiguration(GsonConfiguration gsonConfiguration){
            this.gsonConfiguration = gsonConfiguration;
            return this;
        }

        public Builder logLevel(@RequestInterceptor.LogLevel int logLevel){
            this.logLevel = logLevel;
            return this;
        }

        public Builder executorService(ExecutorService executorService){
            this.executorService = executorService;
            return this;
        }

        public Builder formatPrinter(FormatPrinter formatPrinter){
            this.formatPrinter = formatPrinter;
            return this;
        }

        public Builder cacheFile(File cacheFile){
            this.cacheFile = cacheFile;
            return this;
        }

        public GlobalConfigModule build() {
            return new GlobalConfigModule(this);
        }
    }
}
