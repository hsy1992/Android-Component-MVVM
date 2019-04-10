package com.endless.study.baselibrary.common.download.constant;

/**
 * 下载错误
 * @author haosiyuan
 * @date 2019/4/9 3:26 PM
 */
public interface DownloadError {

    int Downloaded = 0x12;

    String isDownloaded = "已下载";

    int Downloading = 0x13;

    String isDownloading = "已经开始下载，请不要重复添加";

    int DownloadFinish = 0x11;

    String isDownloadFinish = "已经下载完成";

    int DownloadFailed = 0x14;

    String isDownloadFailed = "下载失败";

    int DownloadCreateFileFailed = 0x15;

    String isDownloadCreateFileFailed = "创建文件失败";
}
