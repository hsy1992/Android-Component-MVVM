package com.endless.permission.interfaces;

import com.endless.permission.entity.PermissionCancelEntity;
import com.endless.permission.entity.PermissionRefuseEntity;

/**
 * 权限操作回调
 * @author haosiyuan
 * @date 2019/3/29 5:04 PM
 */
public interface IPermissionCallBack {

    /**
     * 同意权限
     */
    void permissionGranted();

    /**
     * 拒绝权限并且选中不再提示
     * @param refuseEntity
     */
    void permissionDenied(PermissionRefuseEntity refuseEntity);

    /**
     * 取消权限
     * @param cancelEntity
     */
    void permissionCanceled(PermissionCancelEntity cancelEntity);


}
