package com.endless.networkclientstate;

import java.util.LinkedList;

/**
 * 一个固定大小的队列
 * @author haosiyuan
 * @date 2019/3/29 10:35 AM
 */
public class LimitedQueue<E> extends LinkedList<E> {

    private int limit;

    public LimitedQueue<E> create(int limit) {
        this.limit = limit;
        return this;
    }

    @Override
    public boolean add(E o) {
        super.add(o);
        while (size() > limit) { super.remove(); }
        return true;
    }

}
