package com.hsy.study.baselibrary.utils.toast;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hsy.study.baselibrary.utils.Preconditions;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;

/**
 * 系统弹窗可以自定义扩展
 * @author haosiyuan
 * @date 2019/2/19 3:13 PM
 */
public class SystemToast implements IToastConfiguration {

    @Inject
    Gson gson;
    private Toast mToast;

    @Override
    public void toast(Context context, @NonNull String message, boolean isLong) {
        showToast(context, message, isLong);
    }

    @Override
    public void toast(Context context, @DrawableRes int res, boolean isLong) {
        getToast(context, "", isLong);
        mToast.getView().setBackgroundResource(res);
        mToast.show();
    }

    @Override
    public void toast(Context context, @NonNull List<String> messages, boolean isLong) {
        showToast(context, messages.size() > 0 ? messages.get(0) : "", isLong);
    }

    @Override
    public void toast(Context context, @NonNull Object object, Class<?> clazz, boolean isLong) {
        String json = gson.toJson(object, clazz);
        showToast(context, json, isLong);
    }


    @Override
    public void cancel() {
        if (mToast != null) {
            mToast.cancel();
        }
    }


    private void showToast(Context context, String message, boolean isLong) {
        getToast(context, message, isLong);
        mToast.setText(message);
        mToast.show();
    }

    private void getToast(Context context, String message, boolean isLong){
        if (mToast == null) {
            mToast = Toast.makeText(context, message, isLong ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT);
        }
        Preconditions.checkNotNull(mToast, "Toast can not be null");
    }
}
