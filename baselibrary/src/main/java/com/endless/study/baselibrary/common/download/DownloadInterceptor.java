package com.endless.study.baselibrary.common.download;



import com.endless.study.baselibrary.common.download.interfaces.IDownloadListener;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * {@link okhttp3.OkHttpClient} 拦截器
 * @author haosiyuan
 * @date 2019/4/8 3:24 PM
 */
public class DownloadInterceptor implements Interceptor {

    private IDownloadListener downloadListener;

    DownloadInterceptor(IDownloadListener downloadListener) {
        this.downloadListener = downloadListener;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        return response.newBuilder()
                .body(new DownloadResponseBody(response.body(), downloadListener))
                .build();
    }
}
