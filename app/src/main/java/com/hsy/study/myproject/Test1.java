package com.hsy.study.myproject;

import android.os.Bundle;
import android.view.View;

import com.endless.rxbus.RxBus;
import com.endless.rxbus.annotation.Producer;
import com.hsy.study.baselibrary.base.BaseActivity;
import com.hsy.study.baselibrary.common.logger.Logger;
import com.hsy.study.baselibrary.dagger.component.AppComponent;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.disposables.Disposable;

/**
 * @author haosiyuan
 * @date 2019/2/10 8:01 PM
 */
public class Test1 extends BaseActivity {

    public static List<Disposable> list = new ArrayList<>();
    private Disposable observable;


    public void send(View view) {
        RxBus.getInstance().post(String.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_test;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void setUpAppComponent(@NonNull AppComponent appComponent) {
    }

    @Producer
    public String sendadf() {
        return "123123";
    }
}
