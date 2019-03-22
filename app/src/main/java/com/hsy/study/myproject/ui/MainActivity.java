package com.hsy.study.myproject.ui;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;

import com.hsy.study.baselibrary.base.BaseActivity;
import com.hsy.study.baselibrary.dagger.component.AppComponent;
import com.hsy.study.baselibrary.database.entity.User;
import com.hsy.study.baselibrary.common.logger.Logger;
import com.hsy.study.myproject.R;
import com.hsy.study.myproject.Test1;
import com.hsy.study.myproject.UserContract;
import com.hsy.study.myproject.chajian.Test2;
import com.hsy.study.myproject.di.DaggerUserComponent;
import com.hsy.study.myproject.viewmodel.UserViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;

public class MainActivity extends BaseActivity<UserViewModel> implements UserContract.View {


    @BindView(R.id.et_id)
    EditText etId;

    LiveData<String> liveData;
    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    MutableLiveData mediatorLiveData;
    @Override
    public void initView() {

//        HookStartActivity.replaceActivityInstrumentation(this);
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
//        LoginManager.getInstance()
//                .init(LoginConfig.Builder.loginConfig(getApplication()).build())
//                .getVerificationCode("13333333333");

//        HookToast.toast(this, "1234");
        startActivity(new Intent(this, Test2.class));
    }

    public void query(View view) {
        for (User user : viewModel.query()) {
            Logger.errorInfo(user.toString());
        }
    }

    public void queryrx(View view) {
        viewModel.queryrx().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                Logger.errorInfo(users.size() + ">>>>>>>>>");
            }
        });
    }

    public void insertUser(View view) {
        viewModel.insertUser(Integer.parseInt(etId.getText().toString()));
    }

}
