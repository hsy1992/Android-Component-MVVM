package com.endless.study.baselibrary.common.download.interfaces;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * 下载
 * @author haosiyuan
 * @date 2019/4/8 4:10 PM
 */
public interface DownloadApi {

    /**
     * 下载
     * @param url
     * @return
     */
    @Streaming
    @GET
    Observable<ResponseBody> download(@Url String url);
}
