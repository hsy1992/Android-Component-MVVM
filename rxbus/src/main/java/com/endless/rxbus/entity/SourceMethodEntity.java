package com.endless.rxbus.entity;

import com.endless.rxbus.handler.EventThread;

import java.lang.reflect.Method;

import androidx.annotation.Nullable;


/**
 * 源 实体对象
 * @author haosiyuan
 * @date 2019/3/27 1:37 PM
 */
public class SourceMethodEntity {

    private @EventThread int thread;

    private Method method;

    public SourceMethodEntity(int thread, Method method) {
        this.thread = thread;
        this.method = method;
    }

    public int getThread() {
        return thread;
    }

    public Method getMethod() {
        return method;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return super.equals(obj);
    }
}
