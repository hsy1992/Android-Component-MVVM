package com.hsy.study.myproject.ui;


import android.os.Looper;

import com.hsy.study.baselibrary.base.BaseActivity;
import com.hsy.study.baselibrary.common.logger.Logger;
import com.hsy.study.baselibrary.dagger.component.AppComponent;
import com.hsy.study.myproject.R;
import com.hsy.study.myproject.UserContract;
import com.hsy.study.myproject.dagger.DaggerUserComponent;
import com.hsy.study.myproject.viewmodel.UserViewModel;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProviders;

public class MainActivity extends BaseActivity<UserViewModel> implements UserContract.View {

    LiveData<String> liveData;

    Map<String, Set<String>> map = new HashMap<>();

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
        showToast("user");
    }

}
