package com.hsy.study.baselibrary.repository;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;

/**
 * 提供给UI 的数据状态
 * @author haosiyuan
 * @date 2019/3/20 1:41 PM
 */
@IntDef({
        DataStatus.SUCCESS,
        DataStatus.ERROR,
        DataStatus.LOADING
})
@Retention(RetentionPolicy.SOURCE)
public @interface DataStatus {
    int SUCCESS = 1;
    int ERROR = 2;
    int LOADING = 3;
}
