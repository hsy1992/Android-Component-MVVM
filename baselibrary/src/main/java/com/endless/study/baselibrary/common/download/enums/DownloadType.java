package com.endless.study.baselibrary.common.download.enums;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import androidx.annotation.StringDef;

/**
 * 下载类型
 * @author haosiyuan
 * @date 2019/4/8 4:40 PM
 */
@StringDef({
        DownloadType.APK,
        DownloadType.HTML,
        DownloadType.JPG,
        DownloadType.PNG,
        DownloadType.TXT,
        DownloadType.XML,
        DownloadType.ZIP
})
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.FIELD, ElementType.PARAMETER})
public @interface DownloadType {

    String TXT = "TXT";

    String APK = "apk";

    String PNG = "png";

    String JPG = "jpg";

    String HTML = "html";

    String XML = "xml";

    String ZIP = "zip";
}
