package com.endless.rxbus;

/**
 * 对外 单例
 * @author haosiyuan
 * @date 2019/3/26 3:17 PM
 */
public class RxBus {

    private static volatile Bus mBus;

    public static Bus getInstance() {
        if (mBus == null) {
            synchronized (Bus.class) {
                if (mBus == null) {
                    mBus = new Bus();
                }
            }
        }

        return mBus;
    }

}
