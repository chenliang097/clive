package com.rongtuoyouxuan.chatlive.qfcommon.player.exo.texture;

import android.os.Build;

import com.rongtuoyouxuan.chatlive.qfcommon.player.exo.Logger;
import com.rongtuoyouxuan.chatlive.qfcommon.player.exo.Logger;

import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLDisplay;

/**
 * 
 * date:2022/8/8-17:00
 * des:
 */
public class EConfigChooser implements GLTextureView.EGLConfigChooser {
    private final int[] configSpec;
    private final int redSize;
    private final int greenSize;
    private final int blueSize;
    private final int alphaSize;
    private final int depthSize;
    private final int stencilSize;
    private static final int EGL_CONTEXT_CLIENT_VERSION = 2;
    private static final boolean USE_RGB_888;
    private static final int EGL_OPENGL_ES2_BIT = 4;

    public EConfigChooser() {
        this(USE_RGB_888 ? 8 : 5, USE_RGB_888 ? 8 : 6, USE_RGB_888 ? 8 : 5, 0, 0, 0, 2);
    }

    public EConfigChooser(int redSize, int greenSize, int blueSize, int alphaSize, int depthSize, int stencilSize, int version) {
        this.configSpec = this.filterConfigSpec(new int[]{12324, redSize, 12323, greenSize, 12322, blueSize, 12321, alphaSize, 12325, depthSize, 12326, stencilSize, 12344}, version);
        this.redSize = redSize;
        this.greenSize = greenSize;
        this.blueSize = blueSize;
        this.alphaSize = alphaSize;
        this.depthSize = depthSize;
        this.stencilSize = stencilSize;
        Logger.d("EConfigChooser", "new.USE_RGB_888=" + USE_RGB_888 + ",redSize=" + redSize + ",greenSize=" + greenSize + ",blueSize=" + blueSize + ",alphaSize=" + alphaSize + ",depthSize=" + depthSize + ",stencilSize=" + stencilSize);
    }

    private int[] filterConfigSpec(int[] configSpec, int version) {
        if (version != 2) {
            return configSpec;
        } else {
            int len = configSpec.length;
            int[] newConfigSpec = new int[len + 2];
            System.arraycopy(configSpec, 0, newConfigSpec, 0, len - 1);
            newConfigSpec[len - 1] = 12352;
            newConfigSpec[len] = 4;
            newConfigSpec[len + 1] = 12344;
            return newConfigSpec;
        }
    }

    public EGLConfig chooseConfig(EGL10 egl, EGLDisplay display) {
        Logger.d("EConfigChooser", "USE_RGB_888=" + USE_RGB_888 + ",chooseConfig.start");
        int[] num_config = new int[1];
        if (!egl.eglChooseConfig(display, this.configSpec, (EGLConfig[])null, 0, num_config)) {
            throw new IllegalArgumentException("eglChooseConfig failed");
        } else {
            int config_size = num_config[0];
            if (config_size <= 0) {
                throw new IllegalArgumentException("No configs match configSpec");
            } else {
                EGLConfig[] configs = new EGLConfig[config_size];
                if (!egl.eglChooseConfig(display, this.configSpec, configs, config_size, num_config)) {
                    throw new IllegalArgumentException("eglChooseConfig#2 failed");
                } else {
                    EGLConfig config = this.chooseConfig(egl, display, configs);
                    if (config == null) {
                        throw new IllegalArgumentException("No config chosen");
                    } else {
                        return config;
                    }
                }
            }
        }
    }

    private EGLConfig chooseConfig(EGL10 egl, EGLDisplay display, EGLConfig[] configs) {
        EGLConfig[] var4 = configs;
        int var5 = configs.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            EGLConfig config = var4[var6];
            int d = this.findConfigAttrib(egl, display, config, 12325, 0);
            int s = this.findConfigAttrib(egl, display, config, 12326, 0);
            Logger.d("EConfigChooser", "USE_RGB_888=" + USE_RGB_888 + ",chooseConfig.config=" + config + ",d=" + d + ",s=" + s);
            if (d >= this.depthSize && s >= this.stencilSize) {
                int r = this.findConfigAttrib(egl, display, config, 12324, 0);
                int g = this.findConfigAttrib(egl, display, config, 12323, 0);
                int b = this.findConfigAttrib(egl, display, config, 12322, 0);
                int a = this.findConfigAttrib(egl, display, config, 12321, 0);
                Logger.d("EConfigChooser", "chooseConfig.r=" + r + ",g=" + g + ",b=" + b + ",a=" + a);
                if (r == this.redSize && g == this.greenSize && b == this.blueSize && a == this.alphaSize) {
                    return config;
                }
            }
        }

        return null;
    }

    private int findConfigAttrib(EGL10 egl, EGLDisplay display, EGLConfig config, int attribute, int defaultValue) {
        int[] value = new int[1];
        return egl.eglGetConfigAttrib(display, config, attribute, value) ? value[0] : defaultValue;
    }

    static {
        USE_RGB_888 = Build.VERSION.SDK_INT >= 17;
    }
}
