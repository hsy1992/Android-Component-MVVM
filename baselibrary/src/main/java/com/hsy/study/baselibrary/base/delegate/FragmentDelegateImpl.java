package com.hsy.study.baselibrary.base.delegate;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * {@link IFragmentDelegate} 默认实现类
 * @author haosiyuan
 * @date 2019/2/14 2:11 PM
 */
public class FragmentDelegateImpl implements IFragmentDelegate {

    private IFragment iFragment;
    private Fragment fragment;
    private Unbinder unbinder;
    private FragmentManager fragmentManager;

    public FragmentDelegateImpl(@NonNull Fragment fragment, @NonNull FragmentManager fragmentManager) {
        this.iFragment = (IFragment) fragment;
        this.fragment = fragment;
        this.fragmentManager = fragmentManager;
    }

    @Override
    public void onAttach(@NonNull Context context) {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {}

    @Override
    public void onCreateView(@Nullable View view, @Nullable Bundle savedInstanceState) {
        //绑定到butterKnife
        if (view != null){
            unbinder = ButterKnife.bind(fragment, view);
        }
    }

    @Override
    public void onActivityCreate(@Nullable Bundle savedInstanceState) {
        iFragment.initView();
        iFragment.setData(savedInstanceState);
    }

    @Override
    public void onStart() {}

    @Override
    public void onResume() {}

    @Override
    public void onPause() {}

    @Override
    public void onStop() {}

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {}

    @Override
    public void onDestroyView() {
        if (unbinder != null && unbinder != Unbinder.EMPTY){
            unbinder.unbind();
        }
    }

    @Override
    public void onDestroy() {
        this.unbinder = null;
        this.fragmentManager = null;
        this.fragment = null;
        this.iFragment = null;
    }

    @Override
    public void onDetach() {

    }

    @Override
    public boolean isAdded() {
        return fragment != null && fragment.isAdded();
    }
}
