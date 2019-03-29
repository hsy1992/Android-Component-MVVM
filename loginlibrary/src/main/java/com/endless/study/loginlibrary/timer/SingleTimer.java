package com.endless.study.loginlibrary.timer;

import android.os.CountDownTimer;

/**
 * 一个倒计时器
 * @author haosiyuan
 * @date 2019/3/15 10:23 AM
 */
public abstract class SingleTimer extends CountDownTimer {

    private static long oneSecond = 1000l;

    /**
     * 构造方法传入一个 倒计时秒数
     * @param countDownNum
     */
    public SingleTimer(int countDownNum) {
        super(oneSecond * countDownNum, oneSecond);
    }

    /**
     * 返回剩余秒数
     * @param millisUntilFinished
     */
    @Override
    public void onTick(long millisUntilFinished) {
        onTickLeft((int) (millisUntilFinished / oneSecond));
    }

    /**
     * 倒计时结束
     */
    @Override
    public void onFinish() {
        onTimerFinish();
    }

    /**
     * 剩余时间
     * @param leftNum
     */
    protected abstract void onTickLeft(int leftNum);

    /**
     * 倒计时结束
     */
    protected abstract void onTimerFinish();
}
