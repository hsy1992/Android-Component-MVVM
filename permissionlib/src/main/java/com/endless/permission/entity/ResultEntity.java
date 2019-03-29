package com.endless.permission.entity;

import java.util.List;

/**
 * 未授权成功返回
 * @author haosiyuan
 * @date 2019/3/29 3:38 PM
 */
public class ResultEntity {

    private String message;

    public ResultEntity(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
