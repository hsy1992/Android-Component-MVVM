package com.endless.study.loginlibrary;

/**
 * 模块所报异常
 * @author haosiyuan
 * @date 2019/3/15 4:48 PM
 */
public class LoginException extends RuntimeException {

    public LoginException(String tag, String message) {
        super(message, new Throwable(tag));
    }

    public String getTag() {
        return getCause().getMessage();
    }
}
