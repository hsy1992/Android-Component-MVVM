package com.endless.study.myproject;

import android.view.View;

import com.endless.rxbus.RxBus;
import com.endless.rxbus.annotation.Producer;
import com.endless.rxbus.annotation.Tag;
import com.endless.rxbus.handler.EventThread;
import com.endless.study.baselibrary.base.BaseActivity;
import com.endless.study.baselibrary.dagger.component.AppComponent;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * @author haosiyuan
 * @date 2019/2/10 8:01 PM
 */
public class Test1 extends BaseActivity {

    public static List<Disposable> list = new ArrayList<>();
    private Disposable observable;

    public void send(View view) {
        RxBus.getInstance().post("123", String.class);
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

    @Producer(tags = {@Tag("123")}, thread = EventThread.NEW_THREAD)
    public String sendadf() {
        return "123123";
    }
}
