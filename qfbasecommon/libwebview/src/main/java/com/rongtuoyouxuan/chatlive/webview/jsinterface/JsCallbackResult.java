package com.rongtuoyouxuan.chatlive.webview.jsinterface;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.webkit.JavascriptInterface;

import com.rongtuoyouxuan.chatlive.log.PLog;
import com.rongtuoyouxuan.chatlive.util.NetworkUtil;

public class JsCallbackResult {//implements SelectPhotoMenu.Observer {
    private static final String TAG = "JsCallbackResult";
    private Context context;
    private WebviewListener mListener;

    public JsCallbackResult() {
    }

    public void setListener(Context context, WebviewListener listener) {
        this.context = context;
        this.mListener = listener;
    }

    public boolean canInject() {
        return true;
    }

    @JavascriptInterface
    public String isNetConnected() {
        if (NetworkUtil.isNetConnected(context))
            return "true";
        else
            return "false";
    }

    @JavascriptInterface
    public void login(final String text) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                mListener.loginSuccess(text);
            }
        });
    }

    @JavascriptInterface
    public void payComplete(final String text) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                mListener.payComplete(text);
            }
        });
    }

    @JavascriptInterface
    public void registerSucc(final String text) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                mListener.registerSucc(text);
            }
        });
    }

    @JavascriptInterface
    public String isWifi() {
        boolean wifi = NetworkUtil.isWifiConnected(context);
        return wifi ? "true" : "false";
    }

    @JavascriptInterface
    public void close() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                mListener.close();
            }
        });
    }

    @JavascriptInterface
    public void openUrl(final String url) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                mListener.openUrl(url);
            }
        });
    }

    @JavascriptInterface
    public void share(final String url, final String shareContext, final String shareUrl,
                      final String shareTitle) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                mListener.shareBottomDialog(url, shareContext, shareUrl, shareTitle);
            }
        });
    }

    @JavascriptInterface
    public void showShareIcon(final boolean isVisibility, final String url, final String shareContext,
                              final String shareUrl, final String shareTitle) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                mListener.showShareIcon(isVisibility, url, shareContext, shareUrl, shareTitle);
            }
        });
    }

    @JavascriptInterface
    public void showPreviewBtn(final String carID) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                mListener.showPreviewBtn(carID);
            }
        });
    }

    @JavascriptInterface
    public void showQuestionIcon(final String url) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                mListener.showQuestionIcon(url);
            }
        });
    }

    @JavascriptInterface
    public void BLlogout(String s) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                mListener.logout();
            }
        });
    }

    @JavascriptInterface
    public void showOpenGuard(final String uid) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                mListener.showOpenGuard(uid);
            }
        });
    }

    /**
     * 显示隐藏toolbar
     *
     * @param s 1显示 0隐藏
     */
    @JavascriptInterface
    public void Toolbar(final String s) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                if ("0".equals(s)) {
                    mListener.hideToolbar();
                } else {
                    mListener.showToolbar();
                }
            }
        });
    }

    /**
     * 打开礼物面板
     *
     * @param id 礼物id
     */
    @JavascriptInterface
    public void openGiftDialog(final String id) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                mListener.openGiftDialog(id);
            }
        });
    }

    /**
     * h5游戏充值
     */
    @JavascriptInterface
    public void XGPay() {
        PLog.d(TAG, "XGPay");
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                mListener.XGPay();
            }
        });
    }

    /**
     * h5游戏内关闭游戏
     */
    @JavascriptInterface
    public void XGGameClose() {
        PLog.d(TAG, "XGGameClose");
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                mListener.XGGameClose();
            }
        });
    }

    /**
     * webview 中新打开一个WebViewActivity
     */
    @JavascriptInterface
    public void openAppH5(final String url, final boolean addCommonParam) {
        PLog.d(TAG, "openAppH5");
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                mListener.openAppH5(url, addCommonParam);
            }
        });
    }
}