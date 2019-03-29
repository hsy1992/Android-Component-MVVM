package com.endless.study.loginlibrary.type;


import com.endless.study.loginlibrary.LoginConfig;
import com.endless.study.loginlibrary.type.interfaces.ICheck;
import com.endless.study.loginlibrary.type.interfaces.ILoginNormal;

import androidx.annotation.NonNull;

/**
 * 常规登录
 * @author haosiyuan
 * @date 2019/3/11 4:44 PM
 */
class NormalLogin implements ILoginNormal {

    private LoginConfig config;

    private ICheck iCheck;

    NormalLogin(LoginConfig config) {

        this(config, new DefaultCheck());
    }

    NormalLogin(LoginConfig config, @NonNull ICheck iCheck) {

        this.config = config;
        this.iCheck = iCheck;
    }

    @Override
    public void getVerificationCode(String phone) {

        if (iCheck.canGetVerificationCode(config)) {

            iCheck.checkPhone(config, phone);
        }
    }

    @Override
    public void loginCode(String phone, String verificationCode ) {

        iCheck.checkPhone(config, phone);
    }


    @Override
    public void login(String phone, String password) {

        iCheck.checkPhone(config, phone);

        iCheck.checkPassword(config, password);
    }

    @Override
    public void register(String phone, String verificationCode) {

        iCheck.checkPhone(config, phone);

        iCheck.checkVerificationCode(config, verificationCode);
    }

    @Override
    public void register(String phone, String password, String verificationCode) {

        iCheck.checkPhone(config, phone);

        iCheck.checkPassword(config, password);

        iCheck.checkVerificationCode(config, verificationCode);
    }
}
