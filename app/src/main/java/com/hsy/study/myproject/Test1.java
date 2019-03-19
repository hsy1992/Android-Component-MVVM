package com.hsy.study.myproject;

import android.os.Bundle;

import com.hsy.study.baselibrary.common.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.disposables.Disposable;

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
