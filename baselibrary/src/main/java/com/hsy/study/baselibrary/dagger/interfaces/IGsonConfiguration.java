package com.hsy.study.baselibrary.dagger.interfaces;

import android.content.Context;

import com.google.gson.GsonBuilder;

import androidx.annotation.NonNull;

/**
 * gson配置
 * @author haosiyuan
 * @date 2019/2/11 3:46 PM
 */
public interface IGsonConfiguration {

    void configGson(@NonNull Context context, @NonNull GsonBuilder builder);
}
