package com.endless.study.baselibrary.common.download.enums;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import androidx.annotation.IntDef;
import androidx.annotation.StringDef;

/**
 * 下载 停止类型
 * @author haosiyuan
 * @date 2019/4/8 4:40 PM
 */
@IntDef({
        DownloadStopMode.auto,
        DownloadStopMode.hand,
})
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.FIELD, ElementType.PARAMETER})
public @interface DownloadStopMode {

    /**
     * 根据优先级自动停止
     */
    int auto = 1;

    /**
     * 手动停止
     */
    int hand = 2;
}
