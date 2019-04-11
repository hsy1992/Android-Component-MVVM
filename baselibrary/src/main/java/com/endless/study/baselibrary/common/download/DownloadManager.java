package com.endless.study.baselibrary.common.download;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.text.TextUtils;

import com.endless.permission.annotation.Permission;
import com.endless.permission.annotation.PermissionCancel;
import com.endless.permission.annotation.PermissionContext;
import com.endless.permission.annotation.PermissionNeed;
import com.endless.permission.annotation.PermissionRefuse;
import com.endless.permission.entity.PermissionCancelEntity;
import com.endless.permission.entity.PermissionRefuseEntity;
import com.endless.rxbus.RxBus;
import com.endless.study.baselibrary.common.download.constant.DownloadError;
import com.endless.study.baselibrary.common.download.enums.DownloadStatus;
import com.endless.study.baselibrary.common.download.enums.DownloadStopMode;
import com.endless.study.baselibrary.common.download.interfaces.IDownloadListener;
import com.endless.study.baselibrary.common.logger.Logger;
import com.endless.study.baselibrary.database.AppDatabase;
import com.endless.study.baselibrary.database.entity.DownloadEntity;
import com.endless.study.baselibrary.utils.UtilApp;
import com.endless.study.baselibrary.utils.UtilDate;

import org.reactivestreams.Publisher;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.annotation.NonNull;


/**
 * 下载控制器 支持断点下载 支持多任务下载
 * @author haosiyuan
 * @date 2019/4/5 1:11 PM
 */
@Singleton
public class DownloadManager implements DownloadCallback {

    @PermissionContext
    public Application application;

    private Intent serviceIntent;

    private DownloadAidlService downloadController;

    /**
     * 最大同时下载数
     */
    private final static int MAX_DOWNLOAD = 2;
    /**
     * 正在下载的集合
     */
    private final List<DownloadEntity> downloadTaskList = new ArrayList<>();
    /**
     * 下载事件分发
     */
    private final List<IDownloadListener> iDownloadListeners = new CopyOnWriteArrayList<>();

    private AppDatabase appDatabase;

    /**
     * 主线程
     */
    private Handler handler = new Handler(Looper.getMainLooper());

    @Inject
    public DownloadManager(Application application, AppDatabase appDatabase) {
        this.application = application;
        this.appDatabase = appDatabase;
    }

    /**
     * 权限判断
     */
    @PermissionNeed(permission = {Permission.StorageGroup.READ_EXTERNAL_STORAGE,
            Permission.StorageGroup.WRITE_EXTERNAL_STORAGE, Permission.PhoneGroup.REQUEST_INSTALL_PACKAGES})
    public void download(DownloadEntity config) {

        if (serviceIntent == null) {
            serviceIntent = new Intent(application, DownloadService.class);
            application.getApplicationContext().bindService(serviceIntent, getServiceConnection(config), Context.BIND_AUTO_CREATE);
        } else {
            downloadFromDatabase(config);
        }

    }


