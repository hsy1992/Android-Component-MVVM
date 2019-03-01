package com.hsy.study.baselibrary.lifecycle;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.hsy.study.baselibrary.base.AppManager;
import com.hsy.study.baselibrary.base.delegate.IActivityDelegate;
import com.hsy.study.baselibrary.base.delegate.ActivityDelegateImpl;
import com.hsy.study.baselibrary.base.delegate.IActivity;
import com.hsy.study.baselibrary.cache.ICache;
import com.hsy.study.baselibrary.cache.IntelligentCache;
import com.hsy.study.baselibrary.config.IConfigModule;
import com.hsy.study.baselibrary.utils.Preconditions;
import com.hsy.study.baselibrary.utils.logger.Logger;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import dagger.Lazy;

/**
 *  {@link Application.ActivityLifecycleCallbacks} 默认实现类
 *  默认生命周期执行，通过{@link IActivityDelegate }进行管理
 * @author haosiyuan
 * @date 2019/2/14 3:24 PM
 */
@Singleton
public class ActivityLifecycle implements Application.ActivityLifecycleCallbacks {

    @Inject
    Application mApplication;
    @Inject
    Lazy<FragmentManager.FragmentLifecycleCallbacks> mFragmentLifecycle;
    @Inject
    Lazy<List<FragmentManager.FragmentLifecycleCallbacks>> mFragmentLifecycleList;
    @Inject
    ICache<String, Object> mCache;

    @Inject
    public ActivityLifecycle() {}

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        //加入AppManager统一管理
        AppManager.getInstance().addActivity(activity);

        //从缓存中读取 ActivityDelegate 先从Activity中查找缓存ActivityDelegate 如果未缓存则使用默认ActivityDelegateImpl
        if (activity instanceof IActivity) {
            IActivityDelegate activityDelegate = getActivityDelegate(activity);
            if (activityDelegate == null) {
                activityDelegate = new ActivityDelegateImpl(activity);
                ICache<String, Object> cache = getActivityCache((IActivity) activity);
                cache.put(IntelligentCache.getKeyOfKeep(IActivityDelegate.ACTIVITY_DELEGATE), activityDelegate);
            }
            activityDelegate.onCreate(savedInstanceState);
        }

        //注册Fragment 生命周期
        registerFragmentCallbacks(activity);
    }

    @Override
    public void onActivityStarted(Activity activity) {
        IActivityDelegate activityDelegate = getActivityDelegate(activity);
        if (activityDelegate != null) {
            activityDelegate.onStart();
        }
    }

    @Override
    public void onActivityResumed(Activity activity) {
        AppManager.getInstance().setCurrentActivity(activity);

        IActivityDelegate activityDelegate = getActivityDelegate(activity);
        if (activityDelegate != null) {
            activityDelegate.onResume();
        }
    }

    @Override
    public void onActivityPaused(Activity activity) {
        IActivityDelegate activityDelegate = getActivityDelegate(activity);
        if (activityDelegate != null) {
            activityDelegate.onPause();
        }
    }

    @Override
    public void onActivityStopped(Activity activity) {
        //将当前前台Activity设为Null
        if (AppManager.getInstance().getCurrentActivity() == activity) {
            AppManager.getInstance().setCurrentActivity(null);
        }

        IActivityDelegate activityDelegate = getActivityDelegate(activity);
        if (activityDelegate != null) {
            activityDelegate.onStop();
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        IActivityDelegate activityDelegate = getActivityDelegate(activity);
        if (activityDelegate != null) {
            activityDelegate.onSaveInstanceState(outState);
        }
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        AppManager.getInstance().removeActivity(activity);

        IActivityDelegate activityDelegate = getActivityDelegate(activity);
        if (activityDelegate != null) {
            activityDelegate.onDestroy();
        }
    }

    /**
     * 获取 缓存中{@link IActivityDelegate}
     * @return
     */
    private IActivityDelegate getActivityDelegate(Activity activity) {
        IActivityDelegate activityDelegate = null;

        if (activity instanceof IActivity){
            ICache<String, Object> cache = getActivityCache((IActivity) activity);
            activityDelegate = (IActivityDelegate) cache.get(IntelligentCache.getKeyOfKeep(IActivityDelegate.ACTIVITY_DELEGATE));
        }
        return activityDelegate;
    }

    /**
     * 获取{@link Activity} 中的缓存
     * @param activity
     * @return
     */
    private ICache<String, Object> getActivityCache(IActivity activity){
        ICache<String, Object> cache = activity.getCacheData();
        Preconditions.checkNotNull(cache, "%s cannot be null on Activity", ICache.class.getName());
        return cache;
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
            String configKey = IntelligentCache.getKeyOfKeep(IConfigModule.class.getName());
            if (mCache.containsKey(configKey)) {
                //获取所有缓存配置 只执行一次 之后移除缓存
                List<IConfigModule> modules = (List<IConfigModule>) mCache.get(configKey);
                Preconditions.checkNotNull(modules, "modules is null");
                for (IConfigModule module : modules) {
                    module.injectFragmentLifecycle(mApplication, mFragmentLifecycleList.get());
                }
                mCache.remove(configKey);
            }

            //注册自定义的Fragment生命周期
            for (FragmentManager.FragmentLifecycleCallbacks fragmentLifecycle : mFragmentLifecycleList.get()) {
                ((FragmentActivity) activity).getSupportFragmentManager().registerFragmentLifecycleCallbacks(fragmentLifecycle, true);
            }
        }
    }

}
