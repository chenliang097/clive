package com.rongtuoyouxuan.chatlive.crtuikit;

import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.View;

public class ViewUtils {

    /**
     * 判断触摸时间是否在一个view内
     *
     * @param view
     * @param event
     * @return
     */
    public static boolean isTouchEventInView(View view, MotionEvent event) {
        if (view == null || view.getVisibility() == View.GONE || event == null) {
            return false;
        }
        float x = event.getRawX(); // 获取相对于屏幕左上角的 x 坐标值
        float y = event.getRawY(); // 获取相对于屏幕左上角的 y 坐标值
        RectF rect = calcViewScreenLocation(view);
        return rect.contains(x, y);
    }

    /**
     * 计算指定的 View 在屏幕中的坐标。
     */
    public static RectF calcViewScreenLocation(View view) {
        int[] location = new int[2];
        // 获取控件在屏幕中的位置，返回的数组分别为控件左顶点的 x、y 的值
        view.getLocationOnScreen(location);
        return new RectF(location[0], location[1], location[0] + view.getWidth(),
                location[1] + view.getHeight());
    }
}
