package com.endless.rxbus.event;

import com.endless.rxbus.handler.EventThread;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import androidx.annotation.Nullable;

/**
 * 事件基类
 * @author haosiyuan
 * @date 2019/3/26 7:04 PM
 */
public abstract class Event {

    /**
     * 方法的Object
     */
    Object target;

    /**
     * 提供的方法
     */
    Method method;

    /**
     * 线程
     */
    final int eventThread;

    /**
     * Object hashCode
     */
    int hashCode;

    /**
     * 是否有效
     */
    boolean valid = true;

    Event(Object target, Method method, @EventThread int eventThread) {

        if (target == null) {
            throw new IllegalArgumentException("Object target can not be null");
        }

        if (method == null) {
            throw new IllegalArgumentException("method can not be null");
        }

        this.target = target;
        this.method = method;
        this.eventThread = eventThread;

        //取消了权限检查
        method.setAccessible(true);
        //hashCode
        final int prime = 31;
        this.hashCode = (prime + method.hashCode()) * prime + target.hashCode();

    }

    /**
     * 是否是有效的
     * @return
     */
    public boolean isValid() {
        return valid;
    }

    /**
     * 使失效 之后不会生产事件
     */
    public void invalidate() {
        valid = false;
    }

    /**
     * 哈希值
     * @return
     */
    @Override
    public int hashCode() {
        return hashCode;
    }

    public Object getTarget() {
        return target;
    }

    /**
     * 抛出运行异常
     * @param msg 异常信息
     * @param e  调用目标异常
     */
    public void throwRuntimeException(String msg, InvocationTargetException e) {

        throwRuntimeException(msg, e.getCause());
    }

    /**
     * 抛出运行异常
     * @param msg
     * @param e
     */
    public void throwRuntimeException(String msg, Throwable e) {

        Throwable cause = e.getCause();

        if (cause != null) {
            throw new RuntimeException(msg + ": " + cause.getMessage(), cause);
        } else {
            throw new RuntimeException(msg + ": " + e.getMessage(), e);
        }
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == null) {
            return false;
        }

        if (this == obj) {
            return true;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        final Event other = (Event) obj;

        return method.equals(other.method) && target == other.target;
    }
}
