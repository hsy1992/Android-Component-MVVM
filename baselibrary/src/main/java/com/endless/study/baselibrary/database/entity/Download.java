package com.endless.study.baselibrary.database.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * 下载表
 * @author haosiyuan
 * @date 2019/4/8 4:52 PM
 */
@Entity(tableName = "tb_download")
public class Download {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    public Integer id;
    /**
     * 文件名
     */
    public String fileName;
    /**
     * 文件路径
     */
    public String filePath;
    /**
     * 下载url
     */
    public String url;
    /**
     * 总长度
     */
    public Long totalLength;
    /**
     * 已下载的
     */
    public Long currentLength;
    /**
     * 下载开始时间
     */
    public String startTime;
    /**
     * 下载结束时间
     */
    public String finishTime;
    /**
     * 下载状态
     */
    public Integer status;
    /**
     * 下载优先级
     */
    public Integer priority;
    /**
     * 下载停止模式
     */
    public Integer stopMode;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getTotalLength() {
        return totalLength;
    }

    public void setTotalLength(Long totalLength) {
        this.totalLength = totalLength;
    }

    public Long getCurrentLength() {
        return currentLength;
    }

    public void setCurrentLength(Long currentLength) {
        this.currentLength = currentLength;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Integer getStopMode() {
        return stopMode;
    }

    public void setStopMode(Integer stopMode) {
        this.stopMode = stopMode;
    }
}
