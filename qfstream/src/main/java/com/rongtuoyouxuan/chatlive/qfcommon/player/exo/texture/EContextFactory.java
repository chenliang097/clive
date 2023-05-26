package com.rongtuoyouxuan.chatlive.qfcommon.player.exo.texture;

import com.rongtuoyouxuan.chatlive.qfcommon.player.exo.Logger;
import com.rongtuoyouxuan.chatlive.qfcommon.player.exo.Logger;

import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;

/**
 * 
 * date:2022/8/8-17:01
 * des:
 */
public class EContextFactory implements GLTextureView.EGLContextFactory {
    private static final String TAG = "EContextFactory";
    private final int EGL_CLIENT_VERSION = 2;
    private static final int EGL_CONTEXT_CLIENT_VERSION = 12440;

    public EContextFactory() {
    }

    public EGLContext createContext(EGL10 egl, EGLDisplay display, EGLConfig config) {
        int[] attrib_list = new int[]{12440, 2, 12344};
        Logger.d("EContextFactory", "EGL_CONTEXT_CLIENT_VERSION=12440 EGL_CLIENT_VERSION=2");
        return egl.eglCreateContext(display, config, EGL10.EGL_NO_CONTEXT, attrib_list);
    }

    public void destroyContext(EGL10 egl, EGLDisplay display, EGLContext context) {
        if (!egl.eglDestroyContext(display, context)) {
            Logger.d("EContextFactory", "display:" + display + " context: " + context);
            throw new RuntimeException("eglDestroyContex" + egl.eglGetError());
        }
    }
}
