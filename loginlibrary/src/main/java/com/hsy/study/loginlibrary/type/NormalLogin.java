package com.hsy.study.loginlibrary.type;

import android.text.TextUtils;
import android.widget.Toast;

import com.hsy.study.loginlibrary.LoginConfig;
import com.hsy.study.loginlibrary.type.interfaces.ILogin;

/**
 * @author haosiyuan
 * @date 2019/3/11 4:44 PM
 */
public class NormalLogin implements ILogin {

    @Override
    public void login(LoginConfig config, String phone, String password) {

        if (TextUtils.isEmpty(phone)) {


        }

    }
}
