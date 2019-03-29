package com.endless.study.baselibrary.repository;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

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
@Target({ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD})
public @interface DataStatus {
    int SUCCESS = 1;
    int ERROR = 2;
    int LOADING = 3;
}