    /**
     * 从数据库获取 下载资源
     * @param config
     * @return
     */
    private synchronized long downloadFromDatabase(DownloadEntity config) {

        //文件
        File file = new File(config.getFilePath() + config.getFileName());

        //查找数据库是否含有此下载链接
        DownloadEntity downloadEntity = loadByFilePath(config.getUrl(), config.getFilePath());

        //下载id
        long downloadId = -1;

        if (downloadEntity == null) {

            //查找文件路径是否有文件
            DownloadEntity sameDown = loadByFilePath(config.getFilePath());

            if (sameDown != null){
                //相同的文件路径有该文件
                if (sameDown.getCurrentLength().equals(sameDown.getTotalLength())){
                    //已经下载完成
                    synchronized (iDownloadListeners){

                        for (IDownloadListener iDownloadListener : iDownloadListeners){
                            iDownloadListener.onDownloadError(sameDown.getId(), DownloadError.Downloaded,
                                    DownloadError.isDownloaded);
                        }
                    }
                }
            }

            //插入数据库 添加下载记录
            downloadEntity = new DownloadEntity(config.getFileName(), config.getFilePath(), config.getUrl(),
                    0L,0L, UtilDate.getDateWithYMDHMS(),"0",
                    config.getStatus(), config.getPriority(), config.getStopMode());

            downloadId = appDatabase.download().addDownload(downloadEntity);
            downloadEntity.setId(downloadId);
        }

        //判断是否正在下载中
        if (isDownloading(config.getFilePath())) {
            //正在下载中
            synchronized (iDownloadListeners){

                for (IDownloadListener iDownloadListener : iDownloadListeners){
                    iDownloadListener.onDownloadError(downloadId, DownloadError.Downloading,
                            DownloadError.isDownloading);
                }
            }
            return downloadId;
        }

        //判断是否下载完成
        //数据库状态完成
        if (downloadEntity.getStatus() != DownloadStatus.finish) {

            if (downloadEntity.getTotalLength() == 0L || !file.exists() || file.length() == 0L) {
                //数据库标记完成 但是没有下载 切换状态为失败
                downloadEntity.setStatus(DownloadStatus.failed);
            }

            //判断数据库中总长度是否等于文件长度
            if (file.exists() && downloadEntity.getTotalLength() == file.length() && downloadEntity.getTotalLength() != 0){

                downloadEntity.setStatus(DownloadStatus.finish);

                //分发监听为已下载完
                synchronized (iDownloadListeners){
                    for(IDownloadListener iDownloadListener : iDownloadListeners){

                        iDownloadListener.onDownloadError(downloadId, DownloadError.DownloadFinish,
                                DownloadError.isDownloadFinish);
                    }
                }
            }
        } else {
            if (!file.exists() || !downloadEntity.getTotalLength().equals(downloadEntity.getCurrentLength())){
                //未下载完 或者 文件不存在
                downloadEntity.setStatus(DownloadStatus.failed);
            }
        }

        //更新下载状态
        appDatabase.download().updateDownload(downloadEntity);

        //判断更改状态后 判断是否已经下载完成
        if (downloadEntity.getStatus() == DownloadStatus.finish){

            //下载完成，回调应用层 线程回调
            synchronized (iDownloadListeners){

                long finalDownloadId = downloadId;
                handler.post(() -> {
                    for (IDownloadListener iDownloadCallable : iDownloadListeners){
                        iDownloadCallable.onDownloadStatusChanged(finalDownloadId, DownloadStatus.finish);
                    }
                });
            }

            return downloadId;
        }

        //已优先级最高为准
        if (downloadEntity.getPriority() < config.getPriority()) {
            downloadEntity.setPriority(config.getPriority());
        }
        Logger.errorInfo(downloadEntity.toString());
        //下载文件
        reallyDown(downloadEntity);

        return downloadId;
    }

    /**
     * 开始下载
     * @param downloadEntity
     */
    private void reallyDown(DownloadEntity downloadEntity) {
        Logger.errorInfo("downloadController == null");
        if (downloadController != null) {

            Logger.errorInfo("downloadController != null");
            downloadTaskList.add(downloadEntity);

            pauseByPriority(downloadEntity);
        }
    }

