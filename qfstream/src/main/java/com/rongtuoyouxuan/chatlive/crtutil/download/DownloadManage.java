package com.rongtuoyouxuan.chatlive.crtutil.download;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.SystemClock;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * 文件下载工具类<br/>
 * 可支持创建一条下载通道下载任务排队执行<br/>
 * 可支持一条通道的下载任务整体取消<br/>
 * 可支持单个下载任务单独取消<br/>
 * 可支持创建共享的下载通道多个任务并发执行<br/>
 * 创建人:yangzhiqian
 */
public final class DownloadManage {
    private static final boolean DEBUG = false;
    private static final String TAG = "DownloadManage";
    /**
     * 共享的下载线程建议最少5个下载任务
     */
    private static final int MIN_SUGGEST_PIPE_TASK_SIZE = 5;
    private static int PIPE_INDEX = 1;
    private static DownloadManage sInstance;
    /**
     * 记录当前可能有多少条下载的线程，每一个DownloadPipe表示一个下载线程（该线程也许已经被退出了、也许还没开启）
     * 下载线程如果自己退出，此集合不会自动清除
     */
    private final List<DownloadPipe> mSharedDownloadPipe = Collections.synchronizedList(new ArrayList<DownloadPipe>());
    private final List<DownloadPipe> mIsolatedDownloadPipe = Collections.synchronizedList(new ArrayList<DownloadPipe>());

    private DownloadManage() {
    }

    public static DownloadManage instance() {
        if (sInstance == null) {
            synchronized (DownloadManage.class) {
                if (sInstance == null) {
                    sInstance = new DownloadManage();
                }
            }
        }
        return sInstance;
    }

    /**
     * 启动一个下载任务
     * 如果当前没有可用的共享的下载线程，则会新创建一个共享线程来下载，否则共用现有的下载线程
     *
     * @param downloadConf 下载任务的描述
     * @return 已经开启了的下载任务
     * @see #startDownloadTask(DownloadConf, DownloadPipe)
     */
    public DownloadTask startDownloadTask(@NonNull DownloadConf downloadConf) {
        return startDownloadTask(downloadConf, null);
    }

    /**
     * 启动下载任务
     *
     * @param downloadConf 下载任务的描述
     * @param downloadPipe 下载线程，如果该DownloadPipe为null,则和{@link #startDownloadTask(DownloadConf)}
     *                     相同，如果该DownloadPipe已经被终止了，这回调失败
     * @return 已经开启了的下载任务
     */
    public DownloadTask startDownloadTask(@NonNull DownloadConf downloadConf, @Nullable DownloadPipe downloadPipe) {
        if (downloadPipe == null) {
            downloadPipe = getSuggestDownloadPipe();
        }
        DownloadTask task = new DownloadTask(downloadConf, downloadPipe);
        task.start();
        return task;
    }


    /**
     * 终止所有的下载任务
     */
    public void terminalAll() {
        List<DownloadPipe> pipes = new ArrayList<>();
        pipes.addAll(mSharedDownloadPipe);
        pipes.addAll(mIsolatedDownloadPipe);
        for (DownloadPipe pipe : pipes) {
            pipe.terminal();
        }
    }

    /**
     * 查找下载线
     *
     * @param tag 唯一表示
     * @return DownloadPipe
     */
    @Nullable
    public DownloadPipe findDownloadPipe(String tag) {
        if (TextUtils.isEmpty(tag)) {
            return null;
        }
        clearTerminalDownloadPipe();
        Iterator<DownloadPipe> iterator = mIsolatedDownloadPipe.iterator();
        while (iterator.hasNext()) {
            DownloadPipe next;
            try {
                next = iterator.next();
            } catch (Exception ignore) {
                continue;
            }
            if (next != null) {
                if (next.isTerminal()) {
                    //清除已经被终止了的下载线程
                    iterator.remove();
                    continue;
                }
                if (next.mTag.equals(tag)) {
                    return next;
                }
            }
        }
        return null;
    }

