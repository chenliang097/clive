package com.rongtuoyouxuan.qfcommon.player.exo.texture;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.util.AttributeSet;
import android.util.Log;

import com.rongtuoyouxuan.qfcommon.player.exo.PlayerScaleType;
import com.rongtuoyouxuan.qfcommon.player.exo.filter.AlphaFrameFilter;
import com.rongtuoyouxuan.qfcommon.player.exo.filter.GlFilter;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.video.VideoSize;

/**
 *
 * date:2022/8/8-17:01
 * des:
 */
public class EPlayerTextureView extends GLTextureView implements Player.Listener {
    private static final String TAG = "EPlayerTextureView";
    private final EPlayerRenderer renderer;
    private SimpleExoPlayer player;
    private float videoAspect = 1F;
    private PlayerScaleType playerScaleType;
    private int minusHeight = 0;

    public EPlayerTextureView(Context context) {
        this(context, null);
    }

    public EPlayerTextureView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.videoAspect = 1F;
        this.playerScaleType = PlayerScaleType.RESIZE_FIT_WIDTH;
        this.setEGLContextFactory(new EContextFactory());
        this.setEGLConfigChooserNoAlpha(new EConfigChooser());
        this.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        this.setOpaque(false);
        this.renderer = new EPlayerRenderer(this);
        this.setRenderer(this.renderer);
        this.setGlFilter(new AlphaFrameFilter());
    }

    public EPlayerTextureView setSimpleExoPlayer(SimpleExoPlayer player) {
        if (this.player != null) {
            this.player.release();
            this.player = null;
        }

        this.player = player;
        this.player.addListener(this);
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
                viewHeight = (int) ((measuredWidth) / this.videoAspect);
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
    public void onVideoSizeChanged(VideoSize videoSize) {
        this.videoAspect = (float) (videoSize.width / 2) / videoSize.height;
        Log.d(TAG, "onVideoSizeChanged.width=" + videoSize.width + ",height=" + videoSize.height + ",unappliedRotationDegrees=" + videoSize.unappliedRotationDegrees + ",pixelWidthHeightRatio=" + videoSize.pixelWidthHeightRatio + ",videoAspect=" + this.videoAspect);
        this.requestLayout();
    }

    public void onRenderedFirstFrame() {
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
        super.onSurfaceTextureSizeChanged(surface, width, height);
        Log.d(TAG, "onSurfaceSizeChanged.width=" + width + ",height=" + height);
    }
}
