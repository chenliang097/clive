package com.rongtuoyouxuan.chatlive.crtuikit.layout.viewpager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.rongtuoyouxuan.chatlive.crtuikit.layout.rtlviewpager.RtlViewPager;

// https://code.google.com/p/android/issues/detail?id=18990
public class FixedViewPager extends RtlViewPager {

    private boolean isCanScroll = true;

    public FixedViewPager(Context context) {
        super(context);
    }

    public FixedViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (isCanScroll) {
            try {
                return super.onTouchEvent(ev);
            } catch (IllegalArgumentException ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (isCanScroll) {
            try {
                return super.onInterceptTouchEvent(ev);
            } catch (IllegalArgumentException ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }

    public void setScanScroll(boolean isCanScroll) {
        this.isCanScroll = isCanScroll;
    }
}
