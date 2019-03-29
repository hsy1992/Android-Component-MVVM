package com.endless.study.baselibrary.repository;

/**
 * 当前数据状态
 * @author haosiyuan
 * @date 2019/3/20 1:39 PM
 */
public class DataResource<T> {

    @DataStatus
    private int status;

    private T data;

    private String message;

    private DataResource(int status, T data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }


    public static <T>DataResource<T> success(T data) {
        return new DataResource<>(DataStatus.SUCCESS, data, null);
    }

    public static <T>DataResource<T> error(String message, T data) {
        return new DataResource<>(DataStatus.ERROR, data, message);
    }

    public static <T>DataResource<T> loading(T data) {
        return new DataResource<>(DataStatus.LOADING, data, null);
    }

    public int getStatus() {
        return status;
    }

    public T getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }
}
