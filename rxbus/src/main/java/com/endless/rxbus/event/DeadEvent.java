package com.endless.rxbus.event;

/**
 * 一个Event的包装类 不被订阅
 * @author haosiyuan
 * @date 2019/3/26 5:08 PM
 */
public class DeadEvent {

    public Object source;
    public Object event;

    public DeadEvent(Object source, Object event) {
        this.source = source;
        this.event = event;
    }
}
