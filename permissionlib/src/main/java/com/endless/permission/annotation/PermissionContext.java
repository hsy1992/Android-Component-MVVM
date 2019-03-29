package com.endless.permission.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 如果想在{@link android.app.Activity} 和{@link androidx.fragment.app.Fragment}
 * 外的对象使用需要用此注解
 * @author haosiyuan
 * @date 2019/3/29 4:48 PM
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PermissionContext {
}
