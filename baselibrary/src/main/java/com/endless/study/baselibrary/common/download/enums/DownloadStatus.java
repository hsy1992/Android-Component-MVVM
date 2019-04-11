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

    int waiting = 1;

    int downloading = 2;

    int pause = 3;

    int finish = 4;

    int failed = 5;

}
