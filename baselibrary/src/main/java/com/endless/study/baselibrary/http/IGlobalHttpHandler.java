package com.endless.study.baselibrary.http;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 处理 Http 请求和响应结果的处理类
 * 在拦截器中使用
 * @author haosiyuan
 * @date 2019/1/20 8:56 PM
 */
public interface IGlobalHttpHandler {

    /**
     * 先一步拿到 Http请求的结果
     * @param result 服务器返回的结果
     * @param chain {@link okhttp3.Interceptor.Chain}
     * @param response {@link Response}
     * @return {@link Response}
     */
    @NonNull
    Response onHttpResponseResult(@Nullable String result, @Nullable Interceptor.Chain chain, @NonNull Response response);


    /**
     * 在发送请求前 拿到Request
     * @param chain {@link okhttp3.Interceptor.Chain}
     * @param request {@link Request}
     * @return {@link Request}
     */
    @NonNull
    Request onHttpRequestBefore(@Nullable Interceptor.Chain chain, @NonNull Request request);


    IGlobalHttpHandler EMPTY = new IGlobalHttpHandler() {
        @NonNull
        @Override
        public Response onHttpResponseResult(@Nullable String result, @Nullable Interceptor.Chain chain, @NonNull Response response) {
            return response;
        }

        @NonNull
        @Override
        public Request onHttpRequestBefore(@Nullable Interceptor.Chain chain, @NonNull Request request) {
            return request;
        }
    };
}
