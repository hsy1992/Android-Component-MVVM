package com.hsy.study.baselibrary.http.response;

/**
 * api 请求错误
 * @author haosiyuan
 * @date 2019/3/20 4:19 PM
 */
public class ApiErrorResponse<T> extends ApiResponse<T> {

    private String errorMessage;

    public ApiErrorResponse(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
