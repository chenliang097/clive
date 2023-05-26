package com.rongtuoyouxuan.chatlive.qfcommon.player.exo.texture;

import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.opengl.GLES20;
import android.opengl.Matrix;
import android.os.Looper;
import android.util.Log;
import android.view.Surface;

import com.blankj.utilcode.util.ThreadUtils;
import com.rongtuoyouxuan.chatlive.qfcommon.player.exo.EglUtil;
import com.rongtuoyouxuan.chatlive.qfcommon.player.exo.filter.GlFilter;
import com.rongtuoyouxuan.chatlive.qfcommon.player.exo.filter.GlPreviewFilter;
import com.rongtuoyouxuan.chatlive.qfcommon.player.exo.EFramebufferObject;
import com.rongtuoyouxuan.chatlive.qfcommon.player.exo.EglUtil;
import com.rongtuoyouxuan.chatlive.qfcommon.player.exo.filter.GlFilter;
import com.rongtuoyouxuan.chatlive.qfcommon.player.exo.filter.GlPreviewFilter;
import javax.microedition.khronos.egl.EGLConfig;

/**
 *
 * date:2022/8/8-17:01
 * des:
 */
class EPlayerRenderer2 extends EFrameBufferObjectRenderer implements SurfaceTexture.OnFrameAvailableListener {
    private static final String TAG = "EPlayerRenderer";
    private ESurfaceTexture previewTexture;
    private boolean updateSurface = false;
    private int texName;
    private float[] MVPMatrix = new float[16];
    private float[] ProjMatrix = new float[16];
    private float[] MMatrix = new float[16];
    private float[] VMatrix = new float[16];
    private float[] STMatrix = new float[16];
    private EFramebufferObject filterFramebufferObject;
    private GlPreviewFilter previewFilter;
    private GlFilter glFilter;
    private boolean isNewFilter;
    private final EPlayerTextureView2 glPreview;
    private float aspectRatio = 1.0F;
    private MediaPlayer simpleExoPlayer;

    EPlayerRenderer2(EPlayerTextureView2 glPreview) {
        Matrix.setIdentityM(this.STMatrix, 0);
        this.glPreview = glPreview;
    }

    void setGlFilter(final GlFilter filter) {
        this.glPreview.queueEvent(new Runnable() {
            public void run() {
                if (EPlayerRenderer2.this.glFilter != null) {
                    EPlayerRenderer2.this.glFilter.release();
                    EPlayerRenderer2.this.glFilter = null;
                }

                EPlayerRenderer2.this.glFilter = filter;
                EPlayerRenderer2.this.isNewFilter = true;
                EPlayerRenderer2.this.glPreview.requestRender();
            }
        });
    }

    public void onSurfaceCreated(EGLConfig config) {
        GLES20.glClearColor(0.0F, 0.0F, 0.0F, 1.0F);
        int[] args = new int[1];
        GLES20.glGenTextures(args.length, args, 0);
        this.texName = args[0];
        this.previewTexture = new ESurfaceTexture(this.texName);
        this.previewTexture.setOnFrameAvailableListener(this);
        GLES20.glBindTexture(this.previewTexture.getTextureTarget(), this.texName);
        EglUtil.setupSampler(this.previewTexture.getTextureTarget(), 9729, 9728);
        GLES20.glBindTexture(3553, 0);
        this.filterFramebufferObject = new EFramebufferObject();
        this.previewFilter = new GlPreviewFilter(this.previewTexture.getTextureTarget());
        this.previewFilter.setup();
        Log.d(TAG, "player.onSurfaceCreated.looper=" + Looper.myLooper());
        final Surface surface = new Surface(this.previewTexture.getSurfaceTexture());
        ThreadUtils.getMainHandler().post(new Runnable() {
            @Override
            public void run() {
                EPlayerRenderer2.this.simpleExoPlayer.setSurface(surface);
            }
        });
//        (new Handler(this.simpleExoPlayer.getApplicationLooper())).post(new Runnable() {
//            public void run() {
//                EPlayerRenderer2.this.simpleExoPlayer.setVideoSurface(surface);
//            }
//        });
        Matrix.setLookAtM(this.VMatrix, 0, 0.0F, 0.0F, 5.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F);
        synchronized (this) {
            this.updateSurface = false;
        }

        if (this.glFilter != null) {
            this.isNewFilter = true;
        }

        GLES20.glGetIntegerv(3379, args, 0);
    }

    public void onSurfaceChanged(int width, int height) {
        Log.d(TAG, "onSurfaceChanged width = " + width + "  height = " + height);
        this.filterFramebufferObject.setup(width, height);
        this.previewFilter.setFrameSize(width, height);
        if (this.glFilter != null) {
            this.glFilter.setFrameSize(width, height);
        }

        this.aspectRatio = (float) width / (float) height;
        Matrix.frustumM(this.ProjMatrix, 0, -this.aspectRatio, this.aspectRatio, -1.0F, 1.0F, 5.0F, 7.0F);
        Matrix.setIdentityM(this.MMatrix, 0);
    }

    public void onDrawFrame(EFramebufferObject fbo) {
        synchronized (this) {
            if (this.updateSurface) {
                this.previewTexture.updateTexImage();
                this.previewTexture.getTransformMatrix(this.STMatrix);
                this.updateSurface = false;
            }
        }

        if (this.isNewFilter) {
            if (this.glFilter != null) {
                this.glFilter.setup();
                this.glFilter.setFrameSize(fbo.getWidth(), fbo.getHeight());
            }

            this.isNewFilter = false;
        }

        if (this.glFilter != null) {
            this.filterFramebufferObject.enable();
            GLES20.glViewport(0, 0, this.filterFramebufferObject.getWidth(), this.filterFramebufferObject.getHeight());
        }

        GLES20.glClear(16384);
        Matrix.multiplyMM(this.MVPMatrix, 0, this.VMatrix, 0, this.MMatrix, 0);
        Matrix.multiplyMM(this.MVPMatrix, 0, this.ProjMatrix, 0, this.MVPMatrix, 0);
        this.previewFilter.draw(this.texName, this.MVPMatrix, this.STMatrix, this.aspectRatio);
        if (this.glFilter != null) {
            fbo.enable();
            GLES20.glClear(16384);
            this.glFilter.draw(this.filterFramebufferObject.getTexName(), fbo);
        }

    }

    public synchronized void onFrameAvailable(SurfaceTexture previewTexture) {
        this.updateSurface = true;
        this.glPreview.requestRender();
    }

    void setSimpleExoPlayer(MediaPlayer simpleExoPlayer) {
        this.simpleExoPlayer = simpleExoPlayer;
    }

    void release() {
        if (this.glFilter != null) {
            this.glFilter.release();
        }

        if (this.previewTexture != null) {
            this.previewTexture.release();
        }

    }
}

