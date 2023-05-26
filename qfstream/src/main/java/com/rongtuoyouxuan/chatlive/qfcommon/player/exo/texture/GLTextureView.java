package com.rongtuoyouxuan.chatlive.qfcommon.player.exo.texture;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.opengl.GLDebugHelper;
import android.os.Build;
import android.util.AttributeSet;
import android.view.TextureView;
import android.view.View;

import com.rongtuoyouxuan.chatlive.qfcommon.player.exo.ExceptionListener;
import com.rongtuoyouxuan.chatlive.qfcommon.player.exo.Logger;
import com.rongtuoyouxuan.chatlive.qfcommon.player.exo.ExceptionListener;
import com.rongtuoyouxuan.chatlive.qfcommon.player.exo.Logger;

import java.io.Writer;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;
import javax.microedition.khronos.opengles.GL;
import javax.microedition.khronos.opengles.GL10;

/**
 * 
 * date:2022/8/8-17:02
 * des:
 */
public class GLTextureView extends TextureView implements TextureView.SurfaceTextureListener, View.OnLayoutChangeListener {
    private static final String TAG = "GLTextureView";
    public static boolean LOG_ATTACH_DETACH = true;
    public static boolean LOG_THREADS = true;
    public static boolean LOG_PAUSE_RESUME = true;
    public static boolean LOG_SURFACE = true;
    public static boolean LOG_RENDERER = true;
    public static boolean LOG_RENDERER_DRAW_FRAME = false;
    public static boolean LOG_EGL = true;
    public static ExceptionListener exceptionListener;
    public static final int RENDERMODE_WHEN_DIRTY = 0;
    public static final int RENDERMODE_CONTINUOUSLY = 1;
    public static final int DEBUG_CHECK_GL_ERROR = 1;
    public static final int DEBUG_LOG_GL_CALLS = 2;
    private static final GLThreadManager glThreadManager = new GLThreadManager();
    private final WeakReference<GLTextureView> mThisWeakRef = new WeakReference(this);
    private GLThread glThread;
    private Renderer renderer;
    private boolean detached;
    private EGLConfigChooser eglConfigChooser;
    private EGLConfigChooser eglConfigChooserNoAlpha;
    private EGLContextFactory eglContextFactory;
    private EGLWindowSurfaceFactory eglWindowSurfaceFactory;
    private GLWrapper glWrapper;
    private int debugFlags;
    private int eglContextClientVersion;
    private boolean preserveEGLContextOnPause;
    private List<SurfaceTextureListener> surfaceTextureListeners = new ArrayList();
    private boolean useNoAlpha = false;

    public GLTextureView(Context context) {
        super(context);
        this.init();
    }

