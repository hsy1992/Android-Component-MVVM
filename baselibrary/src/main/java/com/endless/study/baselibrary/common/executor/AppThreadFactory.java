package com.endless.study.baselibrary.common.executor;

import java.util.concurrent.ThreadFactory;

/**
 * 线程工厂
 * @author haosiyuan
 * @date 2019/3/20 9:31 AM
 */
public class AppThreadFactory implements ThreadFactory {

    private String threadName;
    private boolean daemon;

    public AppThreadFactory(String threadName, boolean daemon) {
        this.threadName = threadName;
        this.daemon = daemon;
    }

    @Override
    public Thread newThread(Runnable runnable) {
        Thread thread = new Thread(runnable);
        thread.setName(threadName + thread.getId());
        thread.setDaemon(daemon);

        return thread;
    }
}
