package com.rongtuoyouxuan.chatlive.crtutil.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

/**
 * 说明：软键盘工具类
 * <p>
 * 作者：fanly
 * <p>
 * 类型：Class
 * <p>
 * 时间：2016/8/18 15:37
 * <p>
 * 版本：verson 1.0
 */
public class KeyBoardUtils {

    private static int rootViewVisibleHeight;/*纪录根视图的显示高度*/
    private static List<OnSoftKeyboardStateChangedListener> onSoftKeyboardStateChangedListeners = new ArrayList<>(2);

    /**
     * 说明：强制显示软键盘
     *
     * @param et
     */
    public static void showSoftInput(EditText et) {
        if (et != null) {
            et.requestFocus();
            InputMethodManager inputManager = (InputMethodManager) et
                    .getContext()
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.showSoftInput(et, InputMethodManager.SHOW_FORCED);
        }
    }

    //隐藏输入法=
    public static void hideSoftInputBox(Activity activity) {
        try {
            if (activity == null || activity.isFinishing()) return;
            Window window = activity.getWindow();
            if (window == null) return;
            View view = window.getCurrentFocus();
            if (view == null) return;
            InputMethodManager imm = (InputMethodManager) activity.getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm == null || !imm.isActive()) return;
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    /**
     * 说明：强制隐藏软键盘
     */
    public static void hiddenSoftInput(EditText editText) {
        if (editText != null) {
            InputMethodManager inputManager = (InputMethodManager) editText
                    .getContext()
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputManager.isActive()) {
                inputManager.hideSoftInputFromWindow(editText.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    /**
     * <<<<<<< HEAD
     * =======
     * 动态隐藏软键盘
     *
     * @param activity activity
     */
    public static void hideSoftInput(final Activity activity) {
        View view = activity.getCurrentFocus();
        if (view == null) view = new View(activity);
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (imm == null) return;
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * 屏幕分辨率高
     *
     * @param paramActivity
     * @return
     */
    public static int getScreenHeight(Activity paramActivity) {
        Display display = paramActivity.getWindowManager().getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        return metrics.heightPixels;
    }

    /**
     * statusBar高度
     *
     * @param paramActivity
     * @return
     */
    public static int getStatusBarHeight(Activity paramActivity) {
        Rect localRect = new Rect();
        paramActivity.getWindow().getDecorView().getWindowVisibleDisplayFrame(localRect);
        return localRect.top;
    }

    /**
     * 可见屏幕高度
     *
     * @param paramActivity
     * @return
     */
    public static int getAppHeight(Activity paramActivity) {
        Rect localRect = new Rect();
        paramActivity.getWindow().getDecorView().getWindowVisibleDisplayFrame(localRect);
        return localRect.height();
    }

    /**
     * 键盘是否在显示
     *
     * @param paramActivity
     * @return
     */
    public static boolean isKeyBoardShow(Activity paramActivity) {
        int height = getScreenHeight(paramActivity) - getStatusBarHeight(paramActivity)
                - getAppHeight(paramActivity);
        return height != 0;
    }

    public static ViewTreeObserver.OnGlobalLayoutListener setOnGlobalLayoutListener(final Activity activity, final OnSoftKeyboardStateChangedListener listener) {
        if (listener == null || activity == null) return null;
        onSoftKeyboardStateChangedListeners.add(listener);
        final View rootView = activity.getWindow().getDecorView();
        ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                /*获取当前根视图在屏幕上显示的大小*/
                Rect r = new Rect();
                rootView.getWindowVisibleDisplayFrame(r);
                int visibleHeight = r.height();
                if (rootViewVisibleHeight == 0) {
                    rootViewVisibleHeight = visibleHeight;
                    return;
                }
                /*根视图显示高度没有变化，可以看作软键盘显示／隐藏状态没有改变*/
                if (rootViewVisibleHeight == visibleHeight) {
                    return;
                }
                /*根视图显示高度变小超过200，可以看作软键盘显示了*/
                if (rootViewVisibleHeight - visibleHeight > 200) {
                    for (OnSoftKeyboardStateChangedListener li : onSoftKeyboardStateChangedListeners) {
                        li.onSoftKeyboardShow(rootViewVisibleHeight - visibleHeight);
                    }
                    rootViewVisibleHeight = visibleHeight;
                    return;
                }
                /*根视图显示高度变大超过200，可以看作软键盘隐藏了*/
                if (visibleHeight - rootViewVisibleHeight > 200) {
                    for (OnSoftKeyboardStateChangedListener li : onSoftKeyboardStateChangedListeners) {
                        li.onSoftKeyboardHide(visibleHeight - rootViewVisibleHeight);
                    }
                    rootViewVisibleHeight = visibleHeight;
                    return;
                }
            }
        };
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(onGlobalLayoutListener);
        return onGlobalLayoutListener;
    }

    /**
     * 不要用这个方法了，会内存泄漏。
     * 请使用 {@link #removeOnGlobalLayoutListener(Activity, ViewTreeObserver.OnGlobalLayoutListener, OnSoftKeyboardStateChangedListener)}
     */
    @Deprecated()
    public static void removeOnGlobalLayoutListener(final Activity activity, ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener) {
        if (activity == null) return;
        final View rootView = activity.getWindow().getDecorView();
        rootView.getViewTreeObserver().removeOnGlobalLayoutListener(onGlobalLayoutListener);
        onSoftKeyboardStateChangedListeners.remove(onGlobalLayoutListener);
    }

    public static void removeOnGlobalLayoutListener(final Activity activity, ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener, OnSoftKeyboardStateChangedListener listener) {
        if (activity == null) return;
        final View rootView = activity.getWindow().getDecorView();
        rootView.getViewTreeObserver().removeOnGlobalLayoutListener(onGlobalLayoutListener);
        onSoftKeyboardStateChangedListeners.remove(listener);
    }

    public interface OnSoftKeyboardStateChangedListener {
        void onSoftKeyboardShow(int height);

        void onSoftKeyboardHide(int height);
    }

    public static boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if ((v instanceof EditText)) {  //判断得到的焦点控件是否包含EditText
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],    //得到输入框在屏幕中上下左右的位置
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            // 点击位置如果是EditText的区域，忽略它，不收起键盘。
            return !(event.getX() > left) || !(event.getX() < right)
                    || !(event.getY() > top) || !(event.getY() < bottom);
        }
        // 如果焦点不是EditText则忽略
        return false;
    }

}
