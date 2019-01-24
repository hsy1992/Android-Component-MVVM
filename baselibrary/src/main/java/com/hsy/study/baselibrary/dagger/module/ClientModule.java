package com.hsy.study.baselibrary.dagger.module;

import android.app.Application;

import com.google.gson.Gson;
import com.hsy.study.baselibrary.dagger.interfaces.OkHttpConfiguration;
import com.hsy.study.baselibrary.dagger.interfaces.RetrofitConfiguration;
import com.hsy.study.baselibrary.http.GlobalHttpHandler;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import androidx.annotation.Nullable;
import dagger.Module;
import dagger.Provides;
import okhttp3.Dispatcher;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**网络连接
 * @author haosiyuan
 * @date 2019/1/20 8:25 PM
 */
@Module
public class ClientModule {

    /**
     * 超时时长
     */
    private static final int TIME_OUT = 10;

    /**
     * 提供Retrofit Builder
     * @param application       {@link Application}
     * @param builder           {@link Retrofit.Builder}
     * @param configuration     {@link RetrofitConfiguration}
     * @param httpUrl           {@link HttpUrl}
     * @param client            {@link OkHttpClient}
     * @param gson              {@link Gson}
     * @return
     */
    @Singleton
    @Provides
    Retrofit.Builder provideRetrofit(Application application, Retrofit.Builder builder, @Nullable RetrofitConfiguration configuration,
                                 HttpUrl httpUrl, OkHttpClient client, Gson gson){

        builder
                .baseUrl(httpUrl)
                .client(client);

        //执行自定义配置
        if (configuration != null){
            configuration.configRetrofit(application, builder);
        }

        //使用RxJava
        builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        //使用 Gson
        builder.addConverterFactory(GsonConverterFactory.create(gson));

        return builder;
    }

    @Singleton
    @Provides
    OkHttpClient provideOkHttp(Application application, OkHttpClient.Builder builder, @Nullable OkHttpConfiguration configuration, final GlobalHttpHandler handler,
                               Interceptor netWorkInterceptor, @Nullable List<Interceptor> interceptors, ExecutorService executorService){

        builder.
                connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                .addNetworkInterceptor(netWorkInterceptor);

        //添加拦截器
        if (interceptors != null){

            for (Interceptor interceptor : interceptors){
                builder.addInterceptor(interceptor);

            }
        }

        //添加请求前
        if (handler != null){
            builder.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    return chain.proceed(handler.onHttpRequestBefore(chain, chain.request()));
                }
            });
        }

        //设置默认线程池
        builder.dispatcher(new Dispatcher());

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
    OkHttpClient.Builder provideOkHttpBuilder(){
        return new OkHttpClient.Builder();
    }

    /**
     * 提供{@link Retrofit.Builder}
     * @return
     */
    @Singleton
    @Provides
    Retrofit.Builder provideRetrofitBuilder(){
        return new Retrofit.Builder();
    }
}
