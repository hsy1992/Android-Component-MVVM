package com.endless.study.myproject.ui;


import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.endless.rxbus.annotation.Subscriber;
import com.endless.rxbus.annotation.Tag;
import com.endless.rxbus.handler.EventThread;
import com.endless.study.baselibrary.base.BaseActivity;
import com.endless.study.baselibrary.common.logger.Logger;
import com.endless.study.baselibrary.dagger.component.AppComponent;
import com.endless.study.myproject.R;
import com.endless.study.myproject.Test1;
import com.endless.study.myproject.UserContract;
import com.endless.study.myproject.dagger.DaggerUserComponent;
import com.endless.study.myproject.viewmodel.UserViewModel;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}