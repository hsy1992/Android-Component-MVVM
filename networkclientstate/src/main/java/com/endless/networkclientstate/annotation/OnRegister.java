package com.endless.networkclientstate.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 注入监听方法 建议注解在 OnCreate 或者初始方法
 * @author haosiyuan
 * @date 2019/3/28 2:10 PM
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OnRegister {

}
