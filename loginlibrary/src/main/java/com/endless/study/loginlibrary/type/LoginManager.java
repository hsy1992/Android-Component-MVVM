package com.endless.study.loginlibrary.type;

import android.app.Activity;

import com.endless.study.loginlibrary.LoginConfig;
import com.endless.study.loginlibrary.LoginException;
import com.endless.study.loginlibrary.LoginType;
import com.endless.study.loginlibrary.type.callback.ThreeAuthCallBack;
import com.endless.study.loginlibrary.type.interfaces.ILogin;
import com.endless.study.loginlibrary.type.interfaces.ILoginNormal;
import com.endless.study.loginlibrary.type.interfaces.ILoginThree;
import com.endless.study.loginlibrary.type.interfaces.IThreeAuthListener;

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

    /**
     * 三方回调
     */
    private ThreeAuthCallBack callBack;

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

    /**
     * 用户密码登录
     * @param phone
     * @param password
     */
    public boolean loginNormal(String phone, String password) {

        ILoginNormal login = getNormalLogin();

        try {
            login.login(phone, password);
        } catch (LoginException e) {
            config.getExceptionCallBack().onCallBack(e.getTag(), e.getMessage());
            return false;
        }

        return true;
    }

    /**
     * 用户密码登录
     * @param phone
     * @param verificationCode
     */
    public boolean loginCode(String phone, String verificationCode) {

        ILoginNormal login = getNormalLogin();

        try {
            login.loginCode(phone, verificationCode);
        } catch (LoginException e) {
            config.getExceptionCallBack().onCallBack(e.getTag(), e.getMessage());
            return false;
        }

        return true;
    }

    /**
     * 获取验证码
     * @param phone
     */
    public boolean getVerificationCode(String phone) {

        ILoginNormal login = getNormalLogin();

        try {
            login.getVerificationCode(phone);
        } catch (LoginException e) {
            config.getExceptionCallBack().onCallBack(e.getTag(), e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * 用户密码注册
     * @param phone
     * @param password
     */
    public boolean registerNormal(String phone, String password) {

        ILoginNormal login = getNormalLogin();

        try {
            login.register(phone, password);
        } catch (LoginException e) {
            config.getExceptionCallBack().onCallBack(e.getTag(), e.getMessage());
            return false;
        }

        return true;
    }

    /**
     * 用户密码验证码祖册
     * @param phone
     * @param verificationCode
     */
    public boolean registerCode(String phone, String password, String verificationCode) {

        ILoginNormal login = getNormalLogin();

        try {
            login.register(phone, password, verificationCode);
        } catch (LoginException e) {
            config.getExceptionCallBack().onCallBack(e.getTag(), e.getMessage());
            return false;
        }

        return true;
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
                case LoginType.QQ:
                    loginMap.put(type, new QQLogin());
                    return loginMap.get(type);
                case LoginType.WE_CHAT:
                    loginMap.put(type, new WeChatLogin());
                    return loginMap.get(type);
                case LoginType.WEI_BO:
                    loginMap.put(type, new WeiBoLogin());
                    return loginMap.get(type);
                default:
                    return null;
            }
        }
    }

    /**
     * 获取三方登录
     * @return
     */
    private ILoginThree getThreeLogin(@LoginType int type) {

        ILoginThree login = (ILoginThree) getLogin(type);

        if (login != null) {
            return login;
        } else {
            throw new LoginException("LoginType", "LoginType is error");
        }
    }

    /**
     * 初始化三方登录
     */
    public UMInit initThreeLogin() {
        return new UMInit();
    }

    /**
     * 登录QQ
     * @param activity
     * @param listener
     */
    public void loginQQ(Activity activity, IThreeAuthListener listener) {
        getThreeLogin(LoginType.QQ).login(config, activity, getThreeAuthCallBack(listener));
    }

    /**
     * 登录微信
     * @param activity
     * @param listener
     */
    public void loginWeChat(Activity activity, IThreeAuthListener listener) {
        getThreeLogin(LoginType.WE_CHAT).login(config, activity, getThreeAuthCallBack(listener));
    }

    /**
     * 登录微博
     * @param activity
     * @param listener
     */
    public void loginWeiBo(Activity activity, IThreeAuthListener listener) {
        getThreeLogin(LoginType.WEI_BO).login(config, activity, getThreeAuthCallBack(listener));
    }

    /**
     * 获取回调
     * @param listener
     * @return
     */
    private ThreeAuthCallBack getThreeAuthCallBack(IThreeAuthListener listener) {

        if (callBack == null) {
            callBack = new ThreeAuthCallBack(listener);
        }
        return callBack;
    }

}
