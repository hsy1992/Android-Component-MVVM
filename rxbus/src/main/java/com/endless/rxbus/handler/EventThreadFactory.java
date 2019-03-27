package com.endless.rxbus.handler;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 线程工厂 根据event 返回 {@link Scheduler}
 * @author haosiyuan
 * @date 2019/3/26 4:10 PM
 */
public class EventThreadFactory {

    private EventThreadFactory() {
        throw new RuntimeException("can not instance");
    }

    /**
     * 默认返回主线程
     * @param eventThread
     * @return
     */
    public static Scheduler getScheduler(@EventThread int eventThread) {

        Scheduler scheduler;

        switch (eventThread) {
            case EventThread.MAIN_THREAD:
                scheduler = AndroidSchedulers.mainThread();
                break;
            case EventThread.NEW_THREAD:
                scheduler = Schedulers.newThread();
                break;
            case EventThread.IO:
                scheduler = Schedulers.io();
                break;
            case EventThread.COMPUTATION:
                scheduler = Schedulers.computation();
                break;
            case EventThread.TRAMPOLINE:
                scheduler = Schedulers.trampoline();
                break;
            case EventThread.SINGLE:
                scheduler = Schedulers.single();
                break;
            default:
                scheduler = AndroidSchedulers.mainThread();
                break;
        }

        return scheduler;
    }
}
