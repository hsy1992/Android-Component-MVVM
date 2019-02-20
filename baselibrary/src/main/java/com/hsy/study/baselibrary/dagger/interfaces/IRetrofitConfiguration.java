package com.hsy.study.baselibrary.dagger.interfaces;

import android.content.Context;

import androidx.annotation.NonNull;
import retrofit2.Retrofit;

/**
 * retrofit 自定义 配置
 * @author haosiyuan
 * @date 2019/1/20 8:32 PM
 */
public interface IRetrofitConfiguration {

    /**
     * 传入自定义retrofit配置
     * @param context
     * @param builder
     */
    void configRetrofit(@NonNull Context context, @NonNull Retrofit.Builder builder);
}
