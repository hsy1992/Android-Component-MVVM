package com.hsy.study.loginlibrary.type;

import com.hsy.study.loginlibrary.LoginConfig;
import com.hsy.study.loginlibrary.LoginException;
import com.hsy.study.loginlibrary.LoginType;
import com.hsy.study.loginlibrary.type.interfaces.ILogin;
import com.hsy.study.loginlibrary.type.interfaces.ILoginNormal;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;

/**
 * 登录模块管理
 * @author haosiyuan
 * @date 2019/3/15 3:49 PM
 */
public class LoginManager {

    /**
     * 缓存登录模块
     */
    private Map<Integer, ILogin> loginMap;

    /**
     * 登录配置
     */
    private LoginConfig config;

    private static class Instance {
        private static LoginManager loginManager = new LoginManager();
    }

    public static LoginManager getInstance() {
        return Instance.loginManager;
    }

    private LoginManager() {
        loginMap = new HashMap<>();
    }

    public LoginManager init(@NonNull LoginConfig config) {
        this.config = config;
        return this;
    }

    public void loginNormal(String phone, String password) {

        ILoginNormal login = getNormalLogin();
        try {
            login.login(phone, password);
        } catch (LoginException e) {
            config.getExceptionCallBack().onCallBack(e.getTag(), e.getMessage());
        }
    }

    public void getVerificationCode(String phone) {
        ILoginNormal login = getNormalLogin();

        try {
            login.getVerificationCode(phone);
        } catch (LoginException e) {
            config.getExceptionCallBack().onCallBack(e.getTag(), e.getMessage());
        }
    }


    /**
     * 获取常规登录
     * @return
     */
    private ILoginNormal getNormalLogin() {

        ILoginNormal login = (ILoginNormal) getLogin(LoginType.NORMAL);

        if (login != null) {
            return login;
        } else {
            throw new LoginException("LoginType", "LoginType is error");
        }
    }

    /**
     * 获取对应类型的登录
     * @param type
     * @return
     */
    private ILogin getLogin(@LoginType int type) {

        if (config == null) {
            throw new LoginException("getLogin", "config not init");
        }

        ILogin login = loginMap.get(type);

        if (login != null) {
            return login;
        } else {

            switch (type) {
                case LoginType.NORMAL:
                    loginMap.put(type, new NormalLogin(config));
                    return loginMap.get(type);
                default:
                    return null;
            }
        }
    }

}
