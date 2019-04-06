package com.endless.study.myproject.api;

import com.endless.study.myproject.download.DownloadResponseBody;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * 下载
 * @author haosiyuan
 * @date 2019/4/5 1:28 PM
 */
public interface DownloadService {

    @Streaming
    @GET
    Observable<DownloadResponseBody> download(@Url String url);
}
