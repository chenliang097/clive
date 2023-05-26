package com.rongtuoyouxuan.chatlive.qfcommon.player.exo;

import android.opengl.GLES20;
import java.nio.Buffer;

/**
 * 
 * date:2022/8/8-17:04
 * des:
 */
public class EFramebufferObject {
    private int width;
    private int height;
    private int framebufferName;
    private int renderbufferName;
    private int texName;

    public EFramebufferObject() {
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public int getTexName() {
        return this.texName;
    }

    public void setup(int width, int height) {
        int[] args = new int[1];
        GLES20.glGetIntegerv(3379, args, 0);
        if (width <= args[0] && height <= args[0]) {
            GLES20.glGetIntegerv(34024, args, 0);
            if (width <= args[0] && height <= args[0]) {
                GLES20.glGetIntegerv(36006, args, 0);
                int saveFramebuffer = args[0];
                GLES20.glGetIntegerv(36007, args, 0);
                int saveRenderbuffer = args[0];
                GLES20.glGetIntegerv(32873, args, 0);
                int saveTexName = args[0];
                this.release();

                try {
                    this.width = width;
                    this.height = height;
                    GLES20.glGenFramebuffers(args.length, args, 0);
                    this.framebufferName = args[0];
                    GLES20.glBindFramebuffer(36160, this.framebufferName);
                    GLES20.glGenRenderbuffers(args.length, args, 0);
                    this.renderbufferName = args[0];
                    GLES20.glBindRenderbuffer(36161, this.renderbufferName);
                    GLES20.glRenderbufferStorage(36161, 33189, width, height);
                    GLES20.glFramebufferRenderbuffer(36160, 36096, 36161, this.renderbufferName);
                    GLES20.glGenTextures(args.length, args, 0);
                    this.texName = args[0];
                    GLES20.glBindTexture(3553, this.texName);
                    EglUtil.setupSampler(3553, 9729, 9728);
                    GLES20.glTexImage2D(3553, 0, 6408, width, height, 0, 6408, 5121, (Buffer)null);
                    GLES20.glFramebufferTexture2D(36160, 36064, 3553, this.texName, 0);
                    int status = GLES20.glCheckFramebufferStatus(36160);
                    if (status != 36053) {
                        throw new RuntimeException("Failed to initialize framebuffer object " + status);
                    }
                } catch (RuntimeException var8) {
                    this.release();
                    throw var8;
                }

                GLES20.glBindFramebuffer(36160, saveFramebuffer);
                GLES20.glBindRenderbuffer(36161, saveRenderbuffer);
                GLES20.glBindTexture(3553, saveTexName);
            } else {
                throw new IllegalArgumentException("GL_MAX_RENDERBUFFER_SIZE " + args[0]);
            }
        } else {
            throw new IllegalArgumentException("GL_MAX_TEXTURE_SIZE " + args[0]);
        }
    }

    public void release() {
        int[] args = new int[]{this.texName};
        GLES20.glDeleteTextures(args.length, args, 0);
        this.texName = 0;
        args[0] = this.renderbufferName;
        GLES20.glDeleteRenderbuffers(args.length, args, 0);
        this.renderbufferName = 0;
        args[0] = this.framebufferName;
        GLES20.glDeleteFramebuffers(args.length, args, 0);
        this.framebufferName = 0;
    }

    public void enable() {
        GLES20.glBindFramebuffer(36160, this.framebufferName);
    }
}