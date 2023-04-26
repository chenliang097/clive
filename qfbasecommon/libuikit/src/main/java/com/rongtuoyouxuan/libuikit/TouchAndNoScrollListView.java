package com.rongtuoyouxuan.libuikit;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * touch 不可滚动的ListView
 */
public class TouchAndNoScrollListView extends RecyclerView {
    boolean isDown = false;

    public TouchAndNoScrollListView(Context context) {
        super(context);
    }

    public TouchAndNoScrollListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TouchAndNoScrollListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                isDown = true;
                break;
            }
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP: {
                isDown = false;
                break;
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void smoothScrollToPosition(int position) {
        if (!isDown) {
            super.smoothScrollToPosition(position);
        }
    }
}
