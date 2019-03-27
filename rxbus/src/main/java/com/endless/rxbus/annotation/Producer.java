package com.endless.rxbus.annotation;

import com.endless.rxbus.handler.EventThread;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 提供 {@link io.reactivex.Observable} 的注解
 * @author haosiyuan
 * @date 2019/3/26 4:58 PM
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Producer {

    /**
     * 标签
     * @return
     */
    Tag[] tags() default {};

    /**
     * 运行在哪个线程线程
     * @return
     */
    int thread() default EventThread.MAIN_THREAD;
}
