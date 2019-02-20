package com.hsy.study.baselibrary.lifecycle;


import androidx.annotation.CheckResult;
import androidx.annotation.NonNull;
import io.reactivex.Observable;

/**
 * 声明周期接口
 * @author haosiyuan
 * @date 2019/2/10 7:17 PM
 */
public interface ILifecycleProvide<E> {

    /**
     * 绑定的生命周期
     * @return
     */
    @NonNull
    @CheckResult
    Observable<E> lifecycle();

    /**
     * 绑定事件到生命周期
     * @param <T>
     * @return
     */
    @NonNull
    @CheckResult
    <T> LifecycleTransformer<T> bindLifecycle();

    /**
     * 绑定直到 event 解除绑定
     * @param
     * @return
     */
    @NonNull
    @CheckResult
    <T> LifecycleTransformer<T> bindUntilEvent(@NonNull E event);

}
