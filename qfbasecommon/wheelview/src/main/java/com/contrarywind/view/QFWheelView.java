package com.contrarywind.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class QFWheelView extends WheelView {

    public QFWheelView(Context context) {
        this(context, null);
    }

    public QFWheelView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_DOWN:
                if (getParent() != null) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                return super.onTouchEvent(event);
        }
        return false;
    }
}