package com.hsy.study.baselibrary.ui;

import android.content.Context;

import androidx.annotation.NonNull;

/**
 * IView 由ViewModel持有 开放给ViewModel的功能
 * @author haosiyuan
 * @date 2019/2/28 10:41 AM
 */
public interface IView {

    /**
     * 基础提示
     * @param message
     */
    void showToast(@NonNull String message);

    /**
     * 获取上下文
     * @return
     */
    Context getContext();
}
