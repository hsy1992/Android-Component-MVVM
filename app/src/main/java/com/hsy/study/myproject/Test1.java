package com.hsy.study.myproject;

import android.os.Bundle;

import com.hsy.study.baselibrary.base.AppApplication;
import com.hsy.study.baselibrary.utils.logger.Logger;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author haosiyuan
 * @date 2019/2/10 8:01 PM
 */
public class Test1 extends AppCompatActivity {

    public static List<Disposable> list = new ArrayList<>();
    private Disposable observable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
