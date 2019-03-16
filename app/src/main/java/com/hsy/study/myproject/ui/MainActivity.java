package com.hsy.study.myproject.ui;

import android.view.View;

import com.hsy.study.baselibrary.base.BaseActivity;
import com.hsy.study.baselibrary.dagger.component.AppComponent;
import com.hsy.study.baselibrary.database.entity.User;
import com.hsy.study.baselibrary.utils.logger.Logger;
import com.hsy.study.loginlibrary.LoginConfig;
import com.hsy.study.loginlibrary.type.LoginManager;
import com.hsy.study.myproject.R;
import com.hsy.study.myproject.UserContract;
import com.hsy.study.myproject.hook.HookStartActivity;
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

        HookStartActivity.replaceActivityInstrumentation(this);
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

        LoginManager.getInstance()
                .init(LoginConfig.Builder.loginConfig(getApplication()).build())
                .getVerificationCode("13333333333");
//        viewModel.insertUser();
//        HookToast.toast(this, "1234");
//        startActivity(new Intent(this, Test1.class));
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
