package com.hsy.study.baselibrary.lifecycle;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.hsy.study.baselibrary.base.delegate.FragmentDelegate;
import com.hsy.study.baselibrary.base.delegate.FragmentDelegateImpl;
import com.hsy.study.baselibrary.cache.Cache;
import com.hsy.study.baselibrary.cache.IntelligentCache;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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
    Cache<String, Object> mCache;

    @Inject
    public FragmentLifecycle() {}


    @Override
    public void onFragmentAttached(@NonNull FragmentManager fm, @NonNull Fragment f, @NonNull Context context) {

        FragmentDelegate fragmentDelegate = getFragmentDelegate();
        if (fragmentDelegate == null || !fragmentDelegate.isAdded()) {

            fragmentDelegate = new FragmentDelegateImpl(f, fm);
            //加入缓存
            mCache.put(IntelligentCache.getKeyOfKeep(FragmentDelegate.FRAGMENT_DELEGATE), fragmentDelegate);
        }
        fragmentDelegate.onAttach(context);
    }

    @Override
    public void onFragmentCreated(FragmentManager fm, Fragment f, Bundle savedInstanceState) {
        FragmentDelegate fragmentDelegate = getFragmentDelegate();
        if (fragmentDelegate != null) {
            fragmentDelegate.onCreate(savedInstanceState);
        }
    }

    @Override
    public void onFragmentViewCreated(FragmentManager fm, Fragment f, View v, Bundle savedInstanceState) {
        FragmentDelegate fragmentDelegate = getFragmentDelegate();
        if (fragmentDelegate != null) {
            fragmentDelegate.onCreateView(v, savedInstanceState);
        }
    }

    @Override
    public void onFragmentActivityCreated(FragmentManager fm, Fragment f, Bundle savedInstanceState) {
        FragmentDelegate fragmentDelegate = getFragmentDelegate();
        if (fragmentDelegate != null) {
            fragmentDelegate.onActivityCreate(savedInstanceState);
        }
    }

    @Override
    public void onFragmentStarted(FragmentManager fm, Fragment f) {
        FragmentDelegate fragmentDelegate = getFragmentDelegate();
        if (fragmentDelegate != null) {
            fragmentDelegate.onStart();
        }
    }

    @Override
    public void onFragmentResumed(FragmentManager fm, Fragment f) {
        FragmentDelegate fragmentDelegate = getFragmentDelegate();
        if (fragmentDelegate != null) {
            fragmentDelegate.onResume();
        }
    }

    @Override
    public void onFragmentPaused(FragmentManager fm, Fragment f) {
        FragmentDelegate fragmentDelegate = getFragmentDelegate();
        if (fragmentDelegate != null) {
            fragmentDelegate.onPause();
        }
    }

    @Override
    public void onFragmentStopped(FragmentManager fm, Fragment f) {
        FragmentDelegate fragmentDelegate = getFragmentDelegate();
        if (fragmentDelegate != null) {
            fragmentDelegate.onStop();
        }
    }

    @Override
    public void onFragmentSaveInstanceState(FragmentManager fm, Fragment f, Bundle outState) {
        FragmentDelegate fragmentDelegate = getFragmentDelegate();
        if (fragmentDelegate != null) {
            fragmentDelegate.onSaveInstanceState(outState);
        }
    }

    @Override
    public void onFragmentViewDestroyed(FragmentManager fm, Fragment f) {
        FragmentDelegate fragmentDelegate = getFragmentDelegate();
        if (fragmentDelegate != null) {
            fragmentDelegate.onDestroyView();
        }
    }

    @Override
    public void onFragmentDestroyed(FragmentManager fm, Fragment f) {
        FragmentDelegate fragmentDelegate = getFragmentDelegate();
        if (fragmentDelegate != null) {
            fragmentDelegate.onDestroy();
        }
    }

    @Override
    public void onFragmentDetached(FragmentManager fm, Fragment f) {
        FragmentDelegate fragmentDelegate = getFragmentDelegate();
        if (fragmentDelegate != null) {
            fragmentDelegate.onDetach();
        }
    }

    private FragmentDelegate getFragmentDelegate() {
        return (FragmentDelegate) mCache.get(IntelligentCache.getKeyOfKeep(FragmentDelegate.FRAGMENT_DELEGATE));
    }
}
