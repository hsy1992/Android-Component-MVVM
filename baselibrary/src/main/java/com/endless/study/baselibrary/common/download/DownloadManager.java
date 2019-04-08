package com.endless.study.baselibrary.common.download;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.endless.permission.annotation.Permission;
import com.endless.permission.annotation.PermissionCancel;
import com.endless.permission.annotation.PermissionContext;
import com.endless.permission.annotation.PermissionNeed;
import com.endless.permission.annotation.PermissionRefuse;
import com.endless.permission.entity.PermissionCancelEntity;
import com.endless.permission.entity.PermissionRefuseEntity;

/**
 * 下载控制器 支持断点下载 支持多任务下载
 * @author haosiyuan
 * @date 2019/4/5 1:11 PM
 */
public class DownloadManager {

    private static final int REQUEST_CODE_APP_INSTALL = 20000;

    @PermissionContext
    private Activity activity;
    private DownloadConfig config;

    private DownloadManager() {
    }

    public static DownloadManager build() {
        return new DownloadManager();
    }

    /**
     * 权限判断
     * @param config
     */
    public void download(DownloadConfig config, Activity activity) {
        this.activity = activity;
        this.config = config;
        download();
    }

    @PermissionNeed(permission = {Permission.StorageGroup.READ_EXTERNAL_STORAGE,
            Permission.StorageGroup.WRITE_EXTERNAL_STORAGE, Permission.PhoneGroup.REQUEST_INSTALL_PACKAGES})
    private void download() {
        Intent intent = new Intent(activity, DownloadService.class);
        activity.bindService(intent, getServiceConnection(), Context.BIND_AUTO_CREATE);
    }

    @PermissionRefuse
    private void downloadRefuse(PermissionRefuseEntity entity) {

    }

    @PermissionCancel
    private void downloadCancel(PermissionCancelEntity entity) {

    }

    /**
     * 获取Service 连接
     * @return
     */
    private ServiceConnection getServiceConnection() {

        return new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                ((DownloadService.DownloadBinder)service).start(config);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };
    }

}
