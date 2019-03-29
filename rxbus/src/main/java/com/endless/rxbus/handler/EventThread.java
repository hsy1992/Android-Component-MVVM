package com.endless.rxbus.handler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import androidx.annotation.IntDef;
import io.reactivex.Scheduler;

/**
 * 运行在那个线程
 * @author haosiyuan
 * @date 2019/3/26 3:45 PM
 */
@IntDef({EventThread.MAIN_THREAD, EventThread.NEW_THREAD, EventThread.IO, EventThread.SINGLE
        , EventThread.COMPUTATION, EventThread.TRAMPOLINE})
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD})
public @interface EventThread {

    /**
     * 主线程
     */
    int MAIN_THREAD = 0;

    /**
     * 新建线程
     */
    int NEW_THREAD = 1;

    /**
     * 阻塞式IO
     */
    int IO = 2;
    /**
     * 一个计算线程，不可以做IO操作
     */
    int COMPUTATION = 3;

    /**
     * 执行在当前线程，不会马上执行，放入队列
     */
    int TRAMPOLINE = 4;

    /**
     * 返回一个默认共享的线程 共享一个后台线程
     */
    int SINGLE = 5;

}
