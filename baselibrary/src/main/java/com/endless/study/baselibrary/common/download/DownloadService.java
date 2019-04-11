package com.endless.study.baselibrary.common.download;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Log;

import com.endless.study.baselibrary.common.download.constant.DownloadError;
import com.endless.study.baselibrary.common.download.enums.DownloadStatus;
import com.endless.study.baselibrary.common.download.interfaces.DownloadApi;
import com.endless.study.baselibrary.common.logger.Logger;
import com.endless.study.baselibrary.database.entity.DownloadEntity;
import com.endless.study.baselibrary.utils.UtilFile;

import java.io.File;
import java.io.InterruptedIOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import androidx.annotation.Nullable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okio.Okio;

/**
 * 下载服务
 * @author haosiyuan
 * @date 2019/4/5 1:50 PM
 */
public class DownloadService extends Service {

    /**
     * 下载集合
     */
    private final ConcurrentMap<DownloadEntity, Disposable> downloadEntityMap = new ConcurrentHashMap<>();

    public DownloadCallback downloadCallback;

    /**
     * 主线程
     */
    private Handler handler = new Handler(Looper.getMainLooper());

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e(getClass().getSimpleName(), String.format("on bind,intent = %s", intent.toString()));
        return new DownloadBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.errorInfo("DownloadService>>>>>>onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 销毁
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        Logger.errorInfo("DownloadService>>>>>>onDestroy");
        for (DownloadEntity entity : downloadEntityMap.keySet()) {

            if (downloadEntityMap.get(entity) != null && !downloadEntityMap.get(entity).isDisposed()) {
                downloadEntityMap.get(entity).dispose();
            }
        }

        if (downloadCallback != null) {
            try {
                downloadCallback.onDownloadInfoRemove();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 开始下载
     * @param downloadEntity 下载配置
     */
    private void startDownload(DownloadEntity downloadEntity) {
        Logger.errorInfo("startDownload>>>>>>startDownload");
        File file = new File(downloadEntity.getFilePath() + downloadEntity.getFileName());
        Logger.errorInfo(downloadEntity.getFilePath());
        long length = 0L;

        if (file.exists()) {
            length = file.length();
        }

        String range = "bytes=" + length + "-";

        Logger.errorInfo("range>>>>>>>" + range);

        Disposable disposable = DownloadRetrofit.getInstance()
                                    .getRetrofit(downloadEntity.getUrl(), downloadCallback, downloadEntity)
                                    .create(DownloadApi.class)
                                    .download(range, downloadEntity.getUrl())
                                    .subscribeOn(Schedulers.io())
                                    .unsubscribeOn(Schedulers.io())
                                    .map(responseBody -> responseBody.byteStream())
                                    .observeOn(Schedulers.computation())
                                    .doOnSubscribe(disposable1 -> downloadStatusChange(downloadEntity, DownloadStatus.downloading))
                                    .doOnNext(inputStream -> {

                                            UtilFile.writeInput(downloadEntity.getFilePath(), downloadEntity.getFileName(), inputStream);

                                    }).observeOn(AndroidSchedulers.mainThread())
                                    .doOnComplete(() -> {

                                        //下载完成
                                        downloadEntityMap.remove(downloadEntity);
                                        if (downloadCallback != null) {
                                            downloadCallback.onDownloadSuccess(downloadEntity.getId());
                                        }
                                    })
                                    .doOnDispose(() -> {

                                        //解绑时
                                        downloadEntityMap.remove(downloadEntity);
                                        downloadStatusChange(downloadEntity, DownloadStatus.pause);

                                    })
                                    .subscribe(inputStream -> {

                                    }, throwable -> {
                                        //下载出错
                                        downloadEntityMap.remove(downloadEntity);
                                        if (downloadCallback != null) {
                                            downloadCallback.onDownloadError(downloadEntity.getId(), DownloadError.DownloadFailed,
                                                    DownloadError.isDownloadFailed);
                                        }
                                    });

        downloadEntityMap.put(downloadEntity, disposable);
    }

    /**
     * 更新状态
     * @param downloadEntity
     * @param downloadStatus
     */
    private void downloadStatusChange(DownloadEntity downloadEntity, @DownloadStatus int downloadStatus) {

        if (downloadCallback != null){

            synchronized (this.downloadCallback){

                handler.post(() -> {
                    try {
                        downloadCallback.onDownloadStatusChanged(downloadEntity.getId(), downloadStatus);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                });
            }
        }
    }

    /**
     * 下载binder
     */
    class DownloadBinder extends DownloadAidlService.Stub {

        @Override
        public void startDownloadTask(DownloadEntity config) throws RemoteException {

            if (!downloadEntityMap.containsKey(config)) {
                startDownload(config);
            }
        }

        @Override
        public void stopDownloadTask(DownloadEntity config) throws RemoteException {

            synchronized (downloadEntityMap) {

                if (downloadEntityMap.containsKey(config)) {

                    if (!downloadEntityMap.get(config).isDisposed()) {

                        downloadEntityMap.get(config).dispose();
                    }
                    downloadEntityMap.remove(config);
                }
            }
        }

        @Override
        public void registerCallBack(DownloadCallback callback) throws RemoteException {
            downloadCallback = callback;
        }

        @Override
        public void unregisterCallBack(DownloadCallback callback) throws RemoteException {
            downloadCallback = null;
        }
    }
}
