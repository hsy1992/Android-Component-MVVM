package com.hsy.study.baselibrary.common.executor;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
     * 创建个单线程的线程池，相当于单线 程串行执行所有任务 ， 保证接任务的提交顺序依次执行。
     */
    private Executor diskIO;

    /**
     * 网络请求线程
     * 输入的参数即是固定线程数，既是核心线程
     * 数也是最大线程数
     * new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60, TimeUnit.SECONDS,
     *                 new SynchronousQueue<>(), Util.threadFactory("Executor", false))
     */
    private Executor netWorkIO;

    /**
     * 主线程
     */
    private Executor mainExecutor;

    @Inject
    public AppExecutors() {
        diskIO = Executors.newSingleThreadExecutor();
        netWorkIO = Executors.newFixedThreadPool(5);
        mainExecutor = new MainThreadExecutor();
    }

    public Executor getDiskIO() {
        return diskIO;
    }

    public Executor getNetWorkIO() {
        return netWorkIO;
    }

    public Executor getMainExecutor() {
        return mainExecutor;
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
