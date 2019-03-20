package com.hsy.study.baselibrary.common.rxjava.scheduler;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * RxJava中线程调度
 * 添加操作线程
 * @author haosiyuan
 * @date 2019/3/20 10:34 AM
 */
public class SchedulersUtil {

    /**
     * IO线程执行
     * @param <T>
     * @return
     */
    public static <T> BaseScheduler<T> ioToMain() {
        return new BaseScheduler<>(Schedulers.io(), AndroidSchedulers.mainThread());
    }

    /**
     * main -> main
     * @param <T>
     * @return
     */
    public static <T> BaseScheduler<T> mainToMain() {
        return new BaseScheduler<>(AndroidSchedulers.mainThread(), AndroidSchedulers.mainThread());
    }

    /**
     * single -> main
     * @param <T>
     * @return
     */
    public static <T> BaseScheduler<T> singleToMain() {
        return new BaseScheduler<>(Schedulers.single(), AndroidSchedulers.mainThread());
    }

    /**
     * thread -> main
     * @param <T>
     * @return
     */
    public static <T> BaseScheduler<T> threadToMain() {
        return new BaseScheduler<>(Schedulers.newThread(), AndroidSchedulers.mainThread());
    }
}