    /**
     * 从现有的下载线程中找到一个可以共用的的线程，否则创建一个新的DownloadPipe线程实例
     *
     * @return DownloadPipe
     */
    @NonNull
    public DownloadPipe getSuggestDownloadPipe() {
        clearTerminalDownloadPipe();
        DownloadPipe suggest = null;
        Iterator<DownloadPipe> iterator = mSharedDownloadPipe.iterator();
        while (iterator.hasNext()) {
            DownloadPipe next;
            try {
                next = iterator.next();
            } catch (Exception ignore) {
                continue;
            }
            if (next != null) {
                if (next.isTerminal()) {
                    //清除已经被终止了的下载线程
                    iterator.remove();
                    continue;
                }
                if (suggest == null || next.getQueuedDownloadTask().size() < suggest.getQueuedDownloadTask().size()) {
                    suggest = next;
                }
            }
        }
        if (DEBUG) {
            Log.d(TAG, "getSuggestDownloadPipe:alive DownloadPipe size = " + mSharedDownloadPipe.size() + ",has suggest=" + (suggest != null));
        }
        if (suggest != null && suggest.getQueuedDownloadTask().size() < Math.max(MIN_SUGGEST_PIPE_TASK_SIZE, Math.pow(2, mSharedDownloadPipe.size()))) {
            return suggest;
        }
        //没有合适的可用共享下载线程，创建新的
        return createNewDownloadPipe("DownloadPipe-suggest-" + PIPE_INDEX++, true);
    }

    /**
     * 创建新的DownloadPipe线程实例
     *
     * @param tag   DownloadPipe标识
     * @param share 是否共享，如果为true,则该线程会被共享给全局的下载线程(如果指定该DownloadPipe则依然共用)
     * @return DownloadPipe
     */
    @NonNull
    public DownloadPipe createNewDownloadPipe(@NonNull String tag, boolean share) {
        DownloadPipe downloadPipe = new DownloadPipe(tag);
        if (share) {
            mSharedDownloadPipe.add(downloadPipe);
        } else {
            mIsolatedDownloadPipe.add(downloadPipe);
        }
        return downloadPipe;
    }

    private void clearTerminalDownloadPipe() {
        Iterator<DownloadPipe> iterator = mSharedDownloadPipe.iterator();
        while (iterator.hasNext()) {
            DownloadPipe next;
            try {
                next = iterator.next();
            } catch (Exception ignore) {
                continue;
            }
            if (next != null) {
                if (next.isTerminal()) {
                    //清除已经被终止了的下载线程
                    iterator.remove();
                }
            }
        }
        iterator = mIsolatedDownloadPipe.iterator();
        while (iterator.hasNext()) {
            DownloadPipe next;
            try {
                next = iterator.next();
            } catch (Exception ignore) {
                continue;
            }
            if (next != null) {
                if (next.isTerminal()) {
                    //清除已经被终止了的下载线程
                    iterator.remove();
                }
            }
        }
    }

    /**
     * 下载回调<br/>
     * 处理回调线程,建议使用{@link DownloadListenerWrapper}
     */
    public interface DownloadListener {

        /**
         * 调用{@link DownloadTask#cancel()}时或则下载的任务指定的DownloadPipe被终止时调用<br/>
         * 但如果该任务已经执行完毕,取消失败，该回调不会调用<br/>
         *
         * @param task 被取消的任务
         */
        void onCancel(@NonNull DownloadTask task);

        /**
         * 下载任务开始
         *
         * @param task 下载的任务
         */
        void onStart(@NonNull DownloadTask task);

        /**
         * 下载进度变化
         *
         * @param task          下载的任务
         * @param totalLength   文件总大小
         * @param currentLength 当前下载完的数据
         */
        void onProgress(@NonNull DownloadTask task, long totalLength, long currentLength);

