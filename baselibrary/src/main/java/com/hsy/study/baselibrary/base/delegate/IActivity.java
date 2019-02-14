package com.hsy.study.baselibrary.base.delegate;


/**
 * 每个{@link android.app.Activity} 实现此接口 提供一些activity需要实现的方法 不是用基类继承的方法降低耦合
 * @author haosiyuan
 * @date 2019/2/13 4:17 PM
 */
public interface IActivity {

    /**
     * 视图 resId 返回0 不调用{@code setContentView(int)}
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
     * 绑定生命周期，防止rxJava内存泄漏
     */
    void bindLifecycle();
}
