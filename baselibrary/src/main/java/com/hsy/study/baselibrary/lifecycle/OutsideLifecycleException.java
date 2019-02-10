package com.hsy.study.baselibrary.lifecycle;

import androidx.annotation.Nullable;

/**
 * 一个异常可以抛出在声明周期外
 * @author haosiyuan
 * @date 2019/2/7 12:28 PM
 */
public class OutsideLifecycleException extends IllegalStateException{

    public OutsideLifecycleException(@Nullable String detailMessage) {
        super(detailMessage);
    }
}
