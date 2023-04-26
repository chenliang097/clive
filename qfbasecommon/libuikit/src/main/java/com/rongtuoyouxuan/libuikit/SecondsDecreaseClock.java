package com.rongtuoyouxuan.libuikit;

import android.os.Handler;
import android.os.Looper;
import android.view.View;

/**
 * 创建人:yangzhiqian
 * view倒计时
 * 创建时间:2018/11/12 11:29
 */
public final class SecondsDecreaseClock implements Runnable, View.OnAttachStateChangeListener {
    private View mAttachedView;
    private SecondsDecreaseListener mListener;
    private long remainTime;
    private Handler mMainHandler = new Handler(Looper.getMainLooper());
    private boolean mIsEnd = false;

    /**
     * 开始倒计时
     *
     * @param view     附着的view,view Dettach后会停止倒计时
     * @param start    倒计时时间
     * @param listener 倒计时过程监听器
     */
    public void attach(View view, long start, SecondsDecreaseListener listener) {
        if (this.mAttachedView != null) {
            //先前attach过一个view
            if (this.mAttachedView == view) {
                //两次attach的view为同一个，重新postRun
                this.remainTime = start;
                this.mListener = listener;
                this.mIsEnd = false;
                view.removeOnAttachStateChangeListener(this);
                view.addOnAttachStateChangeListener(this);
                view.removeCallbacks(this);
                view.postDelayed(this, 1000);
                return;
            } else {
                //两次attach的view不同,remove先前的监听
                this.mAttachedView.removeOnAttachStateChangeListener(this);
            }
        }
        mMainHandler.removeCallbacks(this);
        //通知上个attach结束
        notifyListener(true);
        this.remainTime = start;
        this.mListener = listener;
        this.mAttachedView = view;
        this.mIsEnd = false;
        if (view != null) {
            view.removeCallbacks(this);
            view.postDelayed(this, 1000);
            view.addOnAttachStateChangeListener(this);
        } else {
            mMainHandler.removeCallbacks(this);
            mMainHandler.postDelayed(this, 1000);
        }
    }

    @Override
    public void onViewAttachedToWindow(View v) {
    }

    @Override
    public void onViewDetachedFromWindow(View v) {
        this.mAttachedView.removeOnAttachStateChangeListener(this);
        notifyListener(true);
    }

    @Override
    public void run() {
        if (mIsEnd) {
            return;
        }
        remainTime--;
        remainTime = remainTime < 0 ? 0 : remainTime;
        notifyListener(remainTime <= 0);
        if (!mIsEnd) {
            //下一秒
            if (mAttachedView != null) {
                mAttachedView.removeCallbacks(this);
                mAttachedView.postDelayed(this, 1000);
            } else {
                mMainHandler.removeCallbacks(this);
                mMainHandler.postDelayed(this, 1000);
            }
        }
    }

    private void notifyListener(boolean end) {
        if (this.mIsEnd) {
            return;
        }
        this.mIsEnd = end;
        if (mListener != null) {
            mListener.onDecrease(remainTime, end);
        }
    }

    public interface SecondsDecreaseListener {
        /**
         * 时间减少或者View Detached时调用
         *
         * @param seconds 剩余秒数
         * @param end     是否结束了倒计时
         */
        void onDecrease(long seconds, boolean end);
    }

}