package com.hsy.study.myproject.ui;


import android.view.View;

import com.hsy.study.baselibrary.base.BaseActivity;
import com.hsy.study.baselibrary.dagger.component.AppComponent;
import com.hsy.study.baselibrary.database.entity.User;
import com.hsy.study.baselibrary.utils.logger.Logger;
import com.hsy.study.myproject.R;
import com.hsy.study.myproject.UserContract;
import com.hsy.study.myproject.viewmodel.UserViewModel;
import com.hsy.study.myproject.di.DaggerUserComponent;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;

public class MainActivity extends BaseActivity<UserViewModel> implements UserContract.View {

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

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

    public void login(View view) {
        viewModel.insertUser();
    }

    public void query(View view) {
        for (User user : viewModel.query()) {
            Logger.errorInfo(user.toString());
        }
    }

    public void queryrx(View view) {
        viewModel.queryrx();
    }
}
