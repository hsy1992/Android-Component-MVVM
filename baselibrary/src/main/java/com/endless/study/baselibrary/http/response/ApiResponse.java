package com.endless.study.baselibrary.http.response;

import android.text.TextUtils;

import retrofit2.Response;

/**
 * api 返回
 * @author haosiyuan
 * @date 2019/3/20 3:48 PM
 */
public class ApiResponse<T> {

    private static final String DEFAULT_ERROR_MESSAGE = "unknown error";

    /**
     * 错误返回
     * @param error
     * @param <T>
     * @return
     */
    public static <T> ApiErrorResponse<T> create(Throwable error) {
        return new ApiErrorResponse<>(TextUtils.isEmpty(error.getMessage()) ? DEFAULT_ERROR_MESSAGE : error.getMessage());
    }

    public static <T> ApiResponse<T> create(Response<T> response) {

        if (response.isSuccessful()) {
            T responseBody = response.body();

            if (responseBody == null || response.code() == 204) {
                return new ApiEmptyResponse<>();
            } else {
                return new ApiSuccessResponse(responseBody, response.headers().get("link"));

            }

        } else {
            String errorMessage = TextUtils.isEmpty(response.errorBody().toString())
                    ? response.message() : response.errorBody().toString();
            return new ApiErrorResponse<>(TextUtils.isEmpty(errorMessage)
                    ? DEFAULT_ERROR_MESSAGE : errorMessage);
        }
    }


}
