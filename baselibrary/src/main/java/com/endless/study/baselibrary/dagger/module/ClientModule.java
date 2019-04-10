package com.endless.study.baselibrary.dagger.module;

import android.app.Application;

import com.google.gson.Gson;
import com.endless.study.baselibrary.dagger.interfaces.IOkHttpConfiguration;
import com.endless.study.baselibrary.dagger.interfaces.IRetrofitConfiguration;
import com.endless.study.baselibrary.dagger.interfaces.IRxCacheConfiguration;
import com.endless.study.baselibrary.http.IGlobalHttpHandler;
import com.endless.study.baselibrary.http.interceptors.MultipleBaseUrlInterceptor;
import com.endless.study.baselibrary.http.log.RequestInterceptor;
import com.endless.study.baselibrary.repository.calladapter.LiveDataCallAdapterFatory;
import com.endless.study.baselibrary.utils.UtilFile;

import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import io.rx_cache2.internal.RxCache;
import io.victoralbertos.jolyglot.GsonSpeaker;
import okhttp3.Dispatcher;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 网络连接
 * @author haosiyuan
 * @date 2019/1/20 8:25 PM
 */
@Module
public abstract class ClientModule {

    /**
     * 超时时长
     */
    private static final int TIME_OUT = 10;

    /**
     * 提供了Retrofit
     * @param application       {@link Application}
     * @param builder           {@link Retrofit.Builder}
     * @param configuration     {@link IRetrofitConfiguration}
     * @param httpUrl           {@link HttpUrl}
     * @param client            {@link OkHttpClient}
     * @param gson              {@link Gson}
     * @return
     */
    @Singleton
    @Provides
    static Retrofit provideRetrofit(Application application, Retrofit.Builder builder, @Nullable IRetrofitConfiguration configuration,
                                 HttpUrl httpUrl, OkHttpClient client, Gson gson){

        builder
            .baseUrl(httpUrl)
            .client(client);

        //执行自定义配置
        if (configuration != null){
            configuration.configRetrofit(application, builder);
        }

        //使用RxJava
        builder.addCallAdapterFactory(new LiveDataCallAdapterFatory());
        //使用 Gson
        builder.addConverterFactory(GsonConverterFactory.create(gson));

        return builder.build();
    }

    /**
     * 提供OkHttp
     * @param application
     * @param builder
     * @param configuration
     * @param handler
     * @param netWorkInterceptor
     * @param interceptors
     * @param executorService
     * @return
     */
    @Singleton
    @Provides
    static OkHttpClient provideOkHttp(Application application, OkHttpClient.Builder builder, @Nullable IOkHttpConfiguration configuration, final IGlobalHttpHandler handler,
                                      Interceptor netWorkInterceptor, @Nullable List<Interceptor> interceptors,
                                      ExecutorService executorService, @NonNull MultipleBaseUrlInterceptor multipleBaseUrlInterceptor){

        builder.
            connectTimeout(TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(TIME_OUT, TimeUnit.SECONDS)
            .addNetworkInterceptor(netWorkInterceptor)
            .addInterceptor(multipleBaseUrlInterceptor);

        //添加拦截器
        if (interceptors != null){
            for (Interceptor interceptor : interceptors){
                builder.addInterceptor(interceptor);
            }
        }

        //添加请求前
        if (handler != null){
            builder.addInterceptor(chain -> chain.proceed(handler.onHttpRequestBefore(chain, chain.request())));
        }

        //设置默认线程池
        builder.dispatcher(new Dispatcher(executorService));

        //实现自定义配置
        if (configuration != null){
            configuration.configOkHttp(application, builder);
        }

        return builder.build();
    }

    /**
     * 提供{@link OkHttpClient.Builder}
     * @return
     */
    @Singleton
    @Provides
    static OkHttpClient.Builder provideOkHttpBuilder(){
        return new OkHttpClient.Builder();
    }

    /**
     * 提供{@link Retrofit.Builder}
     * @return
     */
    @Singleton
    @Provides
    static Retrofit.Builder provideRetrofitBuilder(){
        return new Retrofit.Builder();
    }

    @Binds
    abstract Interceptor bindInterceptor(RequestInterceptor interceptor);

    @Singleton
    @Provides
    static MultipleBaseUrlInterceptor bindMultipleBaseUrlInterceptor() {
        return new MultipleBaseUrlInterceptor();
    }

    /**
     * 提供{@link RxCache}
     * @return
     */
    @Singleton
    @Provides
    static RxCache provideRxCache(Application application, @Nullable IRxCacheConfiguration configuration,
                           @Named("RxCacheDirectory") File cacheDirectory, Gson gson){
        RxCache.Builder builder = new RxCache.Builder();
        RxCache rxCache = null;
        if (configuration != null){
            rxCache = configuration.configRxCache(application, builder);
        }
        if (rxCache != null){
            return rxCache;
        }

        return builder.persistence(cacheDirectory, new GsonSpeaker(gson));
    }

    /**
     * 提供缓存文件地址
     * @param cacheDir
     * @return
     */
    @Singleton
    @Provides
    @Named("RxCacheDirectory")
    static File provideRxCacheDirectory(File cacheDir){
        File cacheDirectory = new File(cacheDir,"RxCache");
        return UtilFile.makeDirs(cacheDirectory);
    }
}
