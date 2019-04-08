package com.endless.study.baselibrary.common.download.interfaces;

import java.util.Map;

/**
 * @author haosiyuan
 * @date 2019/4/8 9:01 PM
 */
public interface IDownloadService {

    /**
     * 设置请求参数
     */
    void setRequestData(byte[] requestData);

    /**
     * 暂停
     */
    void pause();

    /**
     * 是否暂停
     * @return
     */
    boolean isPause();

    /**
     * 获取请求头
     * @return
     */
    Map<String,String> getHttpHeaderMap();

    /**
     * 请求是否取消
     * @return
     */
    boolean isCancle();

    /**
     * 取消请求
     */
    void cancle();
}
