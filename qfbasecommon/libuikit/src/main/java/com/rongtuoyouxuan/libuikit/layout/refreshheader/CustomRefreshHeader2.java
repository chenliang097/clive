package com.rongtuoyouxuan.libuikit.layout.refreshheader;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.rongtuoyouxuan.libuikit.R;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;

/**
 * 
 * date:2022/8/30-11:41
 * des: 用户下拉刷新
 */
public class CustomRefreshHeader2 extends FrameLayout implements RefreshHeader {
    private final ImageView heartImageView;
    private AnimationDrawable pullDownAnim;
//    private AnimationDrawable refreshingAnim;

    private boolean hasSetPullDownAnim = false;

    public CustomRefreshHeader2(Context context) {
        this(context, null, 0);
    }

    public CustomRefreshHeader2(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomRefreshHeader2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = View.inflate(context, R.layout.widget_custom_refresh_header, this);
        heartImageView = (ImageView) view.findViewById(R.id.iv_refresh_header);
    }

    @NonNull
    @Override
    public View getView() {
        return this;
    }

    @NonNull
    @Override
    public SpinnerStyle getSpinnerStyle() {
        return SpinnerStyle.Translate;
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onStartAnimator(@NonNull RefreshLayout layout, int height, int extendHeight) {

    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState oldState, RefreshState newState) {
//        Log.d("CustomRefreshHeader", ">>>CustomRefreshHeader:onStateChanged::" + newState);
        switch (newState) {
            case PullDownToRefresh:
                heartImageView.setImageResource(R.drawable.commonui_refreshing_image_frame_1);
                break;
//            case Refreshing:
//                heartImageView.setImageResource(R.drawable.anim_pull_refreshing);
//                refreshingAnim = (AnimationDrawable) heartImageView.getDrawable();
//                refreshingAnim.start();
//                break;
            case PullDownCanceled:
                onFinish(refreshLayout, true);
            case ReleaseToRefresh:
                break;
        }
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onMoving(boolean isDragging, float percent, int offset, int height, int maxDragHeight) {
//        Log.d("CustomRefreshHeader", ">>>CustomRefreshHeader:onMoving::" + percent);
        if (percent < 1) {
            heartImageView.setScaleX(percent);
            heartImageView.setScaleY(percent);

            if (hasSetPullDownAnim) {
                hasSetPullDownAnim = false;
            }
        }

        if (percent >= 1.0) {
            if (!hasSetPullDownAnim) {
                heartImageView.setImageResource(R.drawable.anim_pull_refreshing);
                pullDownAnim = (AnimationDrawable) heartImageView.getDrawable();
                pullDownAnim.start();

                hasSetPullDownAnim = true;
            }
        }
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onReleased(@NonNull RefreshLayout refreshLayout, int height, int maxDragHeight) {

    }

    @SuppressLint("RestrictedApi")
    @Override
    public int onFinish(@NonNull RefreshLayout layout, boolean success) {
//        Log.d("CustomRefreshHeader", ">>>CustomRefreshHeader:onFinish::");
        if (pullDownAnim != null && pullDownAnim.isRunning()) {
            pullDownAnim.stop();
        }
//        if (refreshingAnim != null && refreshingAnim.isRunning()) {
//            refreshingAnim.stop();
//        }
        hasSetPullDownAnim = false;
        return 0;
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void setPrimaryColors(int... colors) {

    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onInitialized(@NonNull RefreshKernel kernel, int height, int extendHeight) {

    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onHorizontalDrag(float percentX, int offsetX, int offsetMax) {

    }

    @Override
    public boolean isSupportHorizontalDrag() {
        return false;
    }
}
