package com.hsy.study.baselibrary.base.delegate;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * {@link android.app.Activity} activity生命周期代理，用于在生命周期插入逻辑
 * @author haosiyuan
 * @date 2019/2/14 1:38 PM
 */
public interface IActivityDelegate {

    String ACTIVITY_DELEGATE = "ACTIVITY_DELEGATE";

    void onCreate(@Nullable Bundle savedInstanceState);

    void onStart();

    void onResume();

    void onPause();

    void onStop();

    void onSaveInstanceState(@NonNull Bundle outState);

    void onDestroy();

}
