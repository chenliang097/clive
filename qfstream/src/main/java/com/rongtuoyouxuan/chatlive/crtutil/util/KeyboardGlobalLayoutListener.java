package com.rongtuoyouxuan.chatlive.crtutil.util;

import android.app.Activity;
import android.graphics.Rect;
import android.view.ViewTreeObserver;

public class KeyboardGlobalLayoutListener implements ViewTreeObserver.OnGlobalLayoutListener {


    private KeyboardShowListener mKeyboardShowListener;
    //屏幕高度,这个高度不含虚拟按键的高度
    private int mScreenHeight;
    //状态栏高度
    private int mStatusBarHeight;
    private Activity mActivity;
    // 软键盘的高度
    private boolean mIsSoftKeyboardShowing;
    public KeyboardGlobalLayoutListener(Activity activity) {
        if (activity == null || activity.isFinishing()) return;
        this.mActivity = activity;
        mScreenHeight = DisplayUtil.getScreenHeight(activity.getApplicationContext());
        mStatusBarHeight = DisplayUtil.getStatusBarHeight(activity.getApplicationContext());
    }

    public void setKeyboardShowListener(KeyboardShowListener keyboardShowListener) {
        this.mKeyboardShowListener = keyboardShowListener;
    }

    @Override
    public void onGlobalLayout() {
        if (mActivity == null || mActivity.isFinishing()) return;
        Rect r = new Rect();
        //窗口的可见区域高度
        mActivity.getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
        int heightDiff = mScreenHeight - (r.bottom - r.top);
        boolean isKeyboardShowing = heightDiff > mScreenHeight / 3;
        //如果之前软键盘状态为显示，现在为关闭，或者之前为关闭，现在为显示，则表示软键盘的状态发生了改变
        if ((mIsSoftKeyboardShowing && !isKeyboardShowing) || (!mIsSoftKeyboardShowing && isKeyboardShowing)) {
            mIsSoftKeyboardShowing = isKeyboardShowing;
            int keyboardHeight = heightDiff - mStatusBarHeight;
            if (mIsSoftKeyboardShowing) {
                if (mKeyboardShowListener != null) {
                    mKeyboardShowListener.onKeyboardShow(keyboardHeight);
                }
            } else {
                if (mKeyboardShowListener != null) {
                    mKeyboardShowListener.onKeyboardHide();
                }
            }
        }
    }

    public interface KeyboardShowListener {

        /**
         * 键盘显示
         *
         * @param keyboardHeight 键盘高度
         */
        void onKeyboardShow(int keyboardHeight);

        /**
         * 键盘隐藏
         */
        void onKeyboardHide();
    }
}
