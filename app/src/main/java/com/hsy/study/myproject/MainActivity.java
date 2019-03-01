package com.hsy.study.myproject;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hsy.study.baselibrary.base.BaseActivity;
import com.hsy.study.baselibrary.dagger.component.AppComponent;
import com.hsy.study.baselibrary.utils.Preconditions;
import com.hsy.study.baselibrary.utils.logger.Logger;
import com.hsy.study.myproject.di.DaggerUserComponent;
import com.hsy.study.networkclientstate.NetWorkManager;
import com.hsy.study.networkclientstate.NetworkChangeListener;
import com.hsy.study.networkclientstate.NetworkState;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends BaseActivity<UserViewModel> implements UserView, NetworkChangeListener {

    public void jump(View view) {
            Activity activity = getActivity();
            ((ViewGroup)activity.getWindow().getDecorView()).addView(LayoutInflater.from(this)
                    .inflate(R.layout.activity_test,null));

    }

    @Inject
    RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onResume() {
        super.onResume();
        if (Test1.list.size() > 0){
            Logger.errorInfo("rxjava>>>>>>>>" + Test1.list.get(0).isDisposed());
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        showToast("initView");

        NetWorkManager.getInstance().addNetworkChangeListener(this);
    }

    @Override
    public void initData() {
        viewModel.test();
    }

    @Override
    public void setUpAppComponent(@NonNull AppComponent appComponent) {
        DaggerUserComponent
                .builder()
                .view(this)
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    public void showUser() {
        showToast("user");
    }

    public static Activity getActivity() {
        Class activityThreadClass = null;
        try {
            activityThreadClass = Class.forName("android.app.ActivityThread");
            Object activityThread = activityThreadClass.getMethod("currentActivityThread").invoke(null);
            Field activitiesField = activityThreadClass.getDeclaredField("mActivities");
            activitiesField.setAccessible(true);
            Map activities = (Map) activitiesField.get(activityThread);
            for (Object activityRecord : activities.values()) {
                Class activityRecordClass = activityRecord.getClass();
                Field pausedField = activityRecordClass.getDeclaredField("paused");
                pausedField.setAccessible(true);
                if (!pausedField.getBoolean(activityRecord)) {
                    Field activityField = activityRecordClass.getDeclaredField("activity");
                    activityField.setAccessible(true);
                    Activity activity = (Activity) activityField.get(activityRecord);
                    return activity;
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onNoNetwork() {
        Logger.errorInfo("无网>>>>>>>>");
    }

    @Override
    public void onNetReload() {
        Logger.errorInfo("网络重载>>>>>>>>");
    }

    @Override
    public void onNetGood() {

    }

    @Override
    public void onNetBad() {

    }

    @Override
    public void onNetChange(NetworkState state) {
        Logger.errorInfo(state.name() + ">>>>>>>>>>>>");
    }
}
