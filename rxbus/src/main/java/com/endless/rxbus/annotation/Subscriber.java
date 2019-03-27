package com.endless.rxbus.annotation;

import com.endless.rxbus.handler.EventThread;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 订阅 {@link io.reactivex.Observable}
 * @author haosiyuan
 * @date 2019/3/26 5:05 PM
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Subscriber {

    /**
     * 标签
     * @return
     */
    Tag[] tags() default {};

    /**
     * 订阅在哪个线程线程
     * @return
     */
    int thread() default EventThread.MAIN_THREAD;

}
