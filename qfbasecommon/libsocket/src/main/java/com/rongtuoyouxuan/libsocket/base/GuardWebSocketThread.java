package com.rongtuoyouxuan.libsocket.base;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Network;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import com.rongtuoyouxuan.chatlive.log.upload.ULog;
import com.rongtuoyouxuan.chatlive.util.MyLifecycleHandler;
import com.rongtuoyouxuan.chatlive.util.NetworkUtil;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * WebSocket 守护线程
 * <p>
 * Created by mah on 2019-12-12.
 */
public class GuardWebSocketThread {
    private final static String TAG = "GuardWebSocketThread";
    private static GuardWebSocketThread instance;
    private final static int INTERVAL_MS = 1000 * 30;

    private final AtomicBoolean isStart = new AtomicBoolean(false);
    private final Handler uiHandler = new Handler(Looper.getMainLooper());
    private @NonNull GuardWebSocketHandler guardHandler;
    public Context mContext;

    private boolean isForeground = true;

    private MyLifecycleHandler.Listener appLifecycleListener = new MyLifecycleHandler.Listener() {

        @Override
        public void onBecameForeground(Activity activity) {
            isForeground = true;
            checkWebSocketStatus();
        }

        @Override
        public void onBecameBackground(Activity activity) {
            isForeground = false;
        }
    };

    public static GuardWebSocketThread instance() {
        synchronized (IMSocketBase.class) {
            if (instance == null) {
                instance = new GuardWebSocketThread();
            }
        }
        return instance;
    }

    private GuardWebSocketThread() {
        this.mContext = IMSocketBase.instance().mContext;
        init();
    }

    private void init() {
        HandlerThread ht = new HandlerThread(TAG);
        ht.start();
        guardHandler = new GuardWebSocketHandler(ht.getLooper());
        listenerNetworkState(mContext);
        MyLifecycleHandler.getInstance().removeListener(appLifecycleListener);
        MyLifecycleHandler.getInstance().addListener(appLifecycleListener);
    }

    public synchronized void start() {
        if (isStart.get()) {
            ULog.d(TAG, "GuardWebSocketThread is already start");
            return;
        }
        isStart.set(true);
        delayedCheck();
    }

    private void delayedCheck() {
        guardHandler.removeCallbacksAndMessages(null);
        guardHandler.sendEmptyMessageDelayed(0, INTERVAL_MS);
    }

    private void checkWebSocketStatus() {
        ULog.d(TAG, "GuardWebSocketThread guard.....");
        uiHandler.post(new Runnable() {
            @Override
            public void run() {
                if (!IMSocketBase.instance().isConnected()
                        && !IMSocketBase.instance().isRetrying.get()
                        && NetworkUtil.isNetConnected(mContext)
                        && isCanRetryWhileBackground()) {
                    ULog.i(TAG, "checkWebSocketStatus retryFetchToken");
                    IMSocketBase.instance().retryFetchToken();
                }
            }
        });
        delayedCheck();
    }

    /**
     * 判断当前条件是否可以重连
     * <p>
     * 前台 ： 可以重连
     * 后台 + 重连开关：开 ： 可以重连
     * 后台 + 重连开关：关 ： 不可以重连
     */
    private boolean isCanRetryWhileBackground() {
        return isForeground || IMSocketBase.instance().isEnableBackgroundRetry;
    }

    /**
     * 非主线程
     */
    private void onNetworkAvailable() {
        checkWebSocketStatus();
    }

    /**
     * 非主线程
     */
    private void onNetworkLost() {
        uiHandler.post(new Runnable() {
            @Override
            public void run() {
                IMSocketBase.instance().lostNetWork();
            }
        });
    }

    private void listenerNetworkState(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                connectivityManager.registerDefaultNetworkCallback(new ConnectivityManager.NetworkCallback() {

                    @Override
                    public void onAvailable(Network network) {
                        super.onAvailable(network);
                        ULog.e(TAG, "network onAvailable Build.VERSION>=N");
                        GuardWebSocketThread.instance().onNetworkAvailable();
                    }

                    @Override
                    public void onLost(Network network) {
                        super.onLost(network);
                        ULog.e(TAG, "network onLost Build.VERSION>=N");
                        GuardWebSocketThread.instance().onNetworkLost();
                    }
                });
            } else {
                IntentFilter intentFilter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
                context.registerReceiver(new NetworkChangeReceiver(context), intentFilter);
            }
        }
    }

    class NetworkChangeReceiver extends BroadcastReceiver {
        private final Context context;

        NetworkChangeReceiver(Context context) {
            this.context = context;
        }

        @Override
        public void onReceive(final Context context, final Intent intent) {
            boolean netWorkIsConnected = NetworkUtil.netWorkIsConnected(context);
            if ("android.net.conn.CONNECTIVITY_CHANGE".equals(intent.getAction())) {
                if (netWorkIsConnected) {
                    ULog.e(TAG, "network onAvailable");
                    GuardWebSocketThread.instance().onNetworkAvailable();
                } else {
                    ULog.e(TAG, "network onLost");
                    GuardWebSocketThread.instance().onNetworkLost();
                }
            }
        }
    }

    class GuardWebSocketHandler extends Handler {
        private GuardWebSocketHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            GuardWebSocketThread.instance().checkWebSocketStatus();
        }
    }
}
