package com.rongtuoyouxuan.chatlive.stream.view.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * @author ASnow
 * @date 2018/4/3
 * 文件   redEagles
 * 描述
 */
public class LockableScrollView extends ScrollView {

    //是否允许滑动
    private boolean mScrollable = false;

    public LockableScrollView(Context context) {
        this(context,null);
    }

    public LockableScrollView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LockableScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setScrollingEnabled(boolean enabled) {
        mScrollable = enabled;
    }

    public boolean isScrollable() {
        return mScrollable;
    }

    //如果我们不需要滑动事件的话，直接返回false,不需要ScrollView做任何处理
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // if we can scroll pass the event to the superclass
                if (mScrollable) return super.onTouchEvent(ev);
                // only continue to handle the touch event if scrolling enabled
                return mScrollable; // mScrollable is always false at this point
            default:
                return super.onTouchEvent(ev);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        // Don't do anything with intercepted touch events if
        // we are not scrollable
        if (!mScrollable) return false;
        else return super.onInterceptTouchEvent(ev);
    }
}