package com.endless.study.baselibrary.common.executor;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * 线程池
 * @author haosiyuan
 * @date 2019/3/19 5:11 PM
 */
@Singleton
public class AppExecutors {

    /**
     * io线程池
     */
    private ExecutorService diskIO;

    /**
     * 网络请求线程
     */
    private ExecutorService netWorkIO;

    /**
     * 主线程
     */
    private Executor mainExecutor;

    @Inject
    public AppExecutors() {
        mainExecutor = new MainThreadExecutor();
    }

    public ExecutorService getDiskIO() {
        return getDiskIOExecutor();
    }

    public ExecutorService getNetWorkIO() {
        return getNetWorkExecutor();
    }

    public Executor getMainExecutor() {
        return mainExecutor;
    }

    /**
     * 自定义线程池
     * @param threadName
     * @param corePoolSize
     * @param maxPoolSize
     * @param keepAliveTime
     * @return
     */
    public ExecutorService getCustomExecutor(String threadName, int corePoolSize, int maxPoolSize, long keepAliveTime) {
        return new ThreadPoolExecutor(corePoolSize, maxPoolSize, keepAliveTime, TimeUnit.SECONDS,
                    new LinkedBlockingDeque<>(), new AppThreadFactory(threadName, false));
    }

    /**
     * 获取IO线程池
     * @return
     */
    private ExecutorService getDiskIOExecutor() {

        if (diskIO == null) {
            diskIO = new ThreadPoolExecutor(2, 10, 0L, TimeUnit.SECONDS,
                    new LinkedBlockingDeque<>(), new AppThreadFactory("DISK IO", false));
        }
        return diskIO;
    }

    /**
     * 获取NetWork线程池
     * @return
     */
    private ExecutorService getNetWorkExecutor() {

        if (netWorkIO == null) {
            netWorkIO = new ThreadPoolExecutor(5, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS,
                    new LinkedBlockingDeque<>(), new AppThreadFactory("NETWORK IO", false));
        }
        return netWorkIO;
    }


    /**
     * 主线程
     */
    private class MainThreadExecutor implements Executor {
        private Handler mainThreadHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(Runnable command) {
            mainThreadHandler.post(command);
        }
    }
}
