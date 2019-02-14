package com.hsy.study.baselibrary.lifecycle;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

/**
 *  {@link Application.ActivityLifecycleCallbacks} 默认实现类
 *  默认生命周期执行，通过{@link com.hsy.study.baselibrary.base.delegate.ActivityDelegate }进行管理
 * @author haosiyuan
 * @date 2019/2/14 3:24 PM
 */
public class ActivityLifecycle implements Application.ActivityLifecycleCallbacks {

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
}
