package com.endless.study.baselibrary.common.download;


import com.endless.study.baselibrary.common.download.enums.DownloadType;

/**
 * 下载配置
 * @author haosiyuan
 * @date 2019/4/8 10:37 AM
 */
public class DownloadConfig {

    private String url;

    private String fileName;

    private String filePath;

    private String fileType;

    private boolean isUpdate;

    private boolean isShowNotification;

    private boolean isContinue;

    private String downloadType;

    private int downloadStatus;

    private int downloadStopMode;

    private DownloadConfig() {
    }

    public static final class Builder {
        private String url;
        private String mFileName;
        private String mFilePath;
        private String fileType;
        private boolean isUpdate;
        private boolean isShowNotification;
        private boolean isContinue = false;
        private String downloadType;
        private int downloadStatus;
        private int downloadStopMode;

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

        public Builder withFileType(@DownloadType String fileType) {
            this.fileType = fileType;
            return this;
        }

        public Builder withContinue(boolean isContinue) {
            this.isContinue = isContinue;
            return this;
        }

        public Builder withDownloadType(String downloadType) {
            this.downloadType = downloadType;
            return this;
        }

        public Builder withDownloadStatus(int downloadStatus) {
            this.downloadStatus = downloadStatus;
            return this;
        }

        public Builder withDownloadStopMode(int downloadStopMode) {
            this.downloadStopMode = downloadStopMode;
            return this;
        }

        public DownloadConfig build() {
            DownloadConfig downloadConfig = new DownloadConfig();
            downloadConfig.isShowNotification = this.isShowNotification;
            downloadConfig.isUpdate = this.isUpdate;
            downloadConfig.url = this.url;
            downloadConfig.fileName = this.mFileName;
            downloadConfig.filePath = this.mFilePath;
            downloadConfig.fileType = this.fileType;
            downloadConfig.isContinue = this.isContinue;
            downloadConfig.downloadType = this.downloadType;
            downloadConfig.downloadStatus = this.downloadStatus;
            downloadConfig.downloadStopMode = this.downloadStopMode;
            return downloadConfig;
        }
    }

    public String getUrl() {
        return url;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getFileType() {
        return fileType;
    }

    public boolean isUpdate() {
        return isUpdate;
    }

    public boolean isShowNotification() {
        return isShowNotification;
    }

    public boolean isContinue() {
        return isContinue;
    }

    public String getDownloadType() {
        return downloadType;
    }

    public int getDownloadStatus() {
        return downloadStatus;
    }

    public int getDownloadStopMode() {
        return downloadStopMode;
    }
}
