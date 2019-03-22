package com.hsy.study.baselibrary.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

/**
 * 生产空{@link androidx.lifecycle.LiveData}
 * @author haosiyuan
 * @date 2019/3/22 10:23 AM
 */
public class LiveDataFactory {

    /**
     * 统一生产LiveData
     * @param <T>
     * @return
     */
    public static final <T> LiveData<T> productLiveData() {

        return new MutableLiveData<>();
    }
}
