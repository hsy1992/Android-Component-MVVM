package com.hsy.study.myproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.hsy.study.baselibrary.base.AppApplication;
import com.hsy.study.baselibrary.lifecycle.GlobalLifecycleHandler;
import com.hsy.study.baselibrary.lifecycle.GlobalLifecycleObserver;
import com.hsy.study.baselibrary.utils.logger.Logger;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

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
}
