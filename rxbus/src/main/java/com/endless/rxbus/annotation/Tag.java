package com.endless.rxbus.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * tag 标签 希望tag都要添加
 * @author haosiyuan
 * @date 2019/3/26 5:02 PM
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Tag {

    String DEFAULT = "RxBus_Tag";

    String value() default DEFAULT;
}
