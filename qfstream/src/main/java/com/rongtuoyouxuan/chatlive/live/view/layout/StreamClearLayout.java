package com.rongtuoyouxuan.chatlive.live.view.layout;

import android.animation.Animator;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewConfigurationCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;
import android.widget.Scroller;

import com.rongtuoyouxuan.chatlive.crtdatabus.DataBus;
import com.rongtuoyouxuan.chatlive.crtlog.upload.ULog;
import com.rongtuoyouxuan.chatlive.crtutil.util.DisplayUtil;


/**
 * Created by jinqinglin on 2018/4/13.
 */

public class StreamClearLayout extends LinearLayout {

    HideListener mHideListener;
    /**
     * 用于完成滚动操作的实例
     */
    private Scroller mScroller;
    /**
     * 判定为拖动的最小移动像素数
     */
    private int mTouchSlop;
    /**
     * 手机按下时的屏幕坐标
     */
    private float mXDown;
    /**
     * 手机当时所处的屏幕坐标
     */
    private float mXMove;
    private float mXLastMove;
    private View mScrollView;
    private float moveDelta = 0;
    private boolean isAnimate = false;
    private boolean isHide = false;

    public StreamClearLayout(@NonNull Context context) {
        super(context);
        init(context);
    }

    public StreamClearLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public StreamClearLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void setScrollView(View mScrollView) {
        this.mScrollView = mScrollView;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if(ev.getPointerCount() > 1)
            return super.onInterceptTouchEvent(ev);
        switch (ev.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                mXDown = ev.getRawX();
                mXLastMove = mXDown;
                break;
            case MotionEvent.ACTION_MOVE:
                mXMove = ev.getRawX();
                float diff = Math.abs(mXMove - mXDown);
                mXLastMove = mXMove;
                if (diff > mTouchSlop) {
                    return true;
                }
                break;
        }
        ULog.d("TouchEvent", ">>>StreamClearLayout onInterceptTouchEvent>>>" + super.onInterceptTouchEvent(ev));

        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                mXMove = event.getRawX();
                int scrolledX = (int) (mXLastMove - mXMove);
                //添加支持阿拉伯语
                if(DataBus.instance().getLocaleManager().getLanguage().getValue().equals("ar")){
                    if(Math.abs(scrolledX) > DisplayUtil.dipToPixels(getContext(),10)){
                        if (scrolledX > 0 && !isHide) {
                            animatorLeft(false);
                        } else if (scrolledX < 0 && isHide) {
                            animatorLeft(true);
                        }
                    }
                }else{
                    if(Math.abs(scrolledX) > DisplayUtil.dipToPixels(getContext(),10)){
                        if (scrolledX < 0 && !isHide) {
                            animatorLeft(true);
                        } else if (scrolledX > 0 && isHide) {
                            animatorLeft(false);
                        }
                    }
                }
                mXLastMove = mXMove;
                break;
        }
        ULog.d("TouchEvent", ">>>StreamClearLayout onTouchEvent>>>" + super.onTouchEvent(event));

        return super.onTouchEvent(event);
    }

    void init(Context context){
        ViewConfiguration configuration = ViewConfiguration.get(context);
        mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(configuration);
        initViewModel(context);
        initData(context);
    }

    private void initData(Context context) {
    }

    private void initViewModel(Context context) {
    }

    private void animatorLeft(boolean left){
        if(isAnimate)
            return;
        isAnimate = true;
        mScrollView.animate().translationXBy(left ? getWidth() : -getWidth()).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                isHide = !isHide;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isAnimate = false;
                if (mHideListener != null) {
                    mHideListener.onHide(isHide);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                isAnimate = false;
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        }).start();
    }

    public void setHideListener(@Nullable HideListener hideListener) {
        mHideListener = hideListener;
    }
    public interface HideListener {
        void onHide(boolean ishide);
    }
}
