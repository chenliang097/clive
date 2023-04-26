package com.rongtuoyouxuan.chatlive.live.view.floatwindow;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.widget.LinearLayout;

import java.util.HashMap;
import java.util.Map;

import static android.content.Context.WINDOW_SERVICE;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * Author : Ziwen Lan
 * Date : 2020/5/8
 * Time : 11:32
 * Introduction : 悬浮窗功能实现帮助类
 */
public class FloatingWindowHelper {
    private WindowManager mWindowManager;
    private Map<View, WindowManager.LayoutParams> mChildViewMap;
    private int mWidthPixels;
    private int mSlop;

    public FloatingWindowHelper(Context context) {
        mWindowManager = (WindowManager) context.getSystemService(WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        mWindowManager.getDefaultDisplay().getMetrics(outMetrics);
        mWidthPixels = outMetrics.widthPixels;
        mSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    /**
     * 判断是否拥有悬浮窗权限
     *
     * @param isApplyAuthorization 是否申请权限
     */
    public static boolean canDrawOverlays(Context context, boolean isApplyAuthorization) {
        //Android 6.0 以下无需申请权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //判断是否拥有悬浮窗权限，无则跳转悬浮窗权限授权页面
            if (Settings.canDrawOverlays(context)) {
                return true;
            } else {
                if (isApplyAuthorization) {
                    Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + context.getPackageName()));
                    if (context instanceof Service) {
                        intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                    }
                    context.startActivity(intent);
                    return false;
                } else {
                    return false;
                }
            }
        } else {
            return true;
        }
    }

    /**
     * 创建模板 WindowManager.LayoutParams 对象
     */
    private WindowManager.LayoutParams createLayoutParams() {
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            layoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        }
        layoutParams.format = PixelFormat.RGBA_8888;
        layoutParams.gravity = Gravity.START | Gravity.TOP;
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT;
        layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        return layoutParams;
    }

    /**
     * 添加并显示悬浮View
     *
     * @param view 要悬浮的View
     */
    public void addView(View view) {
        if (mWindowManager == null) {
            return;
        }
        if (mChildViewMap == null) {
            mChildViewMap = new HashMap<>();
        }
        if (!mChildViewMap.containsKey(view)) {
            WindowManager.LayoutParams layoutParams = createLayoutParams();
            mChildViewMap.put(view, layoutParams);
            mWindowManager.addView(view, layoutParams);
        }
    }

    /**
     * 添加并（在指定坐标位置）显示悬浮View
     *
     * @param view 要悬浮的View
     */
    public void addView(View view, int x, int y) {
        if (mWindowManager == null) {
            return;
        }
        if (mChildViewMap == null) {
            mChildViewMap = new HashMap<>();
        }
        if (!mChildViewMap.containsKey(view)) {
            WindowManager.LayoutParams layoutParams = createLayoutParams();
            layoutParams.x = x;
            layoutParams.y = y;
            mChildViewMap.put(view, layoutParams);
            mWindowManager.addView(view, layoutParams);
        }
    }

    /**
     * 添加并显示悬浮View
     *
     * @param view    要悬浮的View
     * @param canMove 是否可以拖动
     */
    public void addView(View view, boolean canMove) {
        if (mWindowManager == null) {
            return;
        }
        if (mChildViewMap == null) {
            mChildViewMap = new HashMap<>();
        }
        if (!mChildViewMap.containsKey(view)) {
            WindowManager.LayoutParams layoutParams = createLayoutParams();
            mChildViewMap.put(view, layoutParams);
            mWindowManager.addView(view, layoutParams);
            if (canMove) {
                view.setOnTouchListener(new FloatingOnTouchListener());
            }
        }
    }

    /**
     * 添加并（在指定坐标位置）显示悬浮View
     *
     * @param view    要悬浮的View
     * @param canMove 是否可以拖动
     */
    public void addView(View view, int x, int y, boolean canMove) {
        if (mWindowManager == null) {
            return;
        }
        if (mChildViewMap == null) {
            mChildViewMap = new HashMap<>();
        }
        if (!mChildViewMap.containsKey(view)) {
            WindowManager.LayoutParams layoutParams = createLayoutParams();
            layoutParams.x = x;
            layoutParams.y = y;
            mChildViewMap.put(view, layoutParams);
            mWindowManager.addView(view, layoutParams);
            if (canMove) {
                view.setOnTouchListener(new FloatingOnTouchListener());
            }
        }
    }

    /**
     * 添加并（在指定坐标位置）显示悬浮View
     *
     * @param view    要悬浮的View
     * @param canMove 是否可以拖动
     */
    public void addView(View view, int x, int y, boolean canMove, int width, int height) {
        if (mWindowManager == null) {
            return;
        }
        if (mChildViewMap == null) {
            mChildViewMap = new HashMap<>();
        }
        if (!mChildViewMap.containsKey(view)) {
            WindowManager.LayoutParams layoutParams = createLayoutParams();
            layoutParams.x = x;
            layoutParams.y = y;
            layoutParams.width = width;
            layoutParams.height = height;
            mChildViewMap.put(view, layoutParams);
            mWindowManager.addView(view, layoutParams);
            if (canMove) {
                view.setOnTouchListener(new FloatingOnTouchListener());
            }
        }
    }

    /**
     * 添加并显示悬浮View，自行提供WindowManager.LayoutParams对象
     *
     * @param view         要悬浮的View
     * @param layoutParams WindowManager.LayoutParams 对象
     */
    public void addView(View view, WindowManager.LayoutParams layoutParams) {
        if (mWindowManager == null) {
            return;
        }
        if (mChildViewMap == null) {
            mChildViewMap = new HashMap<>();
        }
        if (!mChildViewMap.containsKey(view)) {
            mChildViewMap.put(view, layoutParams);
            mWindowManager.addView(view, layoutParams);
        }
    }

    /**
     * 判断是否存在该悬浮View
     */
    public boolean contains(View view) {
        if (mChildViewMap != null) {
            return mChildViewMap.containsKey(view);
        }
        return false;
    }

    /**
     * 移除指定悬浮View
     */
    public void removeView(View view) {
        if (mWindowManager == null) {
            return;
        }
        if (mChildViewMap != null) {
            mChildViewMap.remove(view);
        }
        mWindowManager.removeView(view);
    }

    /**
     * 根据 LayoutParams 更新悬浮 View 布局
     */
    public void updateViewLayout(View view, WindowManager.LayoutParams layoutParams) {
        if (mWindowManager == null) {
            return;
        }
        if (mChildViewMap != null && mChildViewMap.containsKey(view)) {
            mChildViewMap.put(view, layoutParams);
            mWindowManager.updateViewLayout(view, layoutParams);
        }
    }

    /**
     * 获取指定悬浮View的 LayoutParams
     */
    public WindowManager.LayoutParams getLayoutParams(View view) {
        if (mChildViewMap.containsKey(view)) {
            return mChildViewMap.get(view);
        }
        return null;
    }

    public void clear() {
        if (mWindowManager == null) {
            return;
        }
        for (View view : mChildViewMap.keySet()) {
            mWindowManager.removeView(view);
        }
        mChildViewMap.clear();
    }

    public void destroy() {
        if (mWindowManager == null) {
            return;
        }
        for (View view : mChildViewMap.keySet()) {
            mWindowManager.removeView(view);
        }
        mChildViewMap.clear();
        mChildViewMap = null;
        mWindowManager = null;
    }

    /**
     * 处理触摸事件实现悬浮View拖动效果
     */
    public class FloatingOnTouchListener implements View.OnTouchListener {
        private int x;
        private int y;
        private float downX;
        private float downY;
        private float upX;
        private float upY;
        private boolean mClick = false;
        private ValueAnimator mAnimator;

        @Override
        public boolean onTouch(View view, MotionEvent event) {
            if (mChildViewMap.containsKey(view)) {
                WindowManager.LayoutParams layoutParams = mChildViewMap.get(view);
                if (layoutParams == null) return false;
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        downX = x = (int) event.getRawX();
                        downY = y = (int) event.getRawY();
                        mClick = false;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        int nowX = (int) event.getRawX();
                        int nowY = (int) event.getRawY();
                        int movedX = nowX - x;
                        int movedY = nowY - y;
                        x = nowX;
                        y = nowY;
                        layoutParams.x = layoutParams.x + movedX;
                        layoutParams.y = layoutParams.y + movedY;
                        if (layoutParams.x < 0) {
                            layoutParams.x = 0;
                        }
                        if (layoutParams.y < 0) {
                            layoutParams.y = 0;
                        }
                        mWindowManager.updateViewLayout(view, layoutParams);
                        break;
                    case MotionEvent.ACTION_UP:
                        upX = event.getRawX();
                        upY = event.getRawY();
                        mClick = (Math.abs(upX - downX) > mSlop) || (Math.abs(upY - downY) > mSlop);
                        mAnimator = ObjectAnimator.ofInt(layoutParams.x, getTargetX(view, (int) event.getRawX()));
                        mAnimator.setDuration(200);
                        mAnimator.addUpdateListener(animation -> {
                            layoutParams.x = (int) animation.getAnimatedValue();
                            if (mChildViewMap != null && mChildViewMap.containsKey(view) && view.getParent() != null) {
                                mWindowManager.updateViewLayout(view, layoutParams);
                            }
                        });
                        mAnimator.start();
                        break;
                    default:
                        break;
                }
            }
            return mClick;
        }

        /**
         * 判定所处方向
         */
        private int getTargetX(View view, int x) {
            int targetX = 0;
            if (x > (mWidthPixels / 2)) {
                targetX = mWidthPixels - view.getMeasuredWidth();
            }
            return targetX;
        }

        /**
         * 判定所处方向
         */
        private void handleDirection(View view, WindowManager.LayoutParams layoutParams, int x, int y) {
            if (x > (mWidthPixels / 2)) {
                layoutParams.x = mWidthPixels - view.getMeasuredWidth();
            } else {
                layoutParams.x = 0;
            }
        }
    }
}
