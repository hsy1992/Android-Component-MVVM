package com.hsy.study.baselibrary.lifecycle;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.hsy.study.baselibrary.base.AppManager;
import com.hsy.study.baselibrary.base.delegate.ActivityDelegate;
import com.hsy.study.baselibrary.base.delegate.ActivityDelegateImpl;
import com.hsy.study.baselibrary.cache.Cache;
import com.hsy.study.baselibrary.cache.IntelligentCache;
import com.hsy.study.baselibrary.config.ConfigModule;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import dagger.Lazy;

/**
 *  {@link Application.ActivityLifecycleCallbacks} 默认实现类
 *  默认生命周期执行，通过{@link com.hsy.study.baselibrary.base.delegate.ActivityDelegate }进行管理
 * @author haosiyuan
 * @date 2019/2/14 3:24 PM
 */
@Singleton
public class ActivityLifecycle implements Application.ActivityLifecycleCallbacks {

    @Inject
    Application mApplication;
    @Inject
    Cache<String, Object> mCache;
    @Inject
    Lazy<FragmentManager.FragmentLifecycleCallbacks> mFragmentLifecycle;
    @Inject
    Lazy<List<FragmentManager.FragmentLifecycleCallbacks>> mFragmentLifecycles;

    @Inject
    public ActivityLifecycle() {
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        //加入AppManager统一管理
        AppManager.getInstance().addActivity(activity);

        //从缓存中读取 ActivityDelegate
        ActivityDelegate activityDelegate = getActivityDelegate();
        if (activityDelegate == null) {
            activityDelegate = new ActivityDelegateImpl(activity);
            mCache.put(IntelligentCache.getKeyOfKeep(ActivityDelegate.ACTIVITY_DELEGATE), activityDelegate);
        }
        activityDelegate.onCreate(savedInstanceState);

        //注册Fragment 生命周期
        registerFragmentCallbacks(activity);
    }

    @Override
    public void onActivityStarted(Activity activity) {
        ActivityDelegate activityDelegate = getActivityDelegate();
        if (activityDelegate != null) {
            activityDelegate.onStart();
        }
    }

    @Override
    public void onActivityResumed(Activity activity) {
        ActivityDelegate activityDelegate = getActivityDelegate();
        if (activityDelegate != null) {
            activityDelegate.onResume();
        }
    }

    @Override
    public void onActivityPaused(Activity activity) {
        ActivityDelegate activityDelegate = getActivityDelegate();
        if (activityDelegate != null) {
            activityDelegate.onPause();
        }
    }

    @Override
    public void onActivityStopped(Activity activity) {
        ActivityDelegate activityDelegate = getActivityDelegate();
        if (activityDelegate != null) {
            activityDelegate.onStop();
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        ActivityDelegate activityDelegate = getActivityDelegate();
        if (activityDelegate != null) {
            activityDelegate.onSaveInstanceState(outState);
        }
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        ActivityDelegate activityDelegate = getActivityDelegate();
        if (activityDelegate != null) {
            activityDelegate.onDestroy();
        }
    }

    /**
     * 获取 缓存中{@link ActivityDelegate}
     * @return
     */
    private ActivityDelegate getActivityDelegate() {
        return (ActivityDelegate) mCache.get(IntelligentCache.getKeyOfKeep(ActivityDelegate.ACTIVITY_DELEGATE));
    }

    /**
     * 为{@link Activity} 中Fragment添加生命周期
     * @param activity
     */
    private void registerFragmentCallbacks(Activity activity){

        if (activity instanceof FragmentActivity) {

            //添加注册Fragment 生命周期管理
            ((FragmentActivity) activity).getSupportFragmentManager().registerFragmentLifecycleCallbacks(mFragmentLifecycle.get(), true);

            //查看缓存中的配置 并注入Fragment的生命周期
            String configKey = IntelligentCache.getKeyOfKeep(ConfigModule.class.getName());
            if (mCache.containsKey(configKey)) {
                //获取所有缓存配置 只执行一次 之后移除缓存
                List<ConfigModule> modules = (List<ConfigModule>) mCache.get(configKey);

                for (ConfigModule module : modules) {
                    module.injectFragmentLifecycle(mApplication, mFragmentLifecycles.get());
                }

                mCache.remove(configKey);
            }

            //注册自定义的Fragment生命周期
            for (FragmentManager.FragmentLifecycleCallbacks fragmentLifecycle : mFragmentLifecycles.get()) {
                ((FragmentActivity) activity).getSupportFragmentManager().registerFragmentLifecycleCallbacks(fragmentLifecycle, true);
            }
        }

    }
}
