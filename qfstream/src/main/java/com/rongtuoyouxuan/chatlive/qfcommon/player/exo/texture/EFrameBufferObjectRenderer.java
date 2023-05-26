package com.rongtuoyouxuan.chatlive.qfcommon.player.exo.texture;

import android.opengl.GLES20;

import com.rongtuoyouxuan.chatlive.qfcommon.player.exo.filter.GlFilter;
import com.rongtuoyouxuan.chatlive.qfcommon.player.exo.EFramebufferObject;
import com.rongtuoyouxuan.chatlive.qfcommon.player.exo.filter.GlFilter;

import java.util.LinkedList;
import java.util.Queue;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * 
 * date:2022/8/8-17:01
 * des:
 */
abstract class EFrameBufferObjectRenderer implements GLTextureView.Renderer {
    private EFramebufferObject framebufferObject;
    private GlFilter normalShader;
    private final Queue<Runnable> runOnDraw = new LinkedList();

    EFrameBufferObjectRenderer() {
    }

    public final void onSurfaceCreated(GL10 gl, EGLConfig config) {
        this.framebufferObject = new EFramebufferObject();
        this.normalShader = new GlFilter();
        this.normalShader.setup();
        this.onSurfaceCreated(config);
    }

    public final void onSurfaceChanged(GL10 gl, int width, int height) {
        this.framebufferObject.setup(width, height);
        this.normalShader.setFrameSize(width, height);
        this.onSurfaceChanged(width, height);
    }

    public final void onDrawFrame(GL10 gl) {
        synchronized(this.runOnDraw) {
            while(!this.runOnDraw.isEmpty()) {
                ((Runnable)this.runOnDraw.poll()).run();
            }
        }

        this.framebufferObject.enable();
        GLES20.glViewport(0, 0, this.framebufferObject.getWidth(), this.framebufferObject.getHeight());
        this.onDrawFrame(this.framebufferObject);
        GLES20.glBindFramebuffer(36160, 0);
        GLES20.glViewport(0, 0, this.framebufferObject.getWidth(), this.framebufferObject.getHeight());
        GLES20.glClear(16640);
        this.normalShader.draw(this.framebufferObject.getTexName(), (EFramebufferObject)null);
    }

    protected void finalize() throws Throwable {
    }

    public abstract void onSurfaceCreated(EGLConfig var1);

    public abstract void onSurfaceChanged(int var1, int var2);

    public abstract void onDrawFrame(EFramebufferObject var1);
}
