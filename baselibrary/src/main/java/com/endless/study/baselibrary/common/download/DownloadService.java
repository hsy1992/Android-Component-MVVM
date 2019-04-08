package com.endless.study.baselibrary.common.download;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.text.TextUtils;

import com.endless.study.baselibrary.R;
import com.endless.study.baselibrary.common.download.interfaces.DownloadApi;
import com.endless.study.baselibrary.utils.UtilFile;

import androidx.annotation.Nullable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 下载服务
 * @author haosiyuan
 * @date 2019/4/5 1:50 PM
 */
class DownloadService extends Service {

    private DownloadBinder downloadBinder = new DownloadBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return downloadBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * 开始下载
     * @param config 下载配置
     */
    private void startDownload(DownloadConfig config) {

        if (TextUtils.isEmpty(config.getUrl())) {
            throw new IllegalArgumentException("DownloadConfig can not find url");
        }

        if (TextUtils.isEmpty(config.getFileType())) {
            throw new IllegalArgumentException("DownloadConfig can not find fileType");
        }


        DownloadRetrofit.getInstance()
                .getRetrofit(config.getUrl(), this::update)
                .create(DownloadApi.class)
                .download(config.getUrl())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .map(responseBody -> responseBody.byteStream())
                .observeOn(Schedulers.computation())
                .doOnNext(inputStream -> {
                    String filePath;
                    String fileName;

                    filePath = TextUtils.isEmpty(config.getFilePath()) ?
                            Environment.getExternalStorageState() + "/" + getResources().getString(R.string.app_name) + "/down" : config.getFilePath();

                    fileName = TextUtils.isEmpty(config.getFileName()) ?
                            getResources().getString(R.string.app_name) + "." + config.getFileType()
                            : config.getFileName() + "." + config.getFileType();

                    UtilFile.writeInput(filePath, fileName, inputStream, config.isContinue());

                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DownloadObserve());
    }

    /**
     * 更新UI,回调
     * @param bytesRead
     * @param contentLength
     * @param done
     */
    public void update(long bytesRead, long contentLength, boolean done) {

    }

    /**
     * 服务连接
     */
    class DownloadBinder extends Binder {

        public void start(DownloadConfig config) {
            startDownload(config);
        }
    }
}
