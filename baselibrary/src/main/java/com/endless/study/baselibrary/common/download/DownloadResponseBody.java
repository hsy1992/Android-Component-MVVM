package com.endless.study.baselibrary.common.download;


import com.endless.study.baselibrary.common.download.interfaces.IDownloadListener;

import androidx.annotation.Nullable;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.BufferedSource;

/**
 * {@link okhttp3.OkHttpClient}返回体
 * @author haosiyuan
 * @date 2019/4/5 1:29 PM
 */
class DownloadResponseBody extends ResponseBody {

    private ResponseBody responseBody;
    private IDownloadListener downloadListener;

    DownloadResponseBody(ResponseBody responseBody, IDownloadListener downloadListener) {
        this.responseBody = responseBody;
        this.downloadListener = downloadListener;
    }

    @Nullable
    @Override
    public MediaType contentType() {
        return responseBody.contentType();
    }

    @Override
    public long contentLength() {
        return responseBody.contentLength();
    }

    @Override
    public BufferedSource source() {
        return null;
    }
}
