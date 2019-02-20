package com.hsy.study.baselibrary.base.delegate;

import android.app.Activity;
import android.os.Bundle;

import com.hsy.study.baselibrary.utils.CommonUtil;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * {@link IActivityDelegate} 默认实现类
 * @author haosiyuan
 * @date 2019/2/14 1:48 PM
 */
public class ActivityDelegateImpl implements IActivityDelegate {

    private Activity mActivity;
    private IActivity iActivity;
    private Unbinder unbinder;

    public ActivityDelegateImpl(@NonNull Activity activity) {
        this.mActivity = activity;
        this.iActivity = (IActivity) activity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        iActivity.setUpAppComponent(CommonUtil.getAppComponent(mActivity));

        int layoutResId = iActivity.getLayoutId();

        if (layoutResId != 0){
            mActivity.setContentView(layoutResId);
            unbinder = ButterKnife.bind(mActivity);
        }
        iActivity.initView();
        iActivity.initData();
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
    public void onDestroy() {
        mActivity = null;
        iActivity = null;
        if (unbinder != null && unbinder != Unbinder.EMPTY){
            unbinder.unbind();
        }
        unbinder = null;
    }
}
