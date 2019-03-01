package com.hsy.study.networkclientstate;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

/**
 * @author haosiyuan
 * @date 2019/2/28 3:19 PM
 */
public class NetWorkManager {

    /**
     * 实例
     */
    private static volatile NetWorkManager instance;
    private LinkedList<NetworkState> states = new LinkedList<>();
    /**
     * 设置无网 状态波动阀值防止重复调用网络重载
     */
    private static final long TIME_OUT = 5000L;
    /**
     * 当前网络状态
     */
    private NetworkState currentSate;

    private LinkedList<NetworkChangeListener> networkChangeListeners = new LinkedList<>();
    private NetworkReceiver networkReceiver;

    public static NetWorkManager getInstance() {
        if (instance == null) {
            synchronized (NetWorkManager.class) {
                instance = new NetWorkManager();
            }
        }
        return instance;
    }

    public void addNetworkChangeListener(NetworkChangeListener listener) {
        if (listener != null) {
            assert networkReceiver != null;
            networkChangeListeners.add(listener);
        }
    }

    public void removeNetworkChangeListener(NetworkChangeListener listener) {
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
        public void onNetReload() {}

        @Override
        public void onNetGood() {}

        @Override
        public void onNetBad() {}

        @Override
        public void onNetChange(NetworkState state) {
            currentSate = state;

            if (states.size() == 0){
                for (NetworkChangeListener listener : networkChangeListeners) {
                    if (currentSate == NetworkState.NO_NET) {
                        listener.onNoNetwork();
                    }
                    listener.onNetChange(currentSate);
                }
            } else if (states.get(states.size() - 1) != currentSate) {

                for (NetworkChangeListener listener : networkChangeListeners) {
                    if (currentSate != NetworkState.NO_NET
                            && states.get(states.size() - 1) == NetworkState.NO_NET) {
                        listener.onNetReload();
                    }
                    listener.onNetChange(currentSate);
                }
            }

            states.add(state);
        }
    }

}
