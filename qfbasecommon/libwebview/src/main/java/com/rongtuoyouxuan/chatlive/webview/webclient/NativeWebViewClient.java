package com.rongtuoyouxuan.chatlive.webview.webclient;

import android.app.AlertDialog;
import android.net.http.SslError;
import android.webkit.CookieSyncManager;
import android.webkit.HttpAuthHandler;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;

import com.blankj.utilcode.util.StringUtils;
import com.rongtuoyouxuan.chatlive.webview.R;

import webview._WebViewClient;

public class NativeWebViewClient extends _WebViewClient {

    private WebviewClientListener listener;

    public NativeWebViewClient(WebviewClientListener listener) {
        super();
        this.listener = listener;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if (listener != null) {
            return listener.shouldOverrideUrlLoading(view, url);
        }
        return false;
    }

    @Override
    public void onReceivedHttpAuthRequest(WebView view,
                                          final HttpAuthHandler handler, String host, String realm) {
    }

    @Override
    public void onReceivedSslError(final WebView view, final SslErrorHandler handler, final SslError error) {
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setMessage(StringUtils.getString(R.string.chat_web_view_ssl));
        builder.setPositiveButton(StringUtils.getString(R.string.login_ok), (dialog, which) -> {
            handler.proceed();// 接受所有网站的证书
        });

        builder.setNegativeButton(StringUtils.getString(R.string.login_cancel), (dialog, which) ->
                handler.cancel());
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onReceivedError(WebView view, int errorCode,
                                String description, String failingUrl) {
        if (errorCode == ERROR_UNKNOWN || errorCode == ERROR_HOST_LOOKUP
                || errorCode == ERROR_PROXY_AUTHENTICATION
                || errorCode == ERROR_CONNECT || errorCode == ERROR_IO
                || errorCode == ERROR_TIMEOUT
                || errorCode == ERROR_UNSUPPORTED_SCHEME
                || errorCode == ERROR_BAD_URL) {
            if (listener != null)
                listener.showWebviewError(failingUrl);
        }
        super.onReceivedError(view, errorCode, description, failingUrl);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        CookieSyncManager.getInstance().sync();
        if (listener != null)
            listener.onPageFinished(url);
        super.onPageFinished(view, url);
    }
}
