package com.endless.study.baselibrary.dagger.interfaces;

import android.content.Context;

import androidx.annotation.NonNull;
import okhttp3.OkHttpClient;

/**
 * OkHttp 自定义 配置
 * @author haosiyuan
 * @date 2019/1/20 8:32 PM
 */
public interface IOkHttpConfiguration {

    /**
     * 实现自定义 OkHttp配置
     * @param context
     * @param builder
     */
    void configOkHttp(@NonNull Context context, @NonNull OkHttpClient.Builder builder);
}
