package com.endless.study.loginlibrary.type.interfaces;

/**
 * 普通登录
 * @author haosiyuan
 * @date 2019/3/15 9:20 AM
 */
public interface ILoginNormal extends ILogin{

    void login(String phone, String password);

    void loginCode(String phone, String verificationCode);

    void getVerificationCode(String phone);

    void register(String phone, String password, String verificationCode);

    void register(String phone, String verificationCode);
}
