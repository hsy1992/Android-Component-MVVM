package com.endless.study.loginlibrary.type.callback;

import com.endless.study.loginlibrary.LoginType;
import com.endless.study.loginlibrary.entity.UserEntity;
import com.endless.study.loginlibrary.type.interfaces.IThreeAuthListener;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

/**
 * 获取用户资料回调
 * @author haosiyuan
 * @date 2019/3/17 10:51 AM
 */
public class ThreeAuthCallBack implements UMAuthListener {

    private IThreeAuthListener listener;

    public ThreeAuthCallBack(IThreeAuthListener listener) {
        this.listener = listener;
    }

    @Override
    public void onStart(SHARE_MEDIA share_media) {
        listener.onLoginStart(getLoginType(share_media));
    }

    @Override
    public void onComplete(SHARE_MEDIA share_media, int action, Map<String, String> map) {
        listener.onLoginComplete(getLoginType(share_media),
                new UserEntity(map.get("uid"), map.get("name"), map.get("gender"), map.get("iconurl")));
    }

    @Override
    public void onError(SHARE_MEDIA share_media, int action, Throwable throwable) {
        listener.onLoginError(getLoginType(share_media));
    }

    @Override
    public void onCancel(SHARE_MEDIA share_media, int action) {
        listener.onLoginCancel(getLoginType(share_media));
    }


    private int getLoginType(SHARE_MEDIA share_media) {

        if (share_media == SHARE_MEDIA.QQ) {
            return LoginType.QQ;
        } else if (share_media == SHARE_MEDIA.WEIXIN) {
            return LoginType.WE_CHAT;
        } else if (share_media == SHARE_MEDIA.SINA) {
            return LoginType.WEI_BO;
        }

        return -1;
    }
}
