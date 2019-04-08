package com.endless.study.baselibrary.common.download.interfaces;

/**
 * 下载回调
 * @author haosiyuan
 * @date 2019/4/5 1:55 PM
 */
public interface IDownloadListener {

    void update(long bytesRead, long contentLength, boolean done);

}
