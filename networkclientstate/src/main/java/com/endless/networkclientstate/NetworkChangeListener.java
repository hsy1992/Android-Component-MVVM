package com.endless.networkclientstate;

/**
 * 网络状态监听
 * @author haosiyuan
 * @date 2019/2/28 2:53 PM
 */
public interface NetworkChangeListener {

    /**
     * 无网
     */
    void onNoNetwork();

    /**
     * 网络重载
     * @param state 重载后状态
     */
    void onNetReload(NetworkState state);

//    /**
//     * 网络良好
//     */
//    void onNetGood();
//
//    /**
//     * 网络不佳
//     */
//    void onNetBad();

    /**
     * 网络变化回调
     * @param state
     */
    void onNetChange(NetworkState state);
}
