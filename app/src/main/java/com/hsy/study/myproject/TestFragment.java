package com.hsy.study.myproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hsy.study.baselibrary.base.AppApplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * @author haosiyuan
 * @date 2019/1/29 3:44 PM
 */
public class TestFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getLifecycle().addObserver(AppApplication.getmAppComponent().observer());
        return inflater.inflate(R.layout.fragment, null);
    }

}
