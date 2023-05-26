package com.rongtuoyouxuan.chatlive.crtuikit.widget.banner.util;

import android.content.Context;
import android.view.animation.AccelerateDecelerateInterpolator;

import androidx.viewpager.widget.ViewPager;

import java.lang.reflect.Field;


public class ViewPagerSrollerHelper {
    public static void setScroller(Context context, ViewPager viewPager,int duration) {
        try {
            Field mField = ViewPager.class.getDeclaredField("mScroller");
            mField.setAccessible(true);
            FixedSpeedScroller mScroller = new FixedSpeedScroller(context, new AccelerateDecelerateInterpolator());
            mScroller.setmDuration(duration);    //在这里设置时间单位毫秒
            mField.set(viewPager, mScroller); //viewPager和FixedSpeedScrolle
        } catch (Exception e) {

        }
    }
}
