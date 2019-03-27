package com.endless.rxbus.handler;

import android.os.Looper;

import com.endless.rxbus.Bus;

/**
 * 线程策略 拒绝某些线程进入
 * @author haosiyuan
 * @date 2019/3/26 4:51 PM
 */
public interface ThreadStrategy {

    void handle(Bus bus);

    /**
     * 任何线程
     */
    ThreadStrategy ANY = new ThreadStrategy() {
        @Override
        public void handle(Bus bus) {

        }
    };


    /**
     * 只有主线程可以访问
     */
    ThreadStrategy MAIN = new ThreadStrategy() {
        @Override
        public void handle(Bus bus) {
            if (Looper.myLooper() != Looper.getMainLooper()) {
                throw new RuntimeException(bus + " must be accessed on main thread");
            }

        }
    };


}
