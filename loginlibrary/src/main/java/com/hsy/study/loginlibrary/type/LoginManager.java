package com.hsy.study.loginlibrary.type;

import android.app.Activity;
import android.content.Context;

import com.hsy.study.loginlibrary.LoginConfig;
import com.hsy.study.loginlibrary.LoginException;
import com.hsy.study.loginlibrary.LoginType;
import com.hsy.study.loginlibrary.type.callback.ThreeAuthCallBack;
import com.hsy.study.loginlibrary.type.interfaces.ILogin;
import com.hsy.study.loginlibrary.type.interfaces.ILoginNormal;
import com.hsy.study.loginlibrary.type.interfaces.ILoginThree;
import com.hsy.study.loginlibrary.type.interfaces.IThreeAuthListener;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.UMShareAPI;

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
    public void loginNormal(String phone, String password) {

        ILoginNormal login = getNormalLogin();

        try {
            login.login(phone, password);
        } catch (LoginException e) {
            config.getExceptionCallBack().onCallBack(e.getTag(), e.getMessage());
        }
    }

    /**
     * 获取验证码
     * @param phone
     */
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
