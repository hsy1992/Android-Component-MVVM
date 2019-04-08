package com.endless.study.baselibrary.common.download.enums;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import androidx.annotation.IntDef;

/**
 * 下载状态
 * @author haosiyuan
 * @date 2019/4/8 5:17 PM
 */
@IntDef({
        DownloadStatus.waiting,
        DownloadStatus.downloading,
        DownloadStatus.pause,
        DownloadStatus.finish,
        DownloadStatus.failed,
})
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.FIELD, ElementType.PARAMETER})
public @interface DownloadStatus {

    int waiting = 0x11;

    int downloading = 0x12;

    int pause = 0x13;

    int finish = 0x14;

    int failed = 0x15;

}
