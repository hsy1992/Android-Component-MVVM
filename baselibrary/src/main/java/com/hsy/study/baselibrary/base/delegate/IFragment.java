package com.hsy.study.baselibrary.base.delegate;


import com.hsy.study.baselibrary.cache.ICache;

import androidx.annotation.Nullable;

/**
 * 每个{@link androidx.fragment.app.Fragment} 实现此接口 提供一些Fragment需要实现的方法 不是用基类继承的方法降低耦合
 * @author haosiyuan
 * @date 2019/2/14 2:22 PM
 */
public interface IFragment {

    /**
     * 视图 resId
     * @return
     */
    int getLayoutId();

    /**
     * 初始化视图
     */
    void initView();

    /**
     * 初始化数据
     */
    void initData();

    /**
     * 调用 setData 向{@link androidx.fragment.app.Fragment} 传数据使Fragment发生相应操作
     * @param data
     */
    void setData(@Nullable Object data);

    /**
     * 获取自定义 Cache<String, Object>
     * @return
     */
    ICache<String, Object> getCacheData();
}
