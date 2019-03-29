package com.endless.study.loginlibrary;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import androidx.annotation.IntDef;


/**
 * 登录类型
 * @author haosiyuan
 * @date 2019/3/11 4:30 PM
 */
@Documented
@IntDef({
        LoginType.NORMAL,
        LoginType.WE_CHAT,
        LoginType.QQ,
        LoginType.WEI_BO,
})
@Target({ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginType {

    /**
     * 普通登录
     */
    int NORMAL = 0;

    /**
     * 微信登录
     */
    int WE_CHAT = 1;

    /**
     * QQ登录
     */
    int QQ = 2;

    /**
     * 微博登录
     */
    int WEI_BO = 3;
}