    public GLTextureView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init();
    }

    protected void finalize() throws Throwable {
        try {
            if (this.glThread != null) {
                this.glThread.requestExitAndWait();
            }
        } finally {
            super.finalize();
        }

    }

    private void init() {
        this.setSurfaceTextureListener(this);
    }

    public void setGLWrapper(GLWrapper glWrapper) {
        this.glWrapper = glWrapper;
    }

    public void setDebugFlags(int debugFlags) {
        this.debugFlags = debugFlags;
    }

    public int getDebugFlags() {
        return this.debugFlags;
    }

    public void setPreserveEGLContextOnPause(boolean preserveOnPause) {
        this.preserveEGLContextOnPause = preserveOnPause;
    }

    public boolean getPreserveEGLContextOnPause() {
        return this.preserveEGLContextOnPause;
    }

    public void setRenderer(Renderer renderer) {
        this.checkRenderThreadState();
        if (this.eglConfigChooser == null) {
            this.eglConfigChooser = new SimpleEGLConfigChooser(true);
        }

        if (this.eglContextFactory == null) {
            this.eglContextFactory = new DefaultContextFactory();
        }

        if (this.eglWindowSurfaceFactory == null) {
            this.eglWindowSurfaceFactory = new DefaultWindowSurfaceFactory();
        }

        this.renderer = renderer;
        this.glThread = new GLThread(this.mThisWeakRef);
        this.glThread.start();
    }

    public void setEGLContextFactory(EGLContextFactory factory) {
        this.checkRenderThreadState();
        this.eglContextFactory = factory;
    }

    public void setEGLWindowSurfaceFactory(EGLWindowSurfaceFactory factory) {
        this.checkRenderThreadState();
        this.eglWindowSurfaceFactory = factory;
    }

    public void setEGLConfigChooser(EGLConfigChooser configChooser) {
        this.checkRenderThreadState();
        this.eglConfigChooser = configChooser;
    }

    public void setEGLConfigChooserNoAlpha(EGLConfigChooser configChooser) {
        this.checkRenderThreadState();
        this.eglConfigChooserNoAlpha = configChooser;
    }

    public void setEGLConfigChooser(boolean needDepth) {
        this.setEGLConfigChooser(new SimpleEGLConfigChooser(needDepth));
    }

    public void setEGLConfigChooser(int redSize, int greenSize, int blueSize, int alphaSize, int depthSize, int stencilSize) {
        this.setEGLConfigChooser(new ComponentSizeChooser(redSize, greenSize, blueSize, alphaSize, depthSize, stencilSize));
    }

    public void setEGLContextClientVersion(int version) {
        this.checkRenderThreadState();
        this.eglContextClientVersion = version;
    }

    public void setRenderMode(int renderMode) {
        this.glThread.setRenderMode(renderMode);
    }

    public int getRenderMode() {
        return this.glThread.getRenderMode();
    }

    public void requestRender() {
        this.glThread.requestRender();
    }

    public void surfaceCreated(SurfaceTexture texture) {
        this.glThread.surfaceCreated();
    }

    public void surfaceDestroyed(SurfaceTexture texture) {
        this.glThread.surfaceDestroyed();
    }

    public void surfaceChanged(SurfaceTexture texture, int format, int w, int h) {
        this.glThread.onWindowResize(w, h);
    }

    public void onPause() {
        this.glThread.onPause();
    }

    public void onResume() {
        this.glThread.onResume();
    }

    public void queueEvent(Runnable r) {
        this.glThread.queueEvent(r);
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (LOG_ATTACH_DETACH) {
            Logger.d("GLTextureView", "onAttachedToWindow reattach =" + this.detached);
        }

        if (this.detached && this.renderer != null) {
            int renderMode = 1;
            if (this.glThread != null) {
                renderMode = this.glThread.getRenderMode();
            }

            this.glThread = new GLThread(this.mThisWeakRef);
            if (renderMode != 1) {
                this.glThread.setRenderMode(renderMode);
            }

            this.glThread.start();
        }

        this.detached = false;
    }

    protected void onDetachedFromWindow() {
        if (LOG_ATTACH_DETACH) {
            Logger.d("GLTextureView", "onDetachedFromWindow");
        }

        if (this.glThread != null) {
            this.glThread.requestExitAndWait();
        }

        this.detached = true;
        super.onDetachedFromWindow();
    }

    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        this.surfaceChanged(this.getSurfaceTexture(), 0, right - left, bottom - top);
    }

    public void addSurfaceTextureListener(SurfaceTextureListener listener) {
        this.surfaceTextureListeners.add(listener);
    }

    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        this.surfaceCreated(surface);
        this.surfaceChanged(surface, 0, width, height);
        Iterator var4 = this.surfaceTextureListeners.iterator();

        while(var4.hasNext()) {
            SurfaceTextureListener l = (SurfaceTextureListener)var4.next();
            l.onSurfaceTextureAvailable(surface, width, height);
        }

    }

    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
        this.surfaceChanged(surface, 0, width, height);
        Iterator var4 = this.surfaceTextureListeners.iterator();

        while(var4.hasNext()) {
            SurfaceTextureListener l = (SurfaceTextureListener)var4.next();
            l.onSurfaceTextureSizeChanged(surface, width, height);
        }

    }

    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        this.surfaceDestroyed(surface);
        Iterator var2 = this.surfaceTextureListeners.iterator();

        while(var2.hasNext()) {
            SurfaceTextureListener l = (SurfaceTextureListener)var2.next();
            l.onSurfaceTextureDestroyed(surface);
        }

        return true;
    }

    public void onSurfaceTextureUpdated(SurfaceTexture surface) {
        this.requestRender();
        Iterator var2 = this.surfaceTextureListeners.iterator();

        while(var2.hasNext()) {
            SurfaceTextureListener l = (SurfaceTextureListener)var2.next();
            l.onSurfaceTextureUpdated(surface);
        }

    }

    private void checkRenderThreadState() {
        if (this.glThread != null) {
            throw new IllegalStateException("setRenderer has already been called for this instance.");
        }
    }

    private static class GLThreadManager {
        private static String TAG = "GLThreadManager";
        private boolean glesVersionCheckComplete;
        private int glesVersion;
        private boolean glesDriverCheckComplete;
        private boolean multipleGLESContextsAllowed;
        private boolean limitedGLESContexts;
        private static final int kGLES_20 = 131072;
        private static final String kMSM7K_RENDERER_PREFIX = "Q3Dimension MSM7500 ";
        private GLThread eglOwner;

        private GLThreadManager() {
        }

        public synchronized void threadExiting(GLThread thread) {
            if (LOG_THREADS) {
                Logger.d("GLThread", "exiting tid=" + thread.getId());
            }

            thread.exited = true;
            if (this.eglOwner == thread) {
                this.eglOwner = null;
            }

            this.notifyAll();
        }

        public boolean tryAcquireEglContextLocked(GLThread thread) {
            if (this.eglOwner != thread && this.eglOwner != null) {
                this.checkGLESVersion();
                if (this.multipleGLESContextsAllowed) {
                    return true;
                } else {
                    if (this.eglOwner != null) {
                        this.eglOwner.requestReleaseEglContextLocked();
                    }

                    return false;
                }
            } else {
                this.eglOwner = thread;
                this.notifyAll();
                return true;
            }
        }

        public void releaseEglContextLocked(GLThread thread) {
            if (this.eglOwner == thread) {
                this.eglOwner = null;
            }

            this.notifyAll();
        }

        public synchronized boolean shouldReleaseEGLContextWhenPausing() {
            return this.limitedGLESContexts;
        }

        public synchronized boolean shouldTerminateEGLWhenPausing() {
            this.checkGLESVersion();
            return !this.multipleGLESContextsAllowed;
        }

        public synchronized void checkGLDriver(GL10 gl) {
            if (!this.glesDriverCheckComplete) {
                this.checkGLESVersion();
                String renderer = gl.glGetString(7937);
                if (this.glesVersion < 131072) {
                    this.multipleGLESContextsAllowed = !renderer.startsWith("Q3Dimension MSM7500 ");
                    this.notifyAll();
                }

                this.limitedGLESContexts = !this.multipleGLESContextsAllowed;
                if (LOG_SURFACE) {
                    Logger.d(TAG, "checkGLDriver renderer = \"" + renderer + "\" multipleContextsAllowed = " + this.multipleGLESContextsAllowed + " limitedGLESContexts = " + this.limitedGLESContexts);
                }

                this.glesDriverCheckComplete = true;
            }

        }

        private void checkGLESVersion() {
            if (!this.glesVersionCheckComplete) {
                this.glesVersionCheckComplete = true;
            }

        }
    }

    static class LogWriter extends Writer {
        private StringBuilder builder = new StringBuilder();

        LogWriter() {
        }

        public void close() {
            this.flushBuilder();
        }

        public void flush() {
            this.flushBuilder();
        }

        public void write(char[] buf, int offset, int count) {
            for(int i = 0; i < count; ++i) {
                char c = buf[offset + i];
                if (c == '\n') {
                    this.flushBuilder();
                } else {
                    this.builder.append(c);
                }
            }

        }

        private void flushBuilder() {
            if (this.builder.length() > 0) {
                Logger.d("GLTextureView", this.builder.toString());
                this.builder.delete(0, this.builder.length());
            }

        }
    }

    static class GLThread extends Thread {
        private boolean shouldExit;
        private boolean exited;
        private boolean requestPaused;
        private boolean paused;
        private boolean hasSurface;
        private boolean surfaceIsBad;
        private boolean waitingForSurface;
        private boolean haveEglContext;
        private boolean haveEglSurface;
        private boolean shouldReleaseEglContext;
        private int width = 0;
        private int height = 0;
        private int renderMode = 1;
        private boolean requestRender = true;
        private boolean renderComplete;
        private ArrayList<Runnable> eventQueue = new ArrayList();
        private boolean sizeChanged = true;
        private EglHelper eglHelper;
        private WeakReference<GLTextureView> glTextureViewWeakRef;

        GLThread(WeakReference<GLTextureView> glTextureViewWeakRef) {
            this.glTextureViewWeakRef = glTextureViewWeakRef;
        }

        public void run() {
            this.setName("GLThread " + this.getId());
            if (LOG_THREADS) {
                Logger.d("GLThread", "starting tid=" + this.getId());
            }

            try {
                this.guardedRun();
            } catch (Exception var5) {
                if (exceptionListener != null) {
                    exceptionListener.onError(var5);
                }

                Logger.d(var5);
            } finally {
                glThreadManager.threadExiting(this);
            }

        }

        private void stopEglSurfaceLocked() {
            if (this.haveEglSurface) {
                this.haveEglSurface = false;
                this.eglHelper.destroySurface();
            }

        }

        private void stopEglContextLocked() {
            if (this.haveEglContext) {
                this.eglHelper.finish();
                this.haveEglContext = false;
                glThreadManager.releaseEglContextLocked(this);
            }

        }

        private void guardedRun() throws InterruptedException {
            this.eglHelper = new EglHelper(this.glTextureViewWeakRef);
            this.haveEglContext = false;
            this.haveEglSurface = false;
            boolean var30 = false;

            try {
                var30 = true;
                GL10 gl = null;
                boolean createEglContext = false;
                boolean createEglSurface = false;
                boolean createGlInterface = false;
                boolean lostEglContext = false;
                boolean sizeChanged = false;
                boolean wantRenderNotification = false;
                boolean doRenderNotification = false;
                boolean askedToReleaseEglContext = false;
                int w = 0;
                int h = 0;
                Runnable event = null;

                label521:
                while(true) {
                    while(true) {
                        synchronized(glThreadManager) {
                            while(true) {
                                if (this.shouldExit) {
                                    var30 = false;
                                    break label521;
                                }

                                if (!this.eventQueue.isEmpty()) {
                                    event = (Runnable)this.eventQueue.remove(0);
                                    break;
                                }

                                boolean pausing = false;
                                if (this.paused != this.requestPaused) {
                                    pausing = this.requestPaused;
                                    this.paused = this.requestPaused;
                                    glThreadManager.notifyAll();
                                    if (LOG_PAUSE_RESUME) {
                                        Logger.d("GLThread", "paused is now " + this.paused + " tid=" + this.getId());
                                    }
                                }

                                if (this.shouldReleaseEglContext) {
                                    if (LOG_SURFACE) {
                                        Logger.d("GLThread", "releasing EGL context because asked to tid=" + this.getId());
                                    }

                                    this.stopEglSurfaceLocked();
                                    this.stopEglContextLocked();
                                    this.shouldReleaseEglContext = false;
                                    askedToReleaseEglContext = true;
                                }

                                if (lostEglContext) {
                                    this.stopEglSurfaceLocked();
                                    this.stopEglContextLocked();
                                    lostEglContext = false;
                                }

                                if (pausing && this.haveEglSurface) {
                                    if (LOG_SURFACE) {
                                        Logger.d("GLThread", "releasing EGL surface because paused tid=" + this.getId());
                                    }

                                    this.stopEglSurfaceLocked();
                                }

                                if (pausing && this.haveEglContext) {
                                    GLTextureView view = (GLTextureView)this.glTextureViewWeakRef.get();
                                    boolean preserveEglContextOnPause = view == null ? false : view.preserveEGLContextOnPause;
                                    if (!preserveEglContextOnPause || glThreadManager.shouldReleaseEGLContextWhenPausing()) {
                                        this.stopEglContextLocked();
                                        if (LOG_SURFACE) {
                                            Logger.d("GLThread", "releasing EGL context because paused tid=" + this.getId());
                                        }
                                    }
                                }

                                if (pausing && glThreadManager.shouldTerminateEGLWhenPausing()) {
                                    this.eglHelper.finish();
                                    if (LOG_SURFACE) {
                                        Logger.d("GLThread", "terminating EGL because paused tid=" + this.getId());
                                    }
                                }

                                if (!this.hasSurface && !this.waitingForSurface) {
                                    if (LOG_SURFACE) {
                                        Logger.d("GLThread", "noticed textureView surface lost tid=" + this.getId());
                                    }

                                    if (this.haveEglSurface) {
                                        this.stopEglSurfaceLocked();
                                    }

                                    this.waitingForSurface = true;
                                    this.surfaceIsBad = false;
                                    glThreadManager.notifyAll();
                                }

                                if (this.hasSurface && this.waitingForSurface) {
                                    if (LOG_SURFACE) {
                                        Logger.d("GLThread", "noticed textureView surface acquired tid=" + this.getId());
                                    }

                                    this.waitingForSurface = false;
                                    glThreadManager.notifyAll();
                                }

                                if (doRenderNotification) {
                                    if (LOG_SURFACE) {
                                        Logger.d("GLThread", "sending render notification tid=" + this.getId());
                                    }

                                    wantRenderNotification = false;
                                    doRenderNotification = false;
                                    this.renderComplete = true;
                                    glThreadManager.notifyAll();
                                }

                                if (this.readyToDraw()) {
                                    if (!this.haveEglContext) {
                                        if (askedToReleaseEglContext) {
                                            askedToReleaseEglContext = false;
                                        } else if (glThreadManager.tryAcquireEglContextLocked(this)) {
                                            try {
                                                this.eglHelper.start();
                                            } catch (RuntimeException var35) {
                                                glThreadManager.releaseEglContextLocked(this);
                                                throw var35;
                                            }

                                            this.haveEglContext = true;
                                            createEglContext = true;
                                            glThreadManager.notifyAll();
                                        }
                                    }

                                    if (this.haveEglContext && !this.haveEglSurface) {
                                        this.haveEglSurface = true;
                                        createEglSurface = true;
                                        createGlInterface = true;
                                        sizeChanged = true;
                                    }

                                    if (this.haveEglSurface) {
                                        if (this.sizeChanged) {
                                            sizeChanged = true;
                                            w = this.width;
                                            h = this.height;
                                            wantRenderNotification = true;
                                            if (LOG_SURFACE) {
                                                Logger.d("GLThread", "noticing that we want render notification tid=" + this.getId());
                                            }

                                            createEglSurface = true;
                                            this.sizeChanged = false;
                                        }

                                        this.requestRender = false;
                                        glThreadManager.notifyAll();
                                        break;
                                    }
                                }

                                if (LOG_THREADS) {
                                    Logger.d("GLThread", "waiting tid=" + this.getId() + " haveEglContext: " + this.haveEglContext + " haveEglSurface: " + this.haveEglSurface + " paused: " + this.paused + " hasSurface: " + this.hasSurface + " surfaceIsBad: " + this.surfaceIsBad + " waitingForSurface: " + this.waitingForSurface + " width: " + this.width + " height: " + this.height + " requestRender: " + this.requestRender + " renderMode: " + this.renderMode);
                                }

                                glThreadManager.wait();
                            }
                        }

                        if (event != null) {
                            event.run();
                            event = null;
                        } else {
                            if (createEglSurface) {
                                if (LOG_SURFACE) {
                                    Logger.d("GLThread", "egl createSurface");
                                }

                                if (!this.eglHelper.createSurface()) {
                                    synchronized(glThreadManager) {
                                        this.surfaceIsBad = true;
                                        glThreadManager.notifyAll();
                                        continue;
                                    }
                                }

                                createEglSurface = false;
                            }

                            if (createGlInterface) {
                                gl = (GL10)this.eglHelper.createGL();
                                glThreadManager.checkGLDriver(gl);
                                createGlInterface = false;
                            }

                            GLTextureView view;
                            if (createEglContext) {
                                if (LOG_RENDERER) {
                                    Logger.d("GLThread", "onSurfaceCreated");
                                }

                                view = (GLTextureView)this.glTextureViewWeakRef.get();
                                if (view != null) {
                                    view.renderer.onSurfaceCreated(gl, this.eglHelper.eglConfig);
                                }

                                createEglContext = false;
                            }

                            if (sizeChanged) {
                                if (LOG_RENDERER) {
                                    Logger.d("GLThread", "onSurfaceChanged(" + w + ", " + h + ")");
                                }

                                view = (GLTextureView)this.glTextureViewWeakRef.get();
                                if (view != null) {
                                    view.renderer.onSurfaceChanged(gl, w, h);
                                }

                                sizeChanged = false;
                            }

                            if (LOG_RENDERER_DRAW_FRAME) {
                                Logger.d("GLThread", "onDrawFrame tid=" + this.getId());
                            }

                            view = (GLTextureView)this.glTextureViewWeakRef.get();
                            if (view != null) {
                                view.renderer.onDrawFrame(gl);
                            }

                            int swapError = this.eglHelper.swap();
                            switch(swapError) {
                                case 12288:
                                    break;
                                case 12302:
                                    if (LOG_SURFACE) {
                                        Logger.d("GLThread", "egl context lost tid=" + this.getId());
                                    }

                                    lostEglContext = true;
                                    break;
                                default:
                                    EglHelper.logEglErrorAsWarning("GLThread", "eglSwapBuffers", swapError);
                                    synchronized(glThreadManager) {
                                        this.surfaceIsBad = true;
                                        glThreadManager.notifyAll();
                                    }
                            }

                            if (wantRenderNotification) {
                                doRenderNotification = true;
                            }
                        }
                    }
                }
            } finally {
                if (var30) {
                    synchronized(glThreadManager) {
                        this.stopEglSurfaceLocked();
                        this.stopEglContextLocked();
                    }
                }
            }

            synchronized(glThreadManager) {
                this.stopEglSurfaceLocked();
                this.stopEglContextLocked();
            }
        }

        public boolean ableToDraw() {
            return this.haveEglContext && this.haveEglSurface && this.readyToDraw();
        }

        private boolean readyToDraw() {
            return !this.paused && this.hasSurface && !this.surfaceIsBad && this.width > 0 && this.height > 0 && (this.requestRender || this.renderMode == 1);
        }

        public void setRenderMode(int renderMode) {
            if (0 <= renderMode && renderMode <= 1) {
                synchronized(glThreadManager) {
                    this.renderMode = renderMode;
                    glThreadManager.notifyAll();
                }
            } else {
                throw new IllegalArgumentException("renderMode");
            }
        }

        public int getRenderMode() {
            synchronized(glThreadManager) {
                return this.renderMode;
            }
        }

        public void requestRender() {
            synchronized(glThreadManager) {
                this.requestRender = true;
                glThreadManager.notifyAll();
            }
        }

        public void surfaceCreated() {
            synchronized(glThreadManager) {
                if (LOG_THREADS) {
                    Logger.d("GLThread", "surfaceCreated tid=" + this.getId());
                }

                this.hasSurface = true;
                glThreadManager.notifyAll();

                while(this.waitingForSurface && !this.exited) {
                    try {
                        glThreadManager.wait();
                    } catch (InterruptedException var4) {
                        Thread.currentThread().interrupt();
                    }
                }

            }
        }

        public void surfaceDestroyed() {
            synchronized(glThreadManager) {
                if (LOG_THREADS) {
                    Logger.d("GLThread", "surfaceDestroyed tid=" + this.getId());
                }

                this.hasSurface = false;
                glThreadManager.notifyAll();

                while(!this.waitingForSurface && !this.exited) {
                    try {
                        glThreadManager.wait();
                    } catch (InterruptedException var4) {
                        Thread.currentThread().interrupt();
                    }
                }

            }
        }

        public void onPause() {
            synchronized(glThreadManager) {
                if (LOG_PAUSE_RESUME) {
                    Logger.d("GLThread", "onPause tid=" + this.getId());
                }

                this.requestPaused = true;
                glThreadManager.notifyAll();

                while(!this.exited && !this.paused) {
                    if (LOG_PAUSE_RESUME) {
                        Logger.d("Main thread", "onPause waiting for paused.");
                    }

                    try {
                        glThreadManager.wait();
                    } catch (InterruptedException var4) {
                        Thread.currentThread().interrupt();
                    }
                }

            }
        }

        public void onResume() {
            synchronized(glThreadManager) {
                if (LOG_PAUSE_RESUME) {
                    Logger.d("GLThread", "onResume tid=" + this.getId());
                }

                this.requestPaused = false;
                this.requestRender = true;
                this.renderComplete = false;
                glThreadManager.notifyAll();

                while(!this.exited && this.paused && !this.renderComplete) {
                    if (LOG_PAUSE_RESUME) {
                        Logger.d("Main thread", "onResume waiting for !paused.");
                    }

                    try {
                        glThreadManager.wait();
                    } catch (InterruptedException var4) {
                        Thread.currentThread().interrupt();
                    }
                }

            }
        }

        public void onWindowResize(int w, int h) {
            synchronized(glThreadManager) {
                this.width = w;
                this.height = h;
                this.sizeChanged = true;
                this.requestRender = true;
                this.renderComplete = false;
                glThreadManager.notifyAll();

                while(!this.exited && !this.paused && !this.renderComplete && this.ableToDraw()) {
                    if (LOG_SURFACE) {
                        Logger.d("Main thread", "onWindowResize waiting for render complete from tid=" + this.getId());
                    }

                    try {
                        glThreadManager.wait();
                    } catch (InterruptedException var6) {
                        Thread.currentThread().interrupt();
                    }
                }

            }
        }

        public void requestExitAndWait() {
            synchronized(glThreadManager) {
                this.shouldExit = true;
                glThreadManager.notifyAll();

                while(!this.exited) {
                    try {
                        glThreadManager.wait();
                    } catch (InterruptedException var4) {
                        Thread.currentThread().interrupt();
                    }
                }

            }
        }

        public void requestReleaseEglContextLocked() {
            this.shouldReleaseEglContext = true;
            glThreadManager.notifyAll();
        }

        public void queueEvent(Runnable r) {
            if (r == null) {
                throw new IllegalArgumentException("r must not be null");
            } else {
                synchronized(glThreadManager) {
                    this.eventQueue.add(r);
                    glThreadManager.notifyAll();
                }
            }
        }
    }

    private static class EglHelper {
        private WeakReference<GLTextureView> glTextureViewWeakRef;
        EGL10 egl;
        EGLDisplay eglDisplay;
        EGLSurface eglSurface;
        EGLConfig eglConfig;
        EGLContext eglContext;

        public EglHelper(WeakReference<GLTextureView> glTextureViewWeakReference) {
            this.glTextureViewWeakRef = glTextureViewWeakReference;
        }

        public void start() {
            if (LOG_EGL) {
                Logger.d("EglHelper", "start() tid=" + Thread.currentThread().getId());
            }

            this.egl = (EGL10)EGLContext.getEGL();
            this.eglDisplay = this.egl.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
            if (LOG_EGL) {
                Logger.d("EglHelper", "start() eglDisplay=" + this.eglDisplay);
            }

            if (this.eglDisplay == EGL10.EGL_NO_DISPLAY) {
                throw new RuntimeException("eglGetDisplay failed");
            } else {
                int[] version = new int[2];
                if (!this.egl.eglInitialize(this.eglDisplay, version)) {
                    throw new RuntimeException("eglInitialize failed");
                } else {
                    GLTextureView view = (GLTextureView)this.glTextureViewWeakRef.get();
                    if (LOG_EGL) {
                        Logger.d("EglHelper", "start() version[0]=" + version[0] + ",version[1]=" + version[1] + ",view=" + view);
                    }

                    if (view == null) {
                        this.eglConfig = null;
                        this.eglContext = null;
                    } else {
                        if (view.useNoAlpha) {
                            this.eglConfig = view.eglConfigChooserNoAlpha.chooseConfig(this.egl, this.eglDisplay);
                        } else {
                            this.eglConfig = view.eglConfigChooser.chooseConfig(this.egl, this.eglDisplay);
                        }

                        this.eglContext = view.eglContextFactory.createContext(this.egl, this.eglDisplay, this.eglConfig);
                        if (LOG_EGL) {
                            Logger.d("EglHelper", "useNoAlpha=" + view.useNoAlpha + ",egl=" + this.egl + " eglDisplay=" + this.eglDisplay + ",eglConfig=" + this.eglConfig + ",view.eglContextFactory=" + view.eglContextFactory + ",view.eglConfigChooser=" + view.eglConfigChooser);
                        }
                    }

                    if (LOG_EGL) {
                        Logger.d("EglHelper", "createContext " + this.eglContext + " tid=" + Thread.currentThread().getId() + ",EGL10.EGL_NO_CONTEXT=" + EGL10.EGL_NO_CONTEXT);
                    }

                    if (this.eglContext == null || this.eglContext == EGL10.EGL_NO_CONTEXT) {
                        if (view != null && !view.useNoAlpha) {
                            String reportError = "error.support.alpha.opengl." + Build.BRAND + "," + Build.MODEL + "," + Build.VERSION.SDK_INT;
                            if (exceptionListener != null) {
                                exceptionListener.onAlphaError(reportError);
                            }

                            Logger.d("GLTextureView", reportError);
                            view.useNoAlpha = true;
                            this.eglConfig = view.eglConfigChooserNoAlpha.chooseConfig(this.egl, this.eglDisplay);
                            this.eglContext = view.eglContextFactory.createContext(this.egl, this.eglDisplay, this.eglConfig);
                            Logger.d("EglHelper", "createContextNoAlpha " + this.eglContext + " tid=" + Thread.currentThread().getId() + ",EGL10.eglConfig=" + this.eglConfig);
                            if (this.eglContext == null || this.eglContext == EGL10.EGL_NO_CONTEXT) {
                                this.eglContext = null;
                                this.throwEglException("createContextNoAlpha");
                            }
                        } else {
                            this.eglContext = null;
                            this.throwEglException("createContext");
                        }
                    }

                    this.eglSurface = null;
                }
            }
        }

        public boolean createSurface() {
            if (LOG_EGL) {
                Logger.d("EglHelper", "createSurface()  tid=" + Thread.currentThread().getId());
            }

            if (this.egl == null) {
                throw new RuntimeException("egl not initialized");
            } else if (this.eglDisplay == null) {
                throw new RuntimeException("eglDisplay not initialized");
            } else if (this.eglConfig == null) {
                throw new RuntimeException("eglConfig not initialized");
            } else {
                this.destroySurfaceImp();
                GLTextureView view = (GLTextureView)this.glTextureViewWeakRef.get();
                if (view != null) {
                    this.eglSurface = view.eglWindowSurfaceFactory.createWindowSurface(this.egl, this.eglDisplay, this.eglConfig, view.getSurfaceTexture());
                } else {
                    this.eglSurface = null;
                }

                if (this.eglSurface != null && this.eglSurface != EGL10.EGL_NO_SURFACE) {
                    if (!this.egl.eglMakeCurrent(this.eglDisplay, this.eglSurface, this.eglSurface, this.eglContext)) {
                        logEglErrorAsWarning("EGLHelper", "eglMakeCurrent", this.egl.eglGetError());
                        return false;
                    } else {
                        return true;
                    }
                } else {
                    int error = this.egl.eglGetError();
                    if (error == 12299) {
                        Logger.d("EglHelper", "createWindowSurface returned EGL_BAD_NATIVE_WINDOW.");
                    }

                    return false;
                }
            }
        }

        GL createGL() {
            GL gl = this.eglContext.getGL();
            GLTextureView view = (GLTextureView)this.glTextureViewWeakRef.get();
            if (view != null) {
                if (view.glWrapper != null) {
                    gl = view.glWrapper.wrap(gl);
                }

                if ((view.debugFlags & 3) != 0) {
                    int configFlags = 0;
                    Writer log = null;
                    if ((view.debugFlags & 1) != 0) {
                        configFlags |= 1;
                    }

                    if ((view.debugFlags & 2) != 0) {
                        log = new LogWriter();
                    }

                    gl = GLDebugHelper.wrap(gl, configFlags, log);
                }
            }

            return gl;
        }

        public int swap() {
            return !this.egl.eglSwapBuffers(this.eglDisplay, this.eglSurface) ? this.egl.eglGetError() : 12288;
        }

        public void destroySurface() {
            if (LOG_EGL) {
                Logger.d("EglHelper", "destroySurface()  tid=" + Thread.currentThread().getId());
            }

            this.destroySurfaceImp();
        }

        private void destroySurfaceImp() {
            if (this.eglSurface != null && this.eglSurface != EGL10.EGL_NO_SURFACE) {
                this.egl.eglMakeCurrent(this.eglDisplay, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_CONTEXT);
                GLTextureView view = (GLTextureView)this.glTextureViewWeakRef.get();
                if (view != null) {
                    view.eglWindowSurfaceFactory.destroySurface(this.egl, this.eglDisplay, this.eglSurface);
                }

                this.eglSurface = null;
            }

        }

        public void finish() {
            if (LOG_EGL) {
                Logger.d("EglHelper", "finish() tid=" + Thread.currentThread().getId());
            }

            if (this.eglContext != null) {
                GLTextureView view = (GLTextureView)this.glTextureViewWeakRef.get();
                if (view != null) {
                    view.eglContextFactory.destroyContext(this.egl, this.eglDisplay, this.eglContext);
                }

                this.eglContext = null;
            }

            if (this.eglDisplay != null) {
                this.egl.eglTerminate(this.eglDisplay);
                this.eglDisplay = null;
            }

        }

        private void throwEglException(String function) {
            throwEglException(function, this.egl.eglGetError());
        }

        public static void throwEglException(String function, int error) {
            String message = formatEglError(function, error);
            if (LOG_THREADS) {
                Logger.d("EglHelper", "throwEglException tid=" + Thread.currentThread().getId() + " " + message);
            }

            throw new RuntimeException(message);
        }

        public static void logEglErrorAsWarning(String tag, String function, int error) {
            Logger.d(tag, formatEglError(function, error));
        }

        public static String formatEglError(String function, int error) {
            return function + " failed: " + error;
        }
    }

    private class SimpleEGLConfigChooser extends ComponentSizeChooser {
        public SimpleEGLConfigChooser(boolean withDepthBuffer) {
            super(8, 8, 8, 0, withDepthBuffer ? 16 : 0, 0);
        }
    }

    private class ComponentSizeChooser extends BaseConfigChooser {
        private int[] value = new int[1];
        protected int redSize;
        protected int greenSize;
        protected int blueSize;
        protected int alphaSize;
        protected int depthSize;
        protected int stencilSize;

        public ComponentSizeChooser(int redSize, int greenSize, int blueSize, int alphaSize, int depthSize, int stencilSize) {
            super(new int[]{12324, redSize, 12323, greenSize, 12322, blueSize, 12321, alphaSize, 12325, depthSize, 12326, stencilSize, 12344});
            this.redSize = redSize;
            this.greenSize = greenSize;
            this.blueSize = blueSize;
            this.alphaSize = alphaSize;
            this.depthSize = depthSize;
            this.stencilSize = stencilSize;
            Logger.d("GLTextureView", "ComponentSizeChooser.redSize=" + redSize + ",greenSize=" + greenSize + ",blueSize=" + blueSize + ",alphaSize=" + alphaSize + ",depthSize=" + depthSize + ",stencilSize=" + stencilSize);
        }

        public EGLConfig chooseConfig(EGL10 egl, EGLDisplay display, EGLConfig[] configs) {
            Logger.d("GLTextureView", "ComponentSizeChooser.configs=" + configs);
            EGLConfig[] var4 = configs;
            int var5 = configs.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                EGLConfig config = var4[var6];
                int d = this.findConfigAttrib(egl, display, config, 12325, 0);
                int s = this.findConfigAttrib(egl, display, config, 12326, 0);
                Logger.d("GLTextureView", "ComponentSizeChooser.chooseConfig.config=" + config + ",d=" + d + ",s=" + s);
                if (d >= this.depthSize && s >= this.stencilSize) {
                    int r = this.findConfigAttrib(egl, display, config, 12324, 0);
                    int g = this.findConfigAttrib(egl, display, config, 12323, 0);
                    int b = this.findConfigAttrib(egl, display, config, 12322, 0);
                    int a = this.findConfigAttrib(egl, display, config, 12321, 0);
                    Logger.d("GLTextureView", "ComponentSizeChooser.chooseConfig.r=" + r + ",g=" + g + ",b=" + b + ",a=" + a);
                    if (r == this.redSize && g == this.greenSize && b == this.blueSize && a == this.alphaSize) {
                        return config;
                    }
                }
            }

            return null;
        }

        private int findConfigAttrib(EGL10 egl, EGLDisplay display, EGLConfig config, int attribute, int defaultValue) {
            return egl.eglGetConfigAttrib(display, config, attribute, this.value) ? this.value[0] : defaultValue;
        }
    }

    private abstract class BaseConfigChooser implements EGLConfigChooser {
        protected int[] mConfigSpec;

        public BaseConfigChooser(int[] configSpec) {
            this.mConfigSpec = this.filterConfigSpec(configSpec);
        }

        public EGLConfig chooseConfig(EGL10 egl, EGLDisplay display) {
            int[] num_config = new int[1];
            if (!egl.eglChooseConfig(display, this.mConfigSpec, (EGLConfig[])null, 0, num_config)) {
                throw new IllegalArgumentException("eglChooseConfig failed");
            } else {
                int numConfigs = num_config[0];
                if (numConfigs <= 0) {
                    throw new IllegalArgumentException("No configs match configSpec");
                } else {
                    EGLConfig[] configs = new EGLConfig[numConfigs];
                    if (!egl.eglChooseConfig(display, this.mConfigSpec, configs, numConfigs, num_config)) {
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

        abstract EGLConfig chooseConfig(EGL10 var1, EGLDisplay var2, EGLConfig[] var3);

        private int[] filterConfigSpec(int[] configSpec) {
            if (eglContextClientVersion != 2) {
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
    }

    public interface EGLConfigChooser {
        EGLConfig chooseConfig(EGL10 var1, EGLDisplay var2);
    }

    private static class DefaultWindowSurfaceFactory implements EGLWindowSurfaceFactory {
        private DefaultWindowSurfaceFactory() {
        }

        public EGLSurface createWindowSurface(EGL10 egl, EGLDisplay display, EGLConfig config, Object nativeWindow) {
            EGLSurface result = null;

            try {
                result = egl.eglCreateWindowSurface(display, config, nativeWindow, (int[])null);
            } catch (IllegalArgumentException var7) {
                Logger.d("GLTextureView", "eglCreateWindowSurface" + var7.getMessage());
            }

            return result;
        }

        public void destroySurface(EGL10 egl, EGLDisplay display, EGLSurface surface) {
            egl.eglDestroySurface(display, surface);
        }
    }

    public interface EGLWindowSurfaceFactory {
        EGLSurface createWindowSurface(EGL10 var1, EGLDisplay var2, EGLConfig var3, Object var4);

        void destroySurface(EGL10 var1, EGLDisplay var2, EGLSurface var3);
    }

    private class DefaultContextFactory implements EGLContextFactory {
        private int EGL_CONTEXT_CLIENT_VERSION;

        private DefaultContextFactory() {
            this.EGL_CONTEXT_CLIENT_VERSION = 12440;
        }

        public EGLContext createContext(EGL10 egl, EGLDisplay display, EGLConfig config) {
            int[] attrib_list = new int[]{this.EGL_CONTEXT_CLIENT_VERSION, eglContextClientVersion, 12344};
            Logger.d("GLTextureView", "eglContextClientVersion=" + eglContextClientVersion + " EGL_CONTEXT_CLIENT_VERSION=" + this.EGL_CONTEXT_CLIENT_VERSION);
            return egl.eglCreateContext(display, config, EGL10.EGL_NO_CONTEXT, eglContextClientVersion != 0 ? attrib_list : null);
        }

        public void destroyContext(EGL10 egl, EGLDisplay display, EGLContext context) {
            if (!egl.eglDestroyContext(display, context)) {
                Logger.d("DefaultContextFactory", "display:" + display + " context: " + context);
                if (LOG_THREADS) {
                    Logger.d("DefaultContextFactory", "tid=" + Thread.currentThread().getId());
                }

                EglHelper.throwEglException("eglDestroyContex", egl.eglGetError());
            }

        }
    }

    public interface EGLContextFactory {
        EGLContext createContext(EGL10 var1, EGLDisplay var2, EGLConfig var3);

        void destroyContext(EGL10 var1, EGLDisplay var2, EGLContext var3);
    }

    public interface Renderer {
        void onSurfaceCreated(GL10 var1, EGLConfig var2);

        void onSurfaceChanged(GL10 var1, int var2, int var3);

        void onDrawFrame(GL10 var1);
    }

    public interface GLWrapper {
        GL wrap(GL var1);
    }
}