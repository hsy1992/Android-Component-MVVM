package com.hsy.study.loginlibrary.type.interfaces;

import com.hsy.study.loginlibrary.LoginType;
import com.hsy.study.loginlibrary.entity.UserEntity;

/**
 * @author haosiyuan
 * @date 2019/3/17 11:20 AM
 */
public interface IThreeAuthListener {

    /**
     * 登录开始
     * @param type
     */
    void onLoginStart(@LoginType int type);

    /**
     * 登录完成
     * @param type
     * @param userEntity
     */
    void onLoginComplete(@LoginType int type, UserEntity userEntity);

    /**
     * 登录错误
     * @param type
     */
    void onLoginError(@LoginType int type);

    /**
     * 登录取消
     * @param type
     */
    void onLoginCancel(@LoginType int type);
}
