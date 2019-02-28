package com.hsy.study.baselibrary.config;

import android.app.Application;
import android.content.Context;
import android.app.Activity;
import androidx.fragment.app.Fragment;

import com.hsy.study.baselibrary.base.delegate.IAppLifecycle;
import com.hsy.study.baselibrary.dagger.module.GlobalConfigModule;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;

/**
 * 给用户提供的一些自定义配置 在AndroidManifest中声明
 * @author haosiyuan
 * @date 2019/2/15 4:14 PM
 */
public interface IConfigModule {
    /**
     * 使用 {@link GlobalConfigModule.Builder} 给框架配置一些配置参数
     *
     * @param context {@link Context}
     * @param builder {@link GlobalConfigModule.Builder}
     */
    void applyOptions(@NonNull Context context, @NonNull GlobalConfigModule.Builder builder);

    /**
     * 使用 {@link IAppLifecycle} 在 {@link Application} 的生命周期中注入一些操作
     *
     * @param context    {@link Context}
     * @param lifecycle {@link Application} 的生命周期容器, 可向框架中添加多个 {@link Application} 的生命周期类
     */
    void injectAppLifecycle(@NonNull Context context, @NonNull List<IAppLifecycle> lifecycle);

    /**
     * 使用 {@link Application.ActivityLifecycleCallbacks} 在 {@link Activity} 的生命周期中注入一些操作
     *
     * @param context    {@link Context}
     * @param lifecycleList {@link Activity} 的生命周期容器, 可向框架中添加多个 {@link Activity} 的生命周期类
     */
    void injectActivityLifecycle(@NonNull Context context, @NonNull List<Application.ActivityLifecycleCallbacks> lifecycleList);

    /**
     * 使用 {@link FragmentManager.FragmentLifecycleCallbacks} 在 {@link Fragment} 的生命周期中注入一些操作
     *
     * @param context    {@link Context}
     * @param lifecycleList {@link Fragment} 的生命周期容器, 可向框架中添加多个 {@link Fragment} 的生命周期类
     */
    void injectFragmentLifecycle(@NonNull Context context, @NonNull List<FragmentManager.FragmentLifecycleCallbacks> lifecycleList);

}
