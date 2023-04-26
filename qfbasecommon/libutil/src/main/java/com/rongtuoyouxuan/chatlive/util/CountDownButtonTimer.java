package com.rongtuoyouxuan.chatlive.util;

import android.os.CountDownTimer;

import java.lang.ref.WeakReference;


/**
 * 要在页面ondestory调用   mTimer.cancel(); mTimer = null; 否则有问题
 */
public class CountDownButtonTimer extends CountDownTimer {

    public interface OnCountTimerListener {
        void onTick(long seconds);

        void onFinish();
    }

    private static final long COUNT_DOWN_INTERVAL = 1000;

    private final OnCountTimerListener weakListener;

    /**
     * @param seconds  倒计时器，单位是秒
     * @param listener
     */
    public CountDownButtonTimer(long seconds, OnCountTimerListener listener) {
        super(seconds * COUNT_DOWN_INTERVAL, COUNT_DOWN_INTERVAL);
        this.weakListener = new WeakReference<>(listener).get();
    }

    @Override
    public void onTick(long millisUntilFinished) {
        long seconds = millisUntilFinished / 1000;
        weakListener.onTick(seconds);
    }

    @Override
    public void onFinish() {
        weakListener.onFinish();
    }
}
