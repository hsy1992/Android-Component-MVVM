// DownloadCallback.aidl
package com.endless.study.baselibrary.common.download;

// Declare any non-default types here with import statements

interface DownloadCallback {

    /**
     * 新增下载任务的监听
     *
     * @param downloadId
     *            下载id
     */
    void onDownloadInfoAdd(long downloadId);

    /**
     * 删除下载任务的监听
     *
     * @param downloadId
     *            下载id
     */
    void onDownloadInfoRemove();

    /**
     * 下载状态变化
     *
     * @param downloadId
     *            下载id
     * @param status
     *            下载状态
     */

    void onDownloadStatusChanged(long downloadId, int status);

    /**
     * 获取了下载文件总的长度
     *
     * @param downloadId
     *            下载id
     * @param totalLength
     *            下载文件总的长度
     */
    void onTotalLengthReceived(long downloadId, long totalLength);

    /**
     * 下载进度
     *
     * @param downloadId
     *            下载id
     * @param downloadPercent
     *            下载的百分比
     * @param speed
     *            下载速度
     */
    void onCurrentSizeChanged(long downloadId, double downloadPercent, long speed);

    /**
     * 下载成功
     *
     * @param downloadId
     *            下载id
     */
    void onDownloadSuccess(long downloadId);

    /**
     * 下载失败监听
     *
     * @param downloadId
     *            下载id
     * @param errorCode
     *            下载错误码
     * @param errorMsg
     *            下载错误信息
     */
    void onDownloadError(long downloadId, int errorCode, String errorMsg);

}
