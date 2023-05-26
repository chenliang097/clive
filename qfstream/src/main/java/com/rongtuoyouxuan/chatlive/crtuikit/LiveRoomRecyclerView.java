package com.rongtuoyouxuan.chatlive.crtuikit;

import android.content.Context;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 类描述:RecyclerView,将空白区域MotionEvent事件传递出去
 * 创建人:malin.myemail@163.com
 * 创建时间:2018.02-05
 * 备注:{@link }
 * 修改人:
 * 修改时间:
 * 修改备注:
 * 版本:
 */
public class LiveRoomRecyclerView extends RecyclerView {


    private CameraTouchListener mListener;
    private RoomRVTouchListener mRoomRVTouchListener;

    public LiveRoomRecyclerView(Context context) {
        super(context);
    }

    public LiveRoomRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    public LiveRoomRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setListener(CameraTouchListener listener) {
        mListener = listener;
    }

    public void setRoomRVTouchListener(RoomRVTouchListener roomRVTouchListener) {
        this.mRoomRVTouchListener = roomRVTouchListener;
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mRoomRVTouchListener != null) {
            mRoomRVTouchListener.touchTime(System.currentTimeMillis());
        }
        if (mListener != null) {
            View childView = findChildViewUnder(event.getX(), event.getY());
            if (childView == null) {
                mListener.onTouchLL(event);
                return false;
            }
        }
        return super.onTouchEvent(event);
    }

    public interface RoomRVTouchListener {
        void touchTime(long lastTime);
    }


    public interface CameraTouchListener {
        void onTouchLL(MotionEvent motionEvent);
    }
}
