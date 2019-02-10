package com.hsy.study.myproject;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.hsy.study.baselibrary.base.AppApplication;
import com.hsy.study.baselibrary.lifecycle.GlobalLifecycleHandler;
import com.hsy.study.baselibrary.lifecycle.GlobalLifecycleObserver;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getLifecycle().addObserver(AppApplication.getmAppComponent().observer());
        getSupportFragmentManager().beginTransaction().add(R.id.fragment, new TestFragment()).commitAllowingStateLoss();
    }
}
