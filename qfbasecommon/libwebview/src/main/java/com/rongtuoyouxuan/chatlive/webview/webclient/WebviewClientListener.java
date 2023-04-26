package com.rongtuoyouxuan.chatlive.webview.webclient;

import android.webkit.WebView;

public interface WebviewClientListener {
    boolean shouldOverrideUrlLoading(WebView view, String url);
    void showWebviewError(String failingUrl);

    void onPageFinished(String url);

    boolean onProceedSslError(String url);
}
