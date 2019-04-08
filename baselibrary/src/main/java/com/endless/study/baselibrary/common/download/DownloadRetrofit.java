package com.endless.study.baselibrary.common.download;

import android.text.TextUtils;

import com.endless.study.baselibrary.common.download.interfaces.IDownloadListener;
import com.endless.study.baselibrary.utils.UtilString;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * 单例提供 {@link Retrofit}
 * @author haosiyuan
 * @date 2019/4/8 2:32 PM
 */
class DownloadRetrofit {

    private ConcurrentMap<String, Retrofit> retrofitConcurrentMap = new ConcurrentHashMap<>();

    private static class DownloadRetrofitInstance {
        private static DownloadRetrofit Instance = new DownloadRetrofit();
    }

    public static DownloadRetrofit getInstance() {
        return DownloadRetrofitInstance.Instance;
    }

    public Retrofit getRetrofit(String url, IDownloadListener downloadListener) {

        String host = UtilString.getHost(url);

        Retrofit retrofit = null;

        if (!TextUtils.isEmpty(host)) {

            if (retrofitConcurrentMap.get(host) != null) {
                retrofit = retrofitConcurrentMap.get(host);

            } else {
                DownloadInterceptor downloadInterceptor = new DownloadInterceptor(downloadListener);

                OkHttpClient client = new OkHttpClient.Builder()
                        .addInterceptor(downloadInterceptor)
                        .retryOnConnectionFailure(true)
                        .connectTimeout(60, TimeUnit.SECONDS)
                        .build();

                retrofit = new Retrofit.Builder()
                                .baseUrl(host)
                                .client(client)
                                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                                .build();
                retrofitConcurrentMap.put(host, retrofit);
            }

        }

        return retrofit;
    }

}
