package com.rongtuoyouxuan.qfcommon.webview.web;

import android.app.AlertDialog;
import android.net.Uri;
import android.net.http.SslError;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.rongtuoyouxuan.qfcommon.R;

/**
 * 建议配合AgentWebview使用
 */

public class CustomWebViewClient extends com.just.agentweb.WebViewClient {

    /**
     * *shouldOverrideUrlLoading不一定每次都被调用，只有需要的时候才会被调用。
     * 比如，一开始页面加载时（没有重定向）不调用，reload不调用，返回上一页面不调用
     * <p>
     * 返回值是true的时候控制去WebView拦截并处理url,如果返回false 不拦截url!!!
     */

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        return handleWebViewCustomUri(Uri.parse(url));
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        return handleWebViewCustomUri(request.getUrl());
    }

    private boolean handleWebViewCustomUri(Uri url) {
        LogUtils.e("handleWebViewCustomUri->" + url);
        return false;
    }

    /**
     * 支持https
     * 如果GooglePlay 平台发出警告
     * 按当前网址https://www.jianshu.com/p/d3f5b5effca9  修改即可。
     */
    @Override
    public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
        AlertDialog.Builder builder = new AlertDialog.Builder(webView.getContext());
        builder.setMessage(StringUtils.getString(R.string.chat_web_view_ssl));
        builder.setPositiveButton(StringUtils.getString(R.string.login_ok), (dialog, which) -> {
            sslErrorHandler.proceed();// 接受所有网站的证书
        });

        builder.setNegativeButton(StringUtils.getString(R.string.login_cancel), (dialog, which) ->
                sslErrorHandler.cancel());
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        //   view.loadUrl("about:blank");
        super.onReceivedError(view, request, error);
    }

    //页面加载完成调用 但是走onReceivedError 后也会走这个方法 ！ 如果想用加载成功监听 用个标识做限制即可
    @Override
    public void onPageFinished(WebView view, String url) {
        LogUtils.e("CustomWebViewClient->onPageFinished");
        super.onPageFinished(view, url);
    }
}