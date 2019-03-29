package com.endless.study.loginlibrary;

import android.app.Application;

import com.endless.study.loginlibrary.type.callback.DefaultExceptionCallBack;
import com.endless.study.loginlibrary.type.interfaces.IExceptionCallBack;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

/**
 * 登录配置
 * @author haosiyuan
 * @date 2019/3/11 4:41 PM
 */
public class LoginConfig {

    /**
     * 验证码时间
     */
    private int verificationTime = 60;

    /**
     * 还不能获取验证码的提示
     */
    private String canNotGetVerificationCodeTips;

    /**
     * 错误次数
     */
    private int errorTimes;

    /**
     * 手机为空默认提示
     */
    private String phoneEmptyTips;

    /**
     * 手机号错误默认提示
     */
    private String phoneErrorTips;

    /**
     * 密码为空提示
     */
    private String passwordEmptyTips;

    /**
     * 密码位数提示
     */
    private String passwordNumTips;

    /**
     * 最小密码位数
     */
    private int passwordMin = 6;

    /**
     * 最大密码位数
     */
    private int passwordMax = 16;

    /**
     * 验证码为空提示
     */
    private String verificationCodeEmptyTips;

    /**
     * 验证码位数提示
     */
    private String verificationCodeNumTips;

    /**
     * 验证码位数
     */
    private int verificationCodeNum = 4;

    /**
     * {@link Application}
     */
    private Application mApplication;

    /**
     * 异常回调
     */
    private IExceptionCallBack exceptionCallBack;

    private LoginConfig() {
    }

    public int getVerificationTime() {
        return verificationTime;
    }

    public int getErrorTimes() {
        return errorTimes;
    }

    public String getPhoneEmptyTips() {
        return phoneEmptyTips;
    }

    public String getPhoneErrorTips() {
        return phoneErrorTips;
    }

    public String getPasswordEmptyTips() {
        return passwordEmptyTips;
    }

    public String getPasswordNumTips() {
        return passwordNumTips;
    }

    public int getPasswordMin() {
        return passwordMin;
    }

    public int getPasswordMax() {
        return passwordMax;
    }

    public String getVerificationCodeEmptyTips() {
        return verificationCodeEmptyTips;
    }

    public int getVerificationCodeNum() {
        return verificationCodeNum;
    }

    public String getVerificationCodeNumTips() {
        return verificationCodeNumTips;
    }

    public String getCanNotGetVerificationCodeTips() {
        return canNotGetVerificationCodeTips;
    }

    public Application getApplication() {
        return mApplication;
    }

    public IExceptionCallBack getExceptionCallBack() {
        return exceptionCallBack;
    }

    public static final class Builder {
        private int verificationTime = 60;
        private String canNotGetVerificationCodeTips;
        private int errorTimes;
        private String phoneEmptyTips;
        private String phoneErrorTips;
        private String passwordEmptyTips;
        private String passwordNumTips;
        private int passwordMin = 6;
        private int passwordMax = 16;
        private String verificationCodeEmptyTips;
        private String verificationCodeNumTips;
        private int verificationCodeNum = 4;
        private Application mApplication;
        private IExceptionCallBack exceptionCallBack;

        private Builder(Application mApplication) {
            this.mApplication = mApplication;
        }

        public static Builder loginConfig(@NonNull Application mApplication) {
            return new Builder(mApplication);
        }

        public Builder withVerificationTime(int verificationTime) {
            this.verificationTime = verificationTime;
            return this;
        }

        public Builder withCanNotGetVerificationCodeTips(String canNotGetVerificationCodeTips) {
            this.canNotGetVerificationCodeTips = canNotGetVerificationCodeTips;
            return this;
        }

        public Builder withErrorTimes(int errorTimes) {
            this.errorTimes = errorTimes;
            return this;
        }

        public Builder withPhoneEmptyTips(String phoneEmptyTips) {
            this.phoneEmptyTips = phoneEmptyTips;
            return this;
        }

        public Builder withPhoneErrorTips(String phoneErrorTips) {
            this.phoneErrorTips = phoneErrorTips;
            return this;
        }

        public Builder withPasswordEmptyTips(String passwordEmptyTips) {
            this.passwordEmptyTips = passwordEmptyTips;
            return this;
        }

        public Builder withPasswordNumTips(String passwordNumTips) {
            this.passwordNumTips = passwordNumTips;
            return this;
        }

        public Builder withPasswordMin(int passwordMin) {
            this.passwordMin = passwordMin;
            return this;
        }

        public Builder withPasswordMax(int passwordMax) {
            this.passwordMax = passwordMax;
            return this;
        }

        public Builder withVerificationCodeEmptyTips(String verificationCodeEmptyTips) {
            this.verificationCodeEmptyTips = verificationCodeEmptyTips;
            return this;
        }

        public Builder withVerificationCodeNumTips(String verificationCodeNumTips) {
            this.verificationCodeNumTips = verificationCodeNumTips;
            return this;
        }

        public Builder withVerificationCodeNum(int verificationCodeNum) {
            this.verificationCodeNum = verificationCodeNum;
            return this;
        }

        public Builder withIExceptionCallBack(IExceptionCallBack exceptionCallBack) {
            this.exceptionCallBack = exceptionCallBack;
            return this;
        }

        public LoginConfig build() {
            LoginConfig loginConfig = new LoginConfig();
            loginConfig.passwordMin = this.passwordMin;
            loginConfig.errorTimes = this.errorTimes;
            loginConfig.passwordNumTips = this.passwordNumTips == null
                    ? getDefaultString(R.string.passwordNumTips) : this.passwordNumTips;
            loginConfig.verificationCodeNumTips = this.verificationCodeNumTips == null
                    ? getDefaultString(R.string.verificationCodeNumTips) : this.verificationCodeNumTips;
            loginConfig.verificationCodeNum = this.verificationCodeNum;
            loginConfig.phoneEmptyTips = this.phoneEmptyTips == null
                    ? getDefaultString(R.string.phoneEmptyTips) : this.phoneEmptyTips;
            loginConfig.verificationTime = this.verificationTime;
            loginConfig.canNotGetVerificationCodeTips = this.canNotGetVerificationCodeTips == null
                    ? getDefaultString(R.string.canNotGetVerificationCodeTips) : this.canNotGetVerificationCodeTips;
            loginConfig.phoneErrorTips = this.phoneErrorTips == null
                    ? getDefaultString(R.string.phoneErrorTips) : this.phoneErrorTips;
            loginConfig.passwordEmptyTips = this.passwordEmptyTips == null
                    ? getDefaultString(R.string.passwordEmptyTips) : this.passwordEmptyTips;
            loginConfig.verificationCodeEmptyTips = this.verificationCodeEmptyTips == null
                    ? getDefaultString(R.string.verificationCodeEmptyTips) : this.verificationCodeEmptyTips;
            loginConfig.passwordMax = this.passwordMax;
            loginConfig.mApplication = this.mApplication;
            loginConfig.exceptionCallBack = this.exceptionCallBack == null
                    ? new DefaultExceptionCallBack(mApplication) : this.exceptionCallBack;
            return loginConfig;
        }

        private String getDefaultString(@StringRes int stringRes) {
            return mApplication.getResources().getString(stringRes);
        }
    }
}
