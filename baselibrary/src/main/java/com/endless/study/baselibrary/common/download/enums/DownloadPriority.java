package com.endless.study.baselibrary.common.download.enums;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import androidx.annotation.IntDef;

/**
 * 下载优先级
 * @author haosiyuan
 * @date 2019/4/9 1:29 PM
 */
@IntDef({
        DownloadPriority.low,
        DownloadPriority.middle,
        DownloadPriority.high
})
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.FIELD, ElementType.PARAMETER})
public @interface DownloadPriority {

    int low = 1;

    int middle = 2;

    int high = 3;
}
