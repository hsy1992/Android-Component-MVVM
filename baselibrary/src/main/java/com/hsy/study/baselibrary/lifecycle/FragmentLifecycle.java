package com.hsy.study.baselibrary.lifecycle;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.fragment.app.FragmentManager;

/**
 * {@link FragmentManager.FragmentLifecycleCallbacks} 默认实现类
 * 默认生命周期执行，通过{@link com.hsy.study.baselibrary.base.delegate.FragmentDelegate }进行管理
 * @author haosiyuan
 * @date 2019/2/14 3:25 PM
 */
@Singleton
public class FragmentLifecycle extends FragmentManager.FragmentLifecycleCallbacks {

    @Inject
    public FragmentLifecycle() {}


}
