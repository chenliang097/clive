package com.rongtuoyouxuan.chatlive.qfcommon.player.exo.texture;

import android.graphics.SurfaceTexture;

/**
 * 
 * date:2022/8/8-17:02
 * des:
 */
class ESurfaceTexture implements SurfaceTexture.OnFrameAvailableListener {
    private SurfaceTexture surfaceTexture;
    private SurfaceTexture.OnFrameAvailableListener onFrameAvailableListener;

    ESurfaceTexture(int texName) {
        this.surfaceTexture = new SurfaceTexture(texName);
        this.surfaceTexture.setOnFrameAvailableListener(this);
    }

    void setOnFrameAvailableListener(SurfaceTexture.OnFrameAvailableListener l) {
        this.onFrameAvailableListener = l;
    }

    int getTextureTarget() {
        return 36197;
    }

    void updateTexImage() {
        this.surfaceTexture.updateTexImage();
    }

    void getTransformMatrix(float[] mtx) {
        this.surfaceTexture.getTransformMatrix(mtx);
    }

    SurfaceTexture getSurfaceTexture() {
        return this.surfaceTexture;
    }

    public void onFrameAvailable(SurfaceTexture surfaceTexture) {
        if (this.onFrameAvailableListener != null) {
            this.onFrameAvailableListener.onFrameAvailable(this.surfaceTexture);
        }

    }

    public void release() {
        this.surfaceTexture.release();
    }
}
