package com.endless.permission.entity;

import java.util.List;

/**
 * 权限拒绝
 * @author haosiyuan
 * @date 2019/3/29 3:38 PM
 */
public class PermissionRefuseEntity extends ResultEntity {

    private List<String> permission;

    public PermissionRefuseEntity(String message, List<String> permission) {
        super(message);
        this.permission = permission;
    }

    public List<String> getPermission() {
        return permission;
    }

    public void setPermission(List<String> permission) {
        this.permission = permission;
    }

    @Override
    public String toString() {
        return "PermissionRefuseEntity{" +
                "permission=" + permission +
                '}';
    }
}
