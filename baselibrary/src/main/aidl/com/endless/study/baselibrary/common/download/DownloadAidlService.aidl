// DownloadAidlService.aidl
package com.endless.study.baselibrary.common.download;

import com.endless.study.baselibrary.database.entity.DownloadEntity;
import com.endless.study.baselibrary.common.download.DownloadCallback;
// Declare any non-default types here with import statements

interface DownloadAidlService {

    void startDownloadTask(inout DownloadEntity config);

    void stopDownloadTask(inout DownloadEntity config);

    //注册回调接口
    void registerCallBack(DownloadCallback callback);

    void unregisterCallBack(DownloadCallback callback);
}
