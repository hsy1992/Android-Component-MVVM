package com.endless.permission;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.endless.permission.annotation.Permission;
import com.endless.permission.annotation.PermissionNeed;
import com.endless.permission.entity.PermissionCancelEntity;
import com.endless.permission.entity.PermissionRefuseEntity;
import com.endless.permission.interfaces.IPermissionCallBack;
import com.endless.permission.util.PermissionUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

/**
 * 申请权限Activity
 * @author haosiyuan
 * @date 2019/3/29 8:35 PM
 */
public class PermissionActivity extends AppCompatActivity {

    /**
     * 回调
     */
    private static IPermissionCallBack permissionCallBack;
    /**
     * 权限列表
     */
    private String[] permissions;
    /**
     * 传值
     */
    private static final String PERMISSION_KEY = "PERMISSION_KEY";

    /**
     * 跳转到Activity申请权限
     *
     * @param context     Context
     * @param permissions 权限list
     * @param listener  回调
     */
    public static void requestPermission(Context context, String[] permissions,
                                         IPermissionCallBack listener) {
        permissionCallBack = listener;
        Intent intent = new Intent(context, PermissionActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Bundle bundle = new Bundle();
        bundle.putStringArray(PERMISSION_KEY, permissions);
        intent.putExtras(bundle);
        context.startActivity(intent);
        if (context instanceof Activity) {
            ((Activity) context).overridePendingTransition(0, 0);
        }

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            permissions = bundle.getStringArray(PERMISSION_KEY);
        }

        if (permissions == null || permissions.length <= 0) {
            finish();
            return;
        }

        requestPermission(permissions);
    }

    /**
     * 申请权限
     *
     * @param permissions
     */
    private void requestPermission(String[] permissions) {

        if (PermissionUtil.hasSelfPermissions(this, permissions)) {
            //所有权限都同意
            if (permissionCallBack != null) {
                permissionCallBack.permissionGranted();
                permissionCallBack = null;
            }
            finish();
            overridePendingTransition(0, 0);
        } else {
            //申请权限
            ActivityCompat.requestPermissions(this, permissions, Permission.RequestCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (PermissionUtil.verifyPermissions(grantResults)) {
            //所有权限都同意
            if (permissionCallBack != null) {
                permissionCallBack.permissionGranted();
            }
        } else {
            if (!PermissionUtil.shouldShowRequestPermissionRationale(this, permissions)) {
                //权限被拒绝并且选中不再提示
                if (permissions.length != grantResults.length) {
                    return;
                }

                List<String> denyList = new ArrayList<>();
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] == -1) {
                        denyList.add(permissions[i]);
                    }
                }

                if (permissionCallBack != null) {
                    permissionCallBack.permissionDenied(new PermissionRefuseEntity("权限被拒绝", denyList));
                }
            } else {
                //权限被取消
                if (permissionCallBack != null) {
                    permissionCallBack.permissionCanceled(new PermissionCancelEntity("权限取消"));
                }
            }
        }

        permissionCallBack = null;
        finish();
        overridePendingTransition(0, 0);
    }
}
