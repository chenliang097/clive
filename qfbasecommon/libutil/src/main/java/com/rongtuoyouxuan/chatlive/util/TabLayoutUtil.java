package com.rongtuoyouxuan.chatlive.util;

import android.content.res.Resources;
import com.google.android.material.tabs.TabLayout;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;

import java.lang.reflect.Field;

/**
 * 创建人:yangzhiqian
 * 创建时间:2018/10/26 14:43
 */
public class TabLayoutUtil {

    /**
     * 给tag设置margin.因为默认的TabLayout的Indicator的宽度为tab的宽度，如果默认情况下产生的
     * 边哭Indicator也会算入宽度，导致Indicator的宽度比tab标题的宽度要长，所以用tab之间的margin解决
     */
    public static void setIndicator(final TabLayout tabs, final float leftDip, final float rightDip) {
        tabs.post(new Runnable() {
            @Override
            public void run() {
                Class<?> tabLayout = tabs.getClass();
                Field tabStrip;
                try {
                    tabStrip = tabLayout.getDeclaredField("mTabStrip");
                } catch (NoSuchFieldException e) {
                    return;
                }

                tabStrip.setAccessible(true);
                LinearLayout llTab;
                try {
                    llTab = (LinearLayout) tabStrip.get(tabs);
                } catch (IllegalAccessException e) {
                    return;
                }

                int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
                int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());

                for (int i = 0; i < llTab.getChildCount(); i++) {
                    View child = llTab.getChildAt(i);
                    child.setPadding(0, 0, 0, 0);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
                    params.leftMargin = left;
                    params.rightMargin = right;
                    child.setLayoutParams(params);
                    child.invalidate();
                }
            }
        });
    }

    public static void setIndicator(final TabLayout tabs, final float gapDp) {
        tabs.post(new Runnable() {
            @Override
            public void run() {
                Class<?> tabLayout = tabs.getClass();
                Field tabStrip;
                try {
                    tabStrip = tabLayout.getDeclaredField("mTabStrip");
                } catch (NoSuchFieldException e) {
                    return;
                }

                tabStrip.setAccessible(true);
                LinearLayout llTab;
                try {
                    llTab = (LinearLayout) tabStrip.get(tabs);
                } catch (IllegalAccessException e) {
                    return;
                }

                int gap = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, gapDp, Resources.getSystem().getDisplayMetrics());

                for (int i = 0; i < llTab.getChildCount(); i++) {
                    View child = llTab.getChildAt(i);
                    child.setPadding(0, 0, 0, 0);
                    if (i >= 1) {
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
                        params.leftMargin = gap;
                        child.setLayoutParams(params);
                    }
                    child.invalidate();
                }
            }
        });
    }
}
