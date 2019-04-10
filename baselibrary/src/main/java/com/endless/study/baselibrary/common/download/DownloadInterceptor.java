package com.endless.study.baselibrary.common.download;


import com.endless.study.baselibrary.database.entity.DownloadEntity;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * {@link okhttp3.OkHttpClient} 拦截器
 * @author haosiyuan
 * @date 2019/4/8 3:24 PM
 */
public class DownloadInterceptor implements Interceptor {

    private DownloadCallback downloadCallback;
    private DownloadEntity downloadEntity;

    DownloadInterceptor(DownloadCallback downloadCallback, DownloadEntity downloadEntity) {
        this.downloadCallback = downloadCallback;
        this.downloadEntity = downloadEntity;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        return response.newBuilder()
                .body(new DownloadResponseBody(response.body(),
                        chain.request().header("RANGE"), downloadCallback, downloadEntity))
                .build();
    }
}
