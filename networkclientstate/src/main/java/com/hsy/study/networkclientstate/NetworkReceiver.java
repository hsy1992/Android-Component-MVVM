package com.hsy.study.networkclientstate;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

/**
 * 网络监听
 * @author haosiyuan
 * @date 2019/2/28 2:51 PM
 */
public class NetworkReceiver extends BroadcastReceiver {

    private NetworkChangeListener networkChangeListeners;

    NetworkReceiver(NetworkChangeListener networkChangeListeners) {
        this.networkChangeListeners = networkChangeListeners;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                //获取正在使用的网络
                NetworkCapabilities capabilities =
                        connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
                notifyNetWorkChangeAbove21(capabilities);
            } else {
                notifyNetWorkChangeBelow21(networkInfo.getType());
            }

        } else {
            networkChangeListeners.onNetChange(NetworkState.NO_NET);
        }
    }

    /**
     * sdk 21 以下网络状态
     * @param netType
     */
    private void notifyNetWorkChangeBelow21(int netType) {
        //sdk 21以下
        switch (netType) {
            case ConnectivityManager.TYPE_MOBILE:
                networkChangeListeners.onNetChange(NetworkState.MOBILE);
                break;
            case ConnectivityManager.TYPE_WIFI:
                networkChangeListeners.onNetChange(NetworkState.WIFI);
                break;
            case ConnectivityManager.TYPE_ETHERNET:
                networkChangeListeners.onNetChange(NetworkState.ETHERNET);
                break;
            case ConnectivityManager.TYPE_VPN:
                networkChangeListeners.onNetChange(NetworkState.VPN);
                break;
            case ConnectivityManager.TYPE_BLUETOOTH:
                networkChangeListeners.onNetChange(NetworkState.BLUETOOTH);
                break;
            default:
                break;
        }
    }

    /**
     * sdk 21 以上网络状态
     * @param capabilities
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void notifyNetWorkChangeAbove21(NetworkCapabilities capabilities) {
        if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
            //蜂窝网络
            networkChangeListeners.onNetChange(NetworkState.MOBILE);
            return;
        }
        if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
            //wifi
            networkChangeListeners.onNetChange(NetworkState.WIFI);
            return;
        }
        if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH)) {
            //蓝牙
            networkChangeListeners.onNetChange(NetworkState.BLUETOOTH);
            return;
        }
        if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
            //以太网
            networkChangeListeners.onNetChange(NetworkState.ETHERNET);
            return;

        }
        if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN)) {
            //VPN
            networkChangeListeners.onNetChange(NetworkState.VPN);
            return;
        }
    }
}
