package com.hsy.study.baselibrary.lifecycle;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.hsy.study.baselibrary.base.delegate.IFragmentDelegate;
import com.hsy.study.baselibrary.base.delegate.FragmentDelegateImpl;
import com.hsy.study.baselibrary.base.delegate.IFragment;
import com.hsy.study.baselibrary.cache.local.ICache;
import com.hsy.study.baselibrary.cache.local.IntelligentCache;
import com.hsy.study.baselibrary.utils.UtilPreconditions;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

/**
 * {@link FragmentManager.FragmentLifecycleCallbacks} 默认实现类
 * 默认生命周期执行，通过{@link IFragmentDelegate }进行管理
 * @author haosiyuan
 * @date 2019/2/14 3:25 PM
 */
@Singleton
public class FragmentLifecycle extends FragmentManager.FragmentLifecycleCallbacks {

    @Inject
    public FragmentLifecycle() {}

    @Override
    public void onFragmentAttached(@NonNull FragmentManager fm, @NonNull Fragment f, @NonNull Context context) {
        if (f instanceof IFragment) {
            IFragmentDelegate fragmentDelegate = getFragmentDelegate(f);
            if (fragmentDelegate == null || !fragmentDelegate.isAdded()) {

                fragmentDelegate = new FragmentDelegateImpl(f, fm);
                ICache<String, Object> cache = getCacheFromFragment((IFragment) f);
                //加入缓存
                cache.put(IntelligentCache.getKeyOfKeep(IFragmentDelegate.FRAGMENT_DELEGATE), fragmentDelegate);
            }
            fragmentDelegate.onAttach(context);
        }
    }

    @Override
    public void onFragmentCreated(FragmentManager fm, Fragment f, Bundle savedInstanceState) {
        IFragmentDelegate fragmentDelegate = getFragmentDelegate(f);
        if (fragmentDelegate != null) {
            fragmentDelegate.onCreate(savedInstanceState);
        }
    }

    @Override
    public void onFragmentViewCreated(FragmentManager fm, Fragment f, View v, Bundle savedInstanceState) {
        IFragmentDelegate fragmentDelegate = getFragmentDelegate(f);
        if (fragmentDelegate != null) {
            fragmentDelegate.onCreateView(v, savedInstanceState);
        }
    }

    @Override
    public void onFragmentActivityCreated(FragmentManager fm, Fragment f, Bundle savedInstanceState) {
        IFragmentDelegate fragmentDelegate = getFragmentDelegate(f);
        if (fragmentDelegate != null) {
            fragmentDelegate.onActivityCreate(savedInstanceState);
        }
    }

    @Override
    public void onFragmentStarted(FragmentManager fm, Fragment f) {
        IFragmentDelegate fragmentDelegate = getFragmentDelegate(f);
        if (fragmentDelegate != null) {
            fragmentDelegate.onStart();
        }
    }

    @Override
    public void onFragmentResumed(FragmentManager fm, Fragment f) {
        IFragmentDelegate fragmentDelegate = getFragmentDelegate(f);
        if (fragmentDelegate != null) {
            fragmentDelegate.onResume();
        }
    }

    @Override
    public void onFragmentPaused(FragmentManager fm, Fragment f) {
        IFragmentDelegate fragmentDelegate = getFragmentDelegate(f);
        if (fragmentDelegate != null) {
            fragmentDelegate.onPause();
        }
    }

    @Override
    public void onFragmentStopped(FragmentManager fm, Fragment f) {
        IFragmentDelegate fragmentDelegate = getFragmentDelegate(f);
        if (fragmentDelegate != null) {
            fragmentDelegate.onStop();
        }
    }

    @Override
    public void onFragmentSaveInstanceState(FragmentManager fm, Fragment f, Bundle outState) {
        IFragmentDelegate fragmentDelegate = getFragmentDelegate(f);
        if (fragmentDelegate != null) {
            fragmentDelegate.onSaveInstanceState(outState);
        }
    }

    @Override
    public void onFragmentViewDestroyed(FragmentManager fm, Fragment f) {
        IFragmentDelegate fragmentDelegate = getFragmentDelegate(f);
        if (fragmentDelegate != null) {
            fragmentDelegate.onDestroyView();
        }
    }

    @Override
    public void onFragmentDestroyed(FragmentManager fm, Fragment f) {
        IFragmentDelegate fragmentDelegate = getFragmentDelegate(f);
        if (fragmentDelegate != null) {
            fragmentDelegate.onDestroy();
        }
    }

    @Override
    public void onFragmentDetached(FragmentManager fm, Fragment f) {
        IFragmentDelegate fragmentDelegate = getFragmentDelegate(f);
        if (fragmentDelegate != null) {
            fragmentDelegate.onDetach();
        }
    }

    private IFragmentDelegate getFragmentDelegate(Fragment fragment) {
        IFragmentDelegate fragmentDelegate = null;

        if (fragment instanceof IFragment){
            ICache<String, Object> cache = getCacheFromFragment((IFragment) fragment);
            fragmentDelegate = (IFragmentDelegate) cache.get(IntelligentCache.getKeyOfKeep(IFragmentDelegate.FRAGMENT_DELEGATE));
        }
        return fragmentDelegate;
    }

    @NonNull
    private ICache<String, Object> getCacheFromFragment(IFragment fragment) {
        ICache<String, Object> cache = fragment.getCacheData();
        UtilPreconditions.checkNotNull(cache, "%s cannot be null on Fragment", ICache.class.getName());
        return cache;
    }
}
