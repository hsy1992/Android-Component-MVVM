package com.endless.study.myproject.app;

import android.app.Application;
import android.content.Context;

import com.endless.study.baselibrary.base.delegate.IAppLifecycle;
import com.endless.study.baselibrary.config.IConfigModule;
import com.endless.study.baselibrary.dagger.module.GlobalConfigModule;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;

/**
 * @author haosiyuan
 * @date 2019-06-28 14:13
 * info : 全局配置
 */
public class AppConfigModule implements IConfigModule {

    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlobalConfigModule.Builder builder) {

    }

    @Override
    public void injectAppLifecycle(@NonNull Context context, @NonNull List<IAppLifecycle> lifecycle) {
        //添加Application 配置
        lifecycle.add(new EndlessApplication());
    }

    @Override
    public void injectActivityLifecycle(@NonNull Context context, @NonNull List<Application.ActivityLifecycleCallbacks> lifecycleList) {

    }

    @Override
    public void injectFragmentLifecycle(@NonNull Context context, @NonNull List<FragmentManager.FragmentLifecycleCallbacks> lifecycleList) {

    }
}
