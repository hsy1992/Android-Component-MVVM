package com.hsy.study.baselibrary.utils.toast;

import android.content.Context;

import java.util.List;

import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;

/**
 * 操作提示
 * @author haosiyuan
 * @date 2019/2/19 3:09 PM
 */
public interface IToastConfiguration {

    /**
     * 提示
     * @param context
     * @param message
     * @param isLong
     */
    void toast(Context context, @NonNull String message, boolean isLong);

    /**
     * 提示
     * @param context
     * @param res
     * @param isLong
     */
    void toast(Context context, @DrawableRes int res, boolean isLong);

    /**
     * 提示
     * @param context
     * @param messages
     * @param isLong
     */
    void toast(Context context, @NonNull List<String> messages, boolean isLong);

    /**
     * 提示
     * @param context
     * @param object
     * @param isLong
     */
    void toast(Context context, @NonNull Object object, Class<?> clazz, boolean isLong);

    /**
     * 取消弹窗
     */
    void cancel();
}