    /**
     * 根据优先级暂停
     */
    private void pauseByPriority(DownloadEntity downloadEntity) {
        Logger.errorInfo("pauseByPriority");
        //超出同时下载 最大数量
        Collections.sort(downloadTaskList, new DownloadPriorityComparator());

        if (downloadTaskList.size() > MAX_DOWNLOAD) {

            for (int i = 0; i < downloadTaskList.size(); i++) {

                if (i < 2) {
                    try {
                        downloadController.startDownloadTask(downloadTaskList.get(i));
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                } else {
                    pause(downloadTaskList.get(i).getId(), DownloadStopMode.auto);
                }
            }
        } else {
            try {
                downloadController.startDownloadTask(downloadEntity);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 从数据库获取 根据FilePath
     * @param filePath
     * @return
     */
    private DownloadEntity loadByFilePath(String filePath) {
        return loadByFilePath(null, filePath);
    }

    /**
     * 从数据库获取 根据FilePath URrl
     * @return
     */
    private DownloadEntity loadByFilePath(String url, String filePath) {
        //查找数据库是否含有此下载链接
        List<DownloadEntity> downloadEntityList;
        downloadEntityList = TextUtils.isEmpty(url) ? appDatabase.download()
                .loadByFilePath(filePath) : appDatabase.download().loadByFilePath(url, filePath);

        DownloadEntity downloadEntity = null;

        if (downloadEntityList != null && !downloadEntityList.isEmpty()) {
            downloadEntity = downloadEntityList.get(0);
        }

        return downloadEntity;
    }

    /**
     * 是否正在下载
     * @param filePath
     * @return
     */
    private boolean isDownloading(String filePath) {

        for (DownloadEntity downloadEntity : downloadTaskList) {

            if (filePath.equals(downloadEntity.getFilePath()) &&
                    downloadEntity.getStatus() == DownloadStatus.downloading) {
                return true;
            }
        }

        return false;
    }

    /**
     * 停止下载
     * @param downloadId
     */
    public void pause(long downloadId, @DownloadStopMode int mode){

        final DownloadEntity downloadInfo = appDatabase.download().loadById(downloadId);
        if (downloadInfo != null) {

            for (DownloadEntity downing : downloadTaskList) {
                if(downloadId == downing.getId()) {
                    try {
                        downloadController.stopDownloadTask(downing);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void pause(@NonNull DownloadEntity downloadEntity, @DownloadStopMode int mode){

        DownloadEntity downloadInfo = loadByFilePath(downloadEntity.getUrl(), downloadEntity.getFilePath());
        pauseAll(downloadInfo);
    }

    private void pauseAll(DownloadEntity downloadInfo) {
        if (downloadInfo != null) {

            for (DownloadEntity downing : downloadTaskList) {
                if(downloadInfo.getId() == downing.getId()) {
                    try {
                        downloadController.stopDownloadTask(downing);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @PermissionRefuse
    private void downloadRefuse(PermissionRefuseEntity entity) {
        Logger.errorInfo(entity.getMessage());
    }

    @PermissionCancel
    private void downloadCancel(PermissionCancelEntity entity) {
        Logger.errorInfo(entity.getMessage());
    }



    /**
     * 获取Service 连接
     * @return
     * @param config
     */
    private ServiceConnection getServiceConnection(DownloadEntity config) {

        return new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {

                Logger.errorInfo("onServiceConnected");
                downloadController = DownloadAidlService.Stub.asInterface(service);

                try {
                    downloadController.registerCallBack(DownloadManager.this);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

                downloadFromDatabase(config);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

                downloadController = null;

                try {
                    downloadController.unregisterCallBack(DownloadManager.this);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        };
    }


    @Override
    public void onDownloadInfoAdd(long downloadId) throws RemoteException {

    }

    @Override
    public void onDownloadInfoRemove() throws RemoteException {

        appDatabase.beginTransaction();

        for (DownloadEntity downloadInfo : downloadTaskList) {

            if (downloadInfo != null){
                downloadInfo.setStatus(DownloadStatus.pause);
                downloadInfo.setCurrentLength(new File(downloadInfo.getFilePath()).length());
                downloadInfo.setFinishTime(UtilDate.getDateWithYMDHMS());
                downloadInfo.setStopMode(DownloadStopMode.hand);
                appDatabase.download().updateDownload(downloadInfo);

                synchronized (iDownloadListeners){

                    for (IDownloadListener downloadCallable : iDownloadListeners){
                        downloadCallable.onDownloadInfoRemove();
                    }
                }
            }
        }

        appDatabase.endTransaction();
    }

    @Override
    public void onDownloadStatusChanged(long downloadId, int status) throws RemoteException {
        Logger.errorInfo( "onDownloadStatusChanged>>>>>" + status);
        //downing 和 pause
        // 修改数据库状态
        appDatabase.download().updateDownloadStatusById(downloadId, status);
    }

    @Override
    public void onTotalLengthReceived(long downloadId, long totalLength) throws RemoteException {

        DownloadEntity downloadInfo = appDatabase.download().loadById(downloadId);

        if (downloadInfo != null){
            downloadInfo.setTotalLength(totalLength);
            appDatabase.download().updateDownload(downloadInfo);
        }
    }

    @Override
    public void onCurrentSizeChanged(long downloadId, double downloadPercent, long speed) throws RemoteException {
        Logger.errorInfo(downloadId +">>>>>" + downloadPercent);
    }

    @Override
    public void onDownloadSuccess(long downloadId) throws RemoteException {

        changeStatus(downloadId, DownloadStatus.finish);
        Logger.errorInfo( "onDownloadSuccess>>>>>");
    }


    private void changeStatus(long downloadId, int downloadStatus) {
        //下载完成 更改状态
        DownloadEntity downloadInfo = appDatabase.download().loadById(downloadId);

        if (downloadInfo != null){
            downloadInfo.setStatus(downloadStatus);
            downloadInfo.setCurrentLength(new File(downloadInfo.getFilePath() + downloadInfo.getFileName()).length());
            downloadInfo.setFinishTime(UtilDate.getDateWithYMDHMS());
            downloadInfo.setStopMode(DownloadStopMode.hand);
            appDatabase.download().updateDownload(downloadInfo);

            Logger.errorInfo(downloadInfo.toString() + ">>>>>");
            synchronized (iDownloadListeners){

                for (IDownloadListener downloadCallable : iDownloadListeners){
                    downloadCallable.onDownloadSuccess(downloadInfo.getId());
                }
            }
        }
        //移除正在下载队列
        downloadTaskList.remove(downloadInfo);

        //自动回复已经取消的下载
        resumeAutoCancelItem();
    }

    /**
     * 自动回复已经取消的下载
     */
    private void resumeAutoCancelItem() {

        if (downloadTaskList.size() > 1) {

            if (downloadController != null) {

                try {
                    downloadController.startDownloadTask(downloadTaskList.get(1));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @Override
    public void onDownloadError(long downloadId, int errorCode, String errorMsg) throws RemoteException {
        appDatabase.download().updateDownloadStatusById(downloadId, DownloadStatus.failed);
    }

    @Override
    public IBinder asBinder() {
        return downloadController != null ? downloadController.asBinder() : null;
    }
}
