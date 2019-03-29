package com.endless.networkclientstate;

import android.app.Application;
import android.content.IntentFilter;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author haosiyuan
 * @date 2019/2/28 3:19 PM
 */
public class NetWorkManager {

    /**
     * 实例
     */
    private static volatile NetWorkManager instance;
    private LinkedList<NetworkState> states = new LimitedQueue<NetworkState>().create(10);
    /**
     * 设置无网 状态波动阀值防止重复调用网络重载
     */
    private static final long TIME_OUT = 10000L;

    /**
     * 是否可以网络重载
     */
    private AtomicBoolean canNetReload = new AtomicBoolean(false);

    /**
     * 当前网络状态
     */
    private NetworkState currentSate;

    private LinkedList<NetworkChangeListener> networkChangeListeners = new LinkedList<>();
    private NetworkReceiver networkReceiver;
    /**
     * 线程池 阻塞式线程只可以有执行一个Runnable
     */
    private ExecutorService executor = new ThreadPoolExecutor(0,
            1, 10L, TimeUnit.SECONDS,
            new LinkedBlockingDeque<Runnable>(1));



    public static NetWorkManager getInstance() {
        if (instance == null) {
            synchronized (NetWorkManager.class) {
                instance = new NetWorkManager();
            }
        }
        return instance;
    }

    void addNetworkChangeListener(NetworkChangeListener listener) {
        if (listener != null) {
            assert networkReceiver != null;
            networkChangeListeners.add(listener);
        }
    }

    void removeNetworkChangeListener(NetworkChangeListener listener) {
        if (listener != null) {
            assert networkReceiver != null;
            networkChangeListeners.remove(listener);
        }
    }

    /**
     * 开启广播
     * @param application
     */
    public void startReceiver(Application application) {
        if (networkReceiver == null) {
            networkReceiver = new NetworkReceiver(new NetworkStateChange());
            IntentFilter filter = new IntentFilter();
            filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
            application.registerReceiver(networkReceiver, filter);
        }
    }

    class NetworkStateChange implements NetworkChangeListener {

        @Override
        public void onNoNetwork() {}

        @Override
        public void onNetReload(NetworkState state) {}

        @Override
        public void onNetChange(NetworkState state) {
            currentSate = state;

            if (states.size() == 0){
                for (NetworkChangeListener listener : networkChangeListeners) {
                    if (currentSate == NetworkState.NO_NET) {
                        listener.onNoNetwork();
                    } else {
                        listener.onNetChange(currentSate);
                    }
                }
            } else if (states.peekLast() != currentSate) {
                // TODO: 2019/3/29 从蜂窝网络->wifi 会触发网络重载 时间很短暂 而直接断开wifi 回到蜂窝网络不会触发
                for (NetworkChangeListener listener : networkChangeListeners) {
                    if (currentSate != NetworkState.NO_NET
                            && states.peekLast() == NetworkState.NO_NET && canNetReload.get()) {
                        canNetReload.set(false);
                        listener.onNetReload(currentSate);
                    } else if (currentSate == NetworkState.NO_NET) {
                        listener.onNoNetwork();
                    } else {
                        listener.onNetChange(currentSate);
                    }
                }
            }

            states.add(state);


            if (currentSate == NetworkState.NO_NET && !canNetReload.get()) {
                //当前无网状态大于阀值才回触发网络重载
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(TIME_OUT);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        if (currentSate == NetworkState.NO_NET) {
                            //倒计时结束 还处于无网状态
                            canNetReload.set(true);
                        }
                    }
                });
            }
        }
    }

}
