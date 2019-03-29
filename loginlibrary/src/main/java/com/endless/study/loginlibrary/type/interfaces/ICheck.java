package com.endless.study.loginlibrary.type.interfaces;

import com.endless.study.loginlibrary.LoginConfig;

/**
 * 检测
 * @author haosiyuan
 * @date 2019/3/14 6:53 PM
 */
public interface ICheck {

    void checkPhone(LoginConfig config, String phone);

    void checkPassword(LoginConfig config, String password);

    void checkVerificationCode(LoginConfig config, String verificationCode);

    boolean canGetVerificationCode(LoginConfig config);

}
