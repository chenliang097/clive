package com.rongtuoyouxuan.libuikit.layout;

import android.content.Context;
import android.graphics.Canvas;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.rongtuoyouxuan.libuikit.MarqueeTextView;

import java.lang.reflect.Field;

public class MarqueeTextListenerView extends MarqueeTextView {
    public static final byte MARQUEE_STOPPED = 0x0;
    public static final byte MARQUEE_STARTING = 0x1;
    public static final byte MARQUEE_RUNNING = 0x2;
    private byte mCurrentStatus = MARQUEE_STOPPED;
    private OnScrollingStateChangedListener onScrollingStateChangedListener;


    public MarqueeTextListenerView(Context context) {
        super(context);
    }

    public MarqueeTextListenerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MarqueeTextListenerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        byte newState = getState();
        if (mCurrentStatus != newState && onScrollingStateChangedListener != null) {
            onScrollingStateChangedListener.onStateChanged(mCurrentStatus = newState);
        }
    }

    public byte getState() {
        try {
            Field marquee = TextView.class.getDeclaredField("mMarquee");
            marquee.setAccessible(true);
            Object marqueeObject = marquee.get(this);
            Field status = marqueeObject.getClass().getDeclaredField("mStatus");
            status.setAccessible(true);
            return (byte) status.get(marqueeObject);
        } catch (Exception e) {
            e.printStackTrace();
            return MARQUEE_STOPPED;
        }
    }


    public void setOnScrollingStateChangedListener(OnScrollingStateChangedListener listener) {
        onScrollingStateChangedListener = listener;
    }

    public interface OnScrollingStateChangedListener {
        void onStateChanged(byte state);
    }
}
