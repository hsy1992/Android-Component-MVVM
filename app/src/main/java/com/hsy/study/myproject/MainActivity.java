package com.hsy.study.myproject;


import android.content.Intent;
import android.view.View;

import com.hsy.study.baselibrary.base.BaseActivity;
import com.hsy.study.baselibrary.utils.logger.Logger;


public class MainActivity extends BaseActivity {

    public void jump(View view) {
        startActivity(new Intent(this, Test1.class));
    }

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

    }
}
