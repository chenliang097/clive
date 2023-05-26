package com.rongtuoyouxuan.chatlive.qfcommon.player.exo.texture;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.util.Log;

import com.rongtuoyouxuan.chatlive.qfcommon.player.exo.filter.AlphaFrameFilter;
import com.rongtuoyouxuan.chatlive.qfcommon.player.exo.filter.GlFilter;
import com.rongtuoyouxuan.chatlive.qfcommon.player.exo.PlayerScaleType;
import com.rongtuoyouxuan.chatlive.qfcommon.player.exo.filter.AlphaFrameFilter;
import com.rongtuoyouxuan.chatlive.qfcommon.player.exo.filter.GlFilter;

/**
 *
 * date:2022/8/8-17:01
 * des:
 */
public class EPlayerTextureView2 extends GLTextureView implements MediaPlayer.OnVideoSizeChangedListener {
    private static final String TAG = "EPlayerTextureView2";
    private final EPlayerRenderer2 renderer;
    private MediaPlayer player;
    private float videoAspect = 1f;
    private PlayerScaleType playerScaleType;
    private int minusHeight = 0;

    public EPlayerTextureView2(Context context) {
        this(context, null);
    }

    public EPlayerTextureView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.videoAspect = 1F;
        this.playerScaleType = PlayerScaleType.RESIZE_FIT_WIDTH;
        this.setEGLContextFactory(new EContextFactory());
        this.setEGLConfigChooserNoAlpha(new EConfigChooser());
        this.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        this.setOpaque(false);
        this.renderer = new EPlayerRenderer2(this);
        this.setRenderer(this.renderer);
        this.setGlFilter(new AlphaFrameFilter());
    }

    public EPlayerTextureView2 setSimpleExoPlayer(MediaPlayer player) {
        if (this.player != null) {
            this.player.release();
            this.player = null;
        }

        this.player = player;
        this.player.setOnVideoSizeChangedListener(this);
        this.renderer.setSimpleExoPlayer(player);
        this.setFocusableInTouchMode(false);
        this.setFocusable(false);
        this.setGlFilter(new AlphaFrameFilter());
        return this;
    }

    private void setGlFilter(GlFilter glFilter) {
        this.renderer.setGlFilter(glFilter);
    }

    public void setPlayerScaleType(PlayerScaleType playerScaleType) {
        this.playerScaleType = playerScaleType;
        this.requestLayout();
    }

    //座驾计算高度时减去部分
    public void setMinusHeight(int minusHeight) {
        this.minusHeight = minusHeight;
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measuredWidth = this.getMeasuredWidth();
        int measuredHeight = this.getMeasuredHeight();
        int viewWidth = measuredWidth;
        int viewHeight = measuredHeight;
        switch (this.playerScaleType) {
            case RESIZE_FIT_WIDTH:
                viewHeight = (int) (measuredWidth / this.videoAspect);
                break;
            case RESIZE_FIT_HEIGHT:
                viewWidth = (int) (measuredHeight * this.videoAspect);
        }

        Log.d(TAG, "onMeasure viewWidth=" + viewWidth + ",viewHeight=" + viewHeight + ",measuredWidth=" + measuredWidth + ",measuredHeight=" + measuredHeight + ",videoAspect=" + this.videoAspect);
        this.setMeasuredDimension(viewWidth, viewHeight);
    }

    public void onPause() {
        super.onPause();
        this.renderer.release();
    }

    @Override
    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
        this.videoAspect = (float) (width / 2) / height;
        Log.d(TAG, "onVideoSizeChanged.width=" + width + ",height=" + height + ",unappliedRotationDegrees=" + ",videoAspect=" + this.videoAspect);
        this.requestLayout();
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
        super.onSurfaceTextureSizeChanged(surface, width, height);
        Log.d(TAG, "onSurfaceSizeChanged.width=" + width + ",height=" + height);
    }

}
