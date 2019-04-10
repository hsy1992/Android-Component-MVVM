package com.endless.study.myproject;

import com.endless.study.baselibrary.base.BaseActivity;
import com.endless.study.baselibrary.common.download.DownloadManager;
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

}
