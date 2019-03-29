package com.endless.study.loginlibrary.type.callback;

import android.app.Application;
import android.widget.Toast;

import com.endless.study.loginlibrary.type.interfaces.IExceptionCallBack;

/**
 * 默认异常回调
 * @author haosiyuan
 * @date 2019/3/15 4:07 PM
 */
public class DefaultExceptionCallBack implements IExceptionCallBack {

    private Application application;

    public DefaultExceptionCallBack(Application application) {
        this.application = application;
    }

    @Override
    public void onCallBack(String tag, String message) {
        Toast.makeText(application, message, Toast.LENGTH_SHORT).show();
    }
}