        /**
         * 下载成功
         *
         * @param task 下载的任务
         */
        void onSucceed(@NonNull DownloadTask task);

        /**
         * 下载失败
         *
         * @param task      下载的任务
         * @param failCode  错误码({@link DownloadTask#CODE_PIPE}、{@link DownloadTask#CODE_URL}、{@link DownloadTask#CODE_CREATE_FILE}、{@link DownloadTask#CODE_CONN_RESPONSE_CODE}、{@link DownloadTask#CODE_CONN}、{@link DownloadTask#CODE_INPUT}、{@link DownloadTask#CODE_OUTPUT}、{@link DownloadTask#CODE_RECEIVE})
         * @param msg       错误消息
         * @param exception 异常信息
         */
        void onFail(@NonNull DownloadTask task, int failCode, String msg, @Nullable Throwable exception);

        /**
         * 完成(不管是成功、失败还是取消,该方法都会被调用,而且在所有回调之后调用)
         *
         * @param task    下载的任务
         * @param succeed 下载是否成功
         */
        void onComplete(@NonNull DownloadTask task, boolean succeed);

        class EmptyDownloadListener implements DownloadListener {

            @Override
            public void onCancel(@NonNull DownloadTask task) {

            }

            @Override
            public void onStart(@NonNull DownloadTask task) {

            }

            @Override
            public void onProgress(@NonNull DownloadTask task, long totalLength, long currentLength) {

            }

            @Override
            public void onSucceed(@NonNull DownloadTask task) {

            }

            @Override
            public void onFail(@NonNull DownloadTask task, int failCode, String msg, @Nullable Throwable exception) {

            }

            @Override
            public void onComplete(@NonNull DownloadTask task, boolean succeed) {

            }
        }

        class DownloadListenerWrapper implements DownloadListener {
            private Handler mCallbackHandler;
            private DownloadListener mDownloadListener;

            /**
             * @param callbackLooper 如果为null则回调可能在其他线程，如果callbackLooper不为null,回调在指定looper线程
             * @param listener       回调监听，部分事件监听建议使用{@link EmptyDownloadListener}子类
             */
            public DownloadListenerWrapper(Looper callbackLooper, DownloadListener listener) {
                if (callbackLooper != null) {
                    mCallbackHandler = new Handler(callbackLooper);
                }
                this.mDownloadListener = listener;
            }

