package com.hsy.study.baselibrary.dagger.module;

import com.hsy.study.baselibrary.dagger.interfaces.OkHttpConfiguration;
import com.hsy.study.baselibrary.dagger.interfaces.RetrofitConfiguration;
import com.hsy.study.baselibrary.dagger.interfaces.RxCacheConfiguration;
import com.hsy.study.baselibrary.http.log.FormatPrinter;
import com.hsy.study.baselibrary.http.log.RequestInterceptor;

import java.io.File;
import java.util.concurrent.ExecutorService;

import dagger.Module;

/**
 * 全局配置
 * @author haosiyuan
 * @date 2019/1/20 8:27 PM
 */
@Module
public class GlobalConfigModule {



    public static final class Builder{

        private File cacheFile;
        private RetrofitConfiguration retrofitConfiguration;
        private OkHttpConfiguration okhttpConfiguration;
        private RxCacheConfiguration rxCacheConfiguration;
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


    }
}
