package com.rongtuoyouxuan.qfcommon.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.viewpager.widget.ViewPager;

public class CommonViewPager extends ViewPager {

    public boolean isCanScroll=true;

    public CommonViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CommonViewPager(Context context) {
        super(context);
    }

    public void setCanScroll(boolean isCanScroll){
        this.isCanScroll=isCanScroll;
    }
    //第一种
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if(!isCanScroll){
            return false;
        }
        return super.onTouchEvent(ev);
    }
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if(!isCanScroll){
            return false;
        }
        return super.onInterceptTouchEvent(event);
    }
}