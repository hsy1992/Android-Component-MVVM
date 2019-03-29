package com.hsy.study.myproject.ui;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.endless.rxbus.annotation.Subscriber;
import com.endless.rxbus.annotation.Tag;
import com.endless.rxbus.handler.EventThread;
import com.hsy.study.baselibrary.base.BaseActivity;
import com.hsy.study.baselibrary.common.logger.Logger;
import com.hsy.study.baselibrary.dagger.component.AppComponent;
import com.hsy.study.myproject.R;
import com.hsy.study.myproject.Test1;
import com.hsy.study.myproject.UserContract;
import com.hsy.study.myproject.dagger.DaggerUserComponent;
import com.hsy.study.myproject.viewmodel.UserViewModel;
import com.hsy.study.networkclientstate.annotation.OnMobile;
import com.hsy.study.networkclientstate.annotation.OnNetReload;
import com.hsy.study.networkclientstate.annotation.OnNoNet;
import com.hsy.study.networkclientstate.annotation.OnRegister;
import com.hsy.study.networkclientstate.annotation.OnUnRegister;
import com.hsy.study.networkclientstate.annotation.OnWifi;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity<UserViewModel> implements UserContract.View {

    LiveData<String> liveData;

    Map<String, Set<String>> map = new HashMap<>();
    @BindView(R.id.textView)
    TextView textView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    MutableLiveData mediatorLiveData;

    @Override
    @OnRegister
    public void initView() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(UserViewModel.class);
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
    }

    public void test1(View view) {
        startActivity(new Intent(this, Test1.class));
    }

    @Subscriber
    public void showww(String message) {
        Logger.errorInfo(message);
    }

    @Subscriber(tags = {@Tag("123")}, thread = EventThread.NEW_THREAD)
    public void showww123(String message) {
        Logger.errorInfo(message + "more" + Thread.currentThread().getName());
    }

    @OnNoNet
    public void onNoNet() {
        showToast("无网");
    }

    @OnMobile
    public void OnMobile() {
        showToast("蜂窝");
    }

    @OnWifi
    public void OnWifi() {
        showToast("wifi");
    }

    @OnNetReload
    public void OnNetReload() {
        showToast("重载");
    }

    @OnUnRegister
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
