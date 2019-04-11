package com.endless.study.baselibrary.database.entity;

import android.os.Environment;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.endless.study.baselibrary.common.download.enums.DownloadPriority;
import com.endless.study.baselibrary.common.download.enums.DownloadStatus;
import com.endless.study.baselibrary.common.download.enums.DownloadStopMode;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Comparator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * 下载表
 * @author haosiyuan
 * @date 2019/4/8 4:52 PM
 */
@Entity(tableName = "tb_download")
public class DownloadEntity implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    public Long id;
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
    /**
     * 是否更新
     */
    private boolean isUpdate = false;
    /**
     * 是否显示通知栏
     */
    private boolean isShowNotification = false;

    public DownloadEntity(String fileName, String filePath, String url, Long totalLength, Long currentLength,
                          String startTime, String finishTime, Integer status, Integer priority, Integer stopMode) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.url = url;
        this.status = status;
        this.priority = priority;
        this.stopMode = stopMode;
        this.totalLength = totalLength;
        this.currentLength = currentLength;
        this.startTime = startTime;
        this.finishTime = finishTime;
    }

    private DownloadEntity() {}

    @Override
    public String toString() {
        return "Download{" +
                "id=" + id +
                ", fileName='" + fileName + '\'' +
                ", filePath='" + filePath + '\'' +
                ", url='" + url + '\'' +
                ", totalLength=" + totalLength +
                ", currentLength=" + currentLength +
                ", startTime='" + startTime + '\'' +
                ", finishTime='" + finishTime + '\'' +
                ", status=" + status +
                ", priority=" + priority +
                ", stopMode=" + stopMode +
                '}';
    }

    @NonNull
    public Long getId() {
        return id;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getUrl() {
        return url;
    }

    public Long getTotalLength() {
        return totalLength;
    }

    public Long getCurrentLength() {
        return currentLength;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public Integer getStatus() {
        return status;
    }

    public Integer getPriority() {
        return priority;
    }

    public Integer getStopMode() {
        return stopMode;
    }

    public void setId(@NonNull Long id) {
        this.id = id;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setTotalLength(Long totalLength) {
        this.totalLength = totalLength;
    }

    public void setCurrentLength(Long currentLength) {
        this.currentLength = currentLength;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public void setStopMode(Integer stopMode) {
        this.stopMode = stopMode;
    }

    public boolean isUpdate() {
        return isUpdate;
    }

    public void setUpdate(boolean update) {
        isUpdate = update;
    }

    public boolean isShowNotification() {
        return isShowNotification;
    }

    public void setShowNotification(boolean showNotification) {
        isShowNotification = showNotification;
    }

    public static final class Builder {
        private String url;
        private String mFileName;
        private String mFilePath;
        private boolean isUpdate = false;
        private boolean isShowNotification = false;
        private int downloadStatus = DownloadStatus.waiting;
        private int downloadStopMode = DownloadStopMode.auto;
        private int downloadPriority = DownloadPriority.low;

        private Builder() {
        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder withUrl(String url) {
            this.url = url;
            return this;
        }

        public Builder withMFileName(String mFileName) {
            this.mFileName = mFileName;
            return this;
        }

        public Builder withMFilePath(String mFilePath) {
            this.mFilePath = mFilePath;
            return this;
        }

        public Builder withIsUpdate(boolean isUpdate) {
            this.isUpdate = isUpdate;
            return this;
        }

        public Builder withIsShowNotification(boolean isShowNotification) {
            this.isShowNotification = isShowNotification;
            return this;
        }

        public Builder withDownloadStatus(@DownloadStatus int downloadStatus) {
            this.downloadStatus = downloadStatus;
            return this;
        }

        public Builder withDownloadStopMode(@DownloadStopMode int downloadStopMode) {
            this.downloadStopMode = downloadStopMode;
            return this;
        }

        public Builder withDownloadPriority(@DownloadPriority int downloadPriority) {
            this.downloadPriority = downloadPriority;
            return this;
        }

        public DownloadEntity build() {
            DownloadEntity downloadConfig = new DownloadEntity();

            if (TextUtils.isEmpty(this.url)) {
                throw new IllegalArgumentException("Download url can not be null");
            }

            String[] preFix = this.url.split("/");

            downloadConfig.fileName = "/" + (TextUtils.isEmpty(this.mFileName) ?
                    preFix[preFix.length-1] : this.mFileName);

            downloadConfig.filePath = TextUtils.isEmpty(this.mFilePath) ?
                    Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + preFix[preFix.length - 1]
                    : this.mFilePath;

            downloadConfig.isShowNotification = this.isShowNotification;
            downloadConfig.isUpdate = this.isUpdate;
            downloadConfig.url = this.url;
            downloadConfig.status = this.downloadStatus;
            downloadConfig.stopMode = this.downloadStopMode;
            downloadConfig.priority = this.downloadPriority;
            return downloadConfig;
        }
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.fileName);
        dest.writeString(this.filePath);
        dest.writeString(this.url);
        dest.writeValue(this.totalLength);
        dest.writeValue(this.currentLength);
        dest.writeString(this.startTime);
        dest.writeString(this.finishTime);
        dest.writeValue(this.status);
        dest.writeValue(this.priority);
        dest.writeValue(this.stopMode);
        dest.writeByte(this.isUpdate ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isShowNotification ? (byte) 1 : (byte) 0);
    }

    protected DownloadEntity(Parcel in) {
    }

    public void readFromParcel(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.fileName = in.readString();
        this.filePath = in.readString();
        this.url = in.readString();
        this.totalLength = (Long) in.readValue(Long.class.getClassLoader());
        this.currentLength = (Long) in.readValue(Long.class.getClassLoader());
        this.startTime = in.readString();
        this.finishTime = in.readString();
        this.status = (Integer) in.readValue(Integer.class.getClassLoader());
        this.priority = (Integer) in.readValue(Integer.class.getClassLoader());
        this.stopMode = (Integer) in.readValue(Integer.class.getClassLoader());
        this.isUpdate = in.readByte() != 0;
        this.isShowNotification = in.readByte() != 0;
    }

    public static final Creator<DownloadEntity> CREATOR = new Creator<DownloadEntity>() {
        @Override
        public DownloadEntity createFromParcel(Parcel source) {
            return new DownloadEntity(source);
        }

        @Override
        public DownloadEntity[] newArray(int size) {
            return new DownloadEntity[size];
        }
    };

    @Override
    public boolean equals(@Nullable Object obj) {
        return id.equals(((DownloadEntity)obj).id);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

}