            @Override
            public void onCancel(@NonNull final DownloadTask task) {
                if (this.mDownloadListener == null) {
                    return;
                }
                if (this.mCallbackHandler != null) {
                    mCallbackHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mDownloadListener.onCancel(task);
                        }
                    });
                } else {
                    mDownloadListener.onCancel(task);
                }
            }

            @Override
            public void onStart(@NonNull final DownloadTask task) {
                if (this.mDownloadListener == null) {
                    return;
                }
                if (this.mCallbackHandler != null) {
                    mCallbackHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mDownloadListener.onStart(task);
                        }
                    });
                } else {
                    mDownloadListener.onStart(task);
                }
            }

            @Override
            public void onProgress(@NonNull final DownloadTask task, final long totalLength, final long currentLength) {
                if (this.mDownloadListener == null) {
                    return;
                }
                if (this.mCallbackHandler != null) {
                    mCallbackHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mDownloadListener.onProgress(task, totalLength, currentLength);
                        }
                    });
                } else {
                    mDownloadListener.onProgress(task, totalLength, currentLength);
                }
            }

            @Override
            public void onSucceed(@NonNull final DownloadTask task) {
                if (this.mDownloadListener == null) {
                    return;
                }
                if (this.mCallbackHandler != null) {
                    mCallbackHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mDownloadListener.onSucceed(task);
                        }
                    });
                } else {
                    mDownloadListener.onSucceed(task);
                }
            }

            @Override
            public void onFail(@NonNull final DownloadTask task, final int failCode, final String msg, @Nullable final Throwable exception) {
                if (this.mDownloadListener == null) {
                    return;
                }
                if (this.mCallbackHandler != null) {
                    mCallbackHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mDownloadListener.onFail(task, failCode, msg, exception);
                        }
                    });
                } else {
                    mDownloadListener.onFail(task, failCode, msg, exception);
                }
            }

            @Override
            public void onComplete(@NonNull final DownloadTask task, final boolean succeed) {
                if (this.mDownloadListener == null) {
                    return;
                }
                if (this.mCallbackHandler != null) {
                    mCallbackHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mDownloadListener.onComplete(task, succeed);
                        }
                    });
                } else {
                    mDownloadListener.onComplete(task, succeed);
                }
            }
        }
    }

    public final static class DownloadPipe {
        private static int sDownloadPipeIndex = 1;
        private final List<DownloadTask> mDownloadTasks = Collections.synchronizedList(new ArrayList<DownloadTask>());
        private final List<DownloadPipeStateListener> mPipeListeners = Collections.synchronizedList(new ArrayList<DownloadPipeStateListener>());
        private String mTag;
        private HandlerThread mHandlerThread;
        private Handler mHandler;
        private boolean mIsStarted = false;
        private boolean mIsTerminal = false;

        DownloadPipe(@NonNull String tag) {
            this.mTag = tag;
        }        private final Runnable mTerminalRun = new Runnable() {
            @Override
            public void run() {
                if (DEBUG) {
                    Log.d(TAG, "DownloadPipe-" + mTag + " wait to terminal");
                }
                synchronized (mTerminalRun) {
                    try {
                        mTerminalRun.wait(10000);
                    } catch (InterruptedException ignore) {
                    }
                }
                List<DownloadTask> queuedDownloadTask = getQueuedDownloadTask();
                if (queuedDownloadTask.size() >= 1) {
                    //还有任务需要做
                    if (DEBUG) {
                        Log.d(TAG, "DownloadPipe-" + mTag + " wait to terminal fail,task siz=" + queuedDownloadTask.size());
                    }
                    return;
                }
                terminal();
            }
        };

        boolean startTask(@NonNull DownloadTask task) {
            if (!mIsStarted) {
                synchronized (this) {
                    if (!mIsStarted) {
                        mHandlerThread = new HandlerThread("DownloadPipe-" + sDownloadPipeIndex++);
                        mHandlerThread.start();
                        mHandler = new Handler(mHandlerThread.getLooper());
                        mIsStarted = true;
                        invokePipeStart();
                        if (DEBUG) {
                            Log.d(TAG, "DownloadPipe-" + mTag + " started");
                        }
                    }
                }
            }
            mDownloadTasks.add(task);
            synchronized (mTerminalRun) {
                mTerminalRun.notifyAll();
            }
            synchronized (this) {
                if (mIsTerminal) {
                    mDownloadTasks.remove(task);
                    return false;
                }
                mHandler.post(task);
                invokeTaskQueue(task);
                mHandler.removeCallbacks(mTerminalRun);
                mHandler.post(mTerminalRun);
                if (DEBUG) {
                    Log.d(TAG, "DownloadPipe-" + mTag + " startTask:" + task.getDownloadConf().getNetUrl());
                }
            }
            return true;
        }

        void cancelTask(@NonNull DownloadTask task) {
            if (mIsTerminal || !mIsStarted) {
                return;
            }
            mHandler.removeCallbacks(task);
            if (DEBUG) {
                Log.d(TAG, "DownloadPipe-" + mTag + " cancelTask" + task.getDownloadConf().getNetUrl());
            }
        }

        void taskEnd(@NonNull DownloadTask task) {
            mDownloadTasks.remove(task);
            invokeTaskComplete(task);
        }

        /**
         * 终止该下载线程<br/>
         * 调用后，该条线上没有完成的下载任务会被取消，线程随后退出<br/>
         * 注意:如果该条线程超过10秒钟还没有接收到下载任务，则该线程会自动退出<br/>
         */
        public void terminal() {
            if (mIsStarted) {
                synchronized (this) {
                    for (DownloadTask task : getQueuedDownloadTask()) {
                        //取消没开始的任务
                        task.cancel();
                    }
                    if (mHandler != null) {
                        mHandler.removeCallbacksAndMessages(null);
                    }
                    if (mHandlerThread != null) {
                        mHandlerThread.quit();
                    }
                    mIsTerminal = true;
                    DownloadManage.instance().clearTerminalDownloadPipe();
                    invokePipeTerminal();
                    if (DEBUG) {
                        Log.d(TAG, "DownloadPipe-" + mTag + " terminal,remain task size=" + getQueuedDownloadTask().size());
                    }
                }
            }
        }

        public String getTag() {
            return mTag;
        }

        /**
         * 线程是否被启动了
         *
         * @return true表示被启动过了
         */
        public boolean isStarted() {
            return mIsStarted;
        }

        /**
         * 线程是否被终止了
         *
         * @return true表示已经被终止了
         * 注意:如果返回true，使用该线程下载都会回调失败
         */
        public boolean isTerminal() {
            return mIsTerminal;
        }

        /**
         * 获取该线程还未执行过的下载任务
         *
         * @return 下载任务集合
         */
        @NonNull
        public List<DownloadTask> getQueuedDownloadTask() {
            return new ArrayList<>(mDownloadTasks);
        }

        public void addDownloadPipeStateListener(DownloadPipeStateListener listener) {
            if (listener != null) {
                mPipeListeners.add(listener);
            }
        }

        public void removeDownloadPipeStateListener(DownloadPipeStateListener listener) {
            if (listener != null) {
                mPipeListeners.remove(listener);
            }
        }

        void invokeTaskQueue(DownloadTask task) {
            for (DownloadPipeStateListener downloadPipeStateListener : new ArrayList<>(mPipeListeners)) {
                downloadPipeStateListener.onTaskQueue(task);
            }
        }

        void invokeTaskRun(DownloadTask task) {
            for (DownloadPipeStateListener downloadPipeStateListener : new ArrayList<>(mPipeListeners)) {
                downloadPipeStateListener.onTaskRun(task);
            }
        }

        void invokeTaskComplete(DownloadTask task) {
            for (DownloadPipeStateListener downloadPipeStateListener : new ArrayList<>(mPipeListeners)) {
                downloadPipeStateListener.onTaskComplete(task);
            }
        }

        void invokePipeStart() {
            for (DownloadPipeStateListener downloadPipeStateListener : new ArrayList<>(mPipeListeners)) {
                downloadPipeStateListener.onPipeStarted(this);
            }
        }

        void invokePipeTerminal() {
            for (DownloadPipeStateListener downloadPipeStateListener : new ArrayList<>(mPipeListeners)) {
                downloadPipeStateListener.onPipeTerminal(this);
            }
        }

        public interface DownloadPipeStateListener {
            void onTaskQueue(DownloadTask task);

            void onTaskRun(DownloadTask task);

            void onTaskComplete(DownloadTask task);

            void onPipeStarted(DownloadPipe pipe);

            void onPipeTerminal(DownloadPipe pipe);
        }


    }

    public static class DownloadTask implements Runnable {
        /**
         * 任务状态码
         */
        public static final int STATE_INIT = 1;
        public static final int STATE_PREPARE = 2;
        public static final int STATE_EXECUTING = 3;
        public static final int STATE_TRANSFERRING = 4;
        public static final int STATE_SUCCEED = 5;
        public static final int STATE_FAIL = 6;
        public static final int STATE_CANCEL = 7;
        /**
         * 下载时错误码
         */
        public static final int CODE_PIPE = 1;//下载线程已经被关闭了
        public static final int CODE_URL = 2;//下载url有问题
        public static final int CODE_CREATE_FILE = 3;//创建文件失败
        public static final int CODE_CONN_RESPONSE_CODE = 4;//服务器返回了非200
        public static final int CODE_CONN = 5;//网络连接出错
        public static final int CODE_INPUT = 6;//获取网络流出错
        public static final int CODE_OUTPUT = 7;//获取文件流出错
        public static final int CODE_RECEIVE = 8;//接收文件信息是异常

        private final DownloadConf mDownloadConf;
        private final DownloadPipe mDownloadPipe;

        private boolean mCanceled = false;
        private boolean mSucceed = false;
        private boolean mComplete = false;
        private int mState = STATE_INIT;

        private long mStartTime;
        private long mEndTime;
        private long mDownloadSize = 0;

        DownloadTask(@NonNull DownloadConf downloadConf, @NonNull DownloadPipe downloadPipe) {
            this.mDownloadConf = downloadConf;
            this.mDownloadPipe = downloadPipe;
        }

        @NonNull
        public DownloadConf getDownloadConf() {
            return mDownloadConf;
        }

        @NonNull
        public DownloadPipe getDownloadPipe() {
            return mDownloadPipe;
        }

        /**
         * 开启下载任务
         *
         * @return 0表示启动成功, 1表示任务被取消, 2表示已经开启过了，3表示DownloadPipe被关闭了
         */
        public synchronized int start() {
            if (mCanceled) {
                //mCanceled置为false时已经回调过了
                return 1;
            }
            if (!setState(STATE_PREPARE)) {
                //已经启动过了
                return 2;
            }
            if (!this.mDownloadPipe.startTask(this)) {
                //pipe已经被终止了
                invokeFail(CODE_PIPE, "DownloadPipe has been terminaled!", null, false);
                invokeComplete();
                return 3;
            }
            return 0;
        }

        /**
         * 取消下载任务
         *
         * @return true表示取消成功, 会调用取消回调
         */
        public synchronized boolean cancel() {
            if (getState() >= STATE_SUCCEED) {
                //已经执行完成了(或则失败)了，不可取消
                return false;
            }
            if (mCanceled) {
                //已经取消过了
                return false;
            }
            this.mDownloadPipe.cancelTask(this);
            mCanceled = true;
            setState(STATE_CANCEL);
            if (getState() < STATE_EXECUTING) {
                //还没执行,执行回调
                DownloadListener downloadListener = this.mDownloadConf.getDownloadListener();
                if (downloadListener != null) {
                    downloadListener.onCancel(this);
                    invokeComplete();
                }
            }
            return true;
        }

        @IntRange(from = STATE_INIT, to = STATE_FAIL)
        public int getState() {
            return mState;
        }

        synchronized boolean setState(@IntRange(from = STATE_INIT, to = STATE_CANCEL) final int state) {
            if (this.mState >= state) {
                return false;
            }
            this.mState = state;
            return true;
        }

        public boolean isCanceled() {
            return mCanceled;
        }

        public boolean isSucceed() {
            return mSucceed;
        }

        public boolean isComplete() {
            return mComplete;
        }

        /**
         * 平均下载速度
         *
         * @return 如果没开始返回0, 单位 B/S
         */
        public long getAvgSpeed() {
            if (mStartTime <= 0) {
                //还没开始
                return 0;
            }
            if (mEndTime > 0) {
                //已经传输完成(成功或则失败)
                return mDownloadSize * 1000 / (mEndTime - mStartTime);
            }
            //传输过程中
            return mDownloadSize * 1000 / (SystemClock.uptimeMillis() - mStartTime);
        }

        @Override
        public void run() {
            if (DEBUG) {
                Log.d(TAG, "DownloadTask-" + Thread.currentThread().getName() + ":" + mDownloadConf.getNetUrl() + " run");
            }
            synchronized (this) {
                if (mCanceled) {
                    return;
                }
                invokeStart();
            }
            try {
                this.mDownloadPipe.invokeTaskRun(this);
                File file = new File(this.mDownloadConf.getFileFolder() + File.separator + this.mDownloadConf.getFileName());
                if (file.isFile() && file.exists() && !this.mDownloadConf.isForceOverride()) {
                    //文件已经存在并且不需要覆盖下载
                    invokeSucceed();
                    return;
                }
                //先删除
                file.delete();
                //创建目录
                file.getParentFile().mkdirs();
                //创建文件
                try {
                    file.createNewFile();
                } catch (IOException ignore) {
                }
                if (!file.exists()) {
                    //文件创建失败
                    invokeFail(CODE_CREATE_FILE, "create target file fail", null, true);
                    return;
                }
                if (checkCancel()) {
                    file.delete();
                    return;
                }
                URL url;
                try {
                    url = new URL(this.mDownloadConf.getNetUrl());
                } catch (Exception ignore) {
                    //url有问题
                    invokeFail(CODE_URL, "download url is illegal", ignore, true);
                    return;
                }
                HttpURLConnection conn;
                long fileSize;
                try {
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setConnectTimeout(10 * 1000);
                    conn.setRequestMethod("GET");
                    conn.setRequestProperty("Accept-Language", "zh-CN");
                    conn.setRequestProperty("Charset", "UTF-8");
                    conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Linux; Android 4.4.4; HTC D820u Build/KTU84P) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/40.0.2214.89 Mobile Safari/537.36");
                    conn.connect();
                    int responseCode = conn.getResponseCode();
                    if (responseCode != 200) {
                        //ResponseCode
                        invokeFail(CODE_CONN_RESPONSE_CODE, "responseCode(" + responseCode + ")", null, true);
                        return;
                    }
                    fileSize = conn.getContentLength();
                } catch (IOException ignore) {
                    //连接异常，如网络问题
                    invokeFail(CODE_CONN, "connect server fail,please check your network!", ignore, true);
                    return;
                }


                InputStream inputStream = null;
                OutputStream outputStream = null;
                try {
                    //获取输入流
                    try {
                        inputStream = conn.getInputStream();
                    } catch (IOException ignore) {
                        //连接成功但是无法建立流
                        invokeFail(CODE_INPUT, "request server inputstream fail", ignore, true);
                        return;
                    }

                    //获取输出流
                    try {
                        outputStream = new FileOutputStream(file);
                    } catch (IOException ignore) {
                        //file write获取失败
                        invokeFail(CODE_OUTPUT, "request target file outputstream fail", ignore, true);
                        return;
                    }

                    if (DEBUG) {
                        Log.d(TAG, "DownloadTask-" + Thread.currentThread().getName() + ":" + mDownloadConf.getNetUrl() + " 连接文件成功，开始下载数据");
                    }

                    byte[] data = new byte[4096];
                    int length;
                    long receiveLength = 0;
                    long beginTime = SystemClock.uptimeMillis();
                    boolean cancel;
                    try {
                        setState(STATE_TRANSFERRING);
                        while (!(cancel = checkCancel()) && (length = inputStream.read(data)) != -1) {
                            outputStream.write(data, 0, length);
                            receiveLength += length;
                            long currentTime = SystemClock.uptimeMillis();
                            if (currentTime - beginTime > 500 || length < 4096) {
                                //防止回调速度过快，500ms回调一次,或者读取到结尾了
                                beginTime = currentTime;
                                invokeProgress(fileSize, receiveLength);
                            }
                        }
                    } catch (IOException ignore) {
                        //传输过程中产生异常
                        invokeFail(CODE_RECEIVE, "receive file data err", ignore, true);
                        return;
                    }
                    if (!cancel) {
                        //成功
                        invokeSucceed();
                    } else {
                        //取消了->删除文件
                        file.delete();
                    }
                } finally {
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (Exception ignore) {
                        }
                    }
                    if (outputStream != null) {
                        try {
                            outputStream.close();
                        } catch (Exception ignore) {
                        }
                    }
                }
            } finally {
                invokeComplete();
                if (DEBUG) {
                    Log.d(TAG, "DownloadTask-" + mDownloadConf.getNetUrl() + " run end");
                }
            }
        }

        private boolean checkCancel() {
            if (mCanceled) {
                //取消了
                DownloadListener downloadListener = this.mDownloadConf.getDownloadListener();
                if (downloadListener != null) {
                    downloadListener.onCancel(this);
                }
                return true;
            }
            return false;
        }

        private void invokeStart() {
            setState(STATE_EXECUTING);
            mStartTime = SystemClock.uptimeMillis();
            DownloadListener downloadListener = this.mDownloadConf.getDownloadListener();
            if (downloadListener != null) {
                downloadListener.onStart(this);
            }
        }

        private void invokeFail(int code, String msg, Throwable throwable, boolean deleteFile) {
            setState(STATE_FAIL);
            if (deleteFile) {
                new File(this.mDownloadConf.getFileFolder() + File.separator + this.mDownloadConf.getFileName()).delete();
            }
            DownloadListener downloadListener = this.mDownloadConf.getDownloadListener();
            if (downloadListener != null) {
                downloadListener.onFail(this, code, msg, throwable);
            }
        }

        private void invokeProgress(long total, long current) {
            mDownloadSize = current;
            DownloadListener downloadListener = this.mDownloadConf.getDownloadListener();
            if (downloadListener != null) {
                downloadListener.onProgress(this, total, current);
            }
            if (DEBUG) {
                Log.d(TAG, "DownloadTask-" + Thread.currentThread().getName() + " progress" + (current * 100 / total));
            }
        }

        private void invokeSucceed() {
            mSucceed = true;
            setState(STATE_SUCCEED);
            DownloadListener downloadListener = this.mDownloadConf.getDownloadListener();
            if (downloadListener != null) {
                downloadListener.onSucceed(this);
            }
        }

        private void invokeComplete() {
            mComplete = true;
            mEndTime = SystemClock.uptimeMillis();
            DownloadListener downloadListener = this.mDownloadConf.getDownloadListener();
            if (downloadListener != null) {
                downloadListener.onComplete(this, mSucceed);
            }
            //从DownloadPipe任务列表中清除该任务
            this.mDownloadPipe.taskEnd(this);
        }
    }

    public static class DownloadConf {
        private final String mNetUrl;
        private final String mFileFolder;
        private final String mFileName;
        private final boolean mForceOverride;
        private DownloadListener mDownloadListener;

        /**
         * @param netUrl           下载地址
         * @param fileFolder       下载文件路径
         * @param fileName         下载文件名
         * @param forceOverride    是否覆盖下载，如果为true,即使文件存在也会重新下载
         * @param downloadListener 下载事件监听,建议使用{@link DownloadListener.DownloadListenerWrapper}
         */
        public DownloadConf(@NonNull String netUrl, @NonNull String fileFolder, @NonNull String fileName, boolean forceOverride, DownloadListener downloadListener) {
            this.mNetUrl = netUrl;
            this.mFileFolder = fileFolder;
            this.mFileName = fileName;
            this.mForceOverride = forceOverride;
            this.mDownloadListener = downloadListener;
        }

        @NonNull
        public String getNetUrl() {
            return mNetUrl;
        }

        @NonNull
        public String getFileFolder() {
            return mFileFolder;
        }

        @NonNull
        public String getFileName() {
            return mFileName;
        }

        public boolean isForceOverride() {
            return mForceOverride;
        }

        public DownloadListener getDownloadListener() {
            return mDownloadListener;
        }

        public void setDownloadListener(DownloadListener mDownloadListener) {
            this.mDownloadListener = mDownloadListener;
        }
    }
}
