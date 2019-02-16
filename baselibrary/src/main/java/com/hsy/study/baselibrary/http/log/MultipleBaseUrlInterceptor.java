package com.hsy.study.baselibrary.http.log;

import com.hsy.study.baselibrary.http.BaseUrl;
import com.hsy.study.baselibrary.utils.Preconditions;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 多个{@code BaseUrl} 的拦截器
 * @author haosiyuan
 * @date 2019/2/16 5:42 PM
 */
public class MultipleBaseUrlInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();

        Headers headers = request.headers();

        //找出header 中的baseUrl
        if (headers.names().contains(BaseUrl.HTTP_BASE_URL_HEAD)) {
            String baseUrl = headers.get(BaseUrl.HTTP_BASE_URL_HEAD);
            Preconditions.checkNotNull(baseUrl,"Header BaseUrl can not be null");
            request.newBuilder().url(baseUrl);
        }

        return chain.proceed(request);
    }
}
