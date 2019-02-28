package com.hsy.study.myproject;


import android.content.Intent;
import android.view.View;

import com.hsy.study.baselibrary.base.BaseActivity;
import com.hsy.study.baselibrary.dagger.component.AppComponent;
import com.hsy.study.baselibrary.utils.Preconditions;
import com.hsy.study.baselibrary.utils.logger.Logger;
import com.hsy.study.myproject.di.DaggerUserComponent;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends BaseActivity<UserViewModel> implements UserView{

    public void jump(View view) {
        startActivity(new Intent(this, Test1.class));
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
    }

    @Override
    public void initData() {
        viewModel.test();
    }

    @Override
    public void setUpAppComponent(@NonNull AppComponent appComponent) {
        DaggerUserComponent
                .builder()
                .context(this)
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    public void showUser() {
        showToast("user");
    }
}
