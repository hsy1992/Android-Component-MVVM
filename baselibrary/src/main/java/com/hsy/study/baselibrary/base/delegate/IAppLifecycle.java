package com.hsy.study.baselibrary.base.delegate;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;

/**
 * {@link android.app.Application} 的生命周期代理类
 * @author haosiyuan
 * @date 2019/2/15 4:18 PM
 */
public interface IAppLifecycle {

    /**
     * 执行第一个声明周期
     * @param base
     */
    void attachBaseContext(@NonNull Context base);

    /**
     * 创建
     * @param application
     */
    void onCreate(@NonNull Application application);

    /**
     * 终止
     * @param application
     */
    void onTerminate(@NonNull Application application);

}
