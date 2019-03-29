package com.endless.study.loginlibrary.type;

import android.text.TextUtils;
import android.util.Log;

import com.endless.study.loginlibrary.LoginConfig;
import com.endless.study.loginlibrary.LoginException;
import com.endless.study.loginlibrary.timer.SingleTimer;
import com.endless.study.loginlibrary.type.interfaces.ICheck;
import com.endless.study.loginlibrary.type.interfaces.IThrowableTag;
import com.endless.study.loginlibrary.utils.StringUtil;

/**
 * 检查
 * @author haosiyuan
 * @date 2019/3/14 6:58 PM
 */
class DefaultCheck implements ICheck {

    private SingleTimer timer;

    private boolean canGetVerificationCode = true;

    /**
     * 检查手机号
     * @param config
     * @param phone
     */
    @Override
    public void checkPhone(LoginConfig config, String phone) {

        if (TextUtils.isEmpty(phone)) {
            throw new LoginException(IThrowableTag.PHONE_EMPTY_TIPS,
                    config.getPhoneEmptyTips());
        }

        if (!StringUtil.isPhoneNum(phone)) {
            throw new LoginException(IThrowableTag.PHONE_ERROR_TIPS,
                    config.getPhoneErrorTips());
        }
    }

    /**
     * 检查密码
     * @param config
     * @param password
     */
    @Override
    public void checkPassword(LoginConfig config, String password) {

        if (TextUtils.isEmpty(password)) {
            throw new LoginException(IThrowableTag.PASSWORD_EMPTY_TIPS,
                    config.getPasswordEmptyTips());
        }

        if (password.length() < config.getPasswordMin() || password.length() > config.getPasswordMax()) {
            throw new LoginException(IThrowableTag.PASSWORD_NUM_TIPS,
                    config.getPasswordNumTips());
        }
    }

    /**
     * 检查验证码
     * @param config
     * @param verificationCode
     */
    @Override
    public void checkVerificationCode(LoginConfig config, String verificationCode) {

        if (TextUtils.isEmpty(verificationCode)) {
            throw new LoginException(IThrowableTag.VERIFICATION_CODE_EMPTY_TIPS,
                    config.getVerificationCodeEmptyTips());
        }

        if (verificationCode.length() != config.getVerificationCodeNum()) {
            throw new LoginException(IThrowableTag.VERIFICATION_CODE_NUM_TIPS,
                    config.getVerificationCodeNumTips());
        }
    }

    /**
     * 能否获取验证码
     * @param config
     * @return
     */
    @Override
    public boolean canGetVerificationCode(LoginConfig config) {

        if (timer == null) {
            timer = new SingleTimer(config.getVerificationTime()) {
                @Override
                protected void onTickLeft(int leftNum) {

                    Log.e("test", leftNum +">>>>>>>");
                }

                @Override
                protected void onTimerFinish() {
                    canGetVerificationCode = true;
                }
            };
        }

        if (canGetVerificationCode) {

            timer.start();
            canGetVerificationCode = false;
        } else {
            throw new LoginException(IThrowableTag.CAN_NOT_GET_VERIFICATION_CODE_TIPS,
                    config.getCanNotGetVerificationCodeTips());
        }

        return canGetVerificationCode;
    }
}
