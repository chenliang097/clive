package com.rongtuoyouxuan.chatlive.webview;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;

import com.rongtuoyouxuan.chatlive.log.PLog;
import com.rongtuoyouxuan.chatlive.net2.BaseNetImpl;
import com.rongtuoyouxuan.chatlive.util.EnvUtils;
import com.rongtuoyouxuan.chatlive.util.SysUtil;
import com.rongtuoyouxuan.chatlive.webview.jsinterface.InjdectJs;
import com.rongtuoyouxuan.chatlive.webview.jsinterface.WebviewListener;
import com.rongtuoyouxuan.chatlive.webview.webclient.NativeWebViewClient;
import com.rongtuoyouxuan.chatlive.webview.webclient.WebviewClientListener;
import com.rongtuoyouxuan.libuikit.CommonLoadErrorLayout;

import java.net.URL;
import java.util.Map;

import webview._chromewebclient;

public class WebViewWrapper extends FrameLayout implements WebviewClientListener, WebviewListener, View.OnClickListener {
    public static final int REQUEST_SELECT_FILE = 100;
    public final static String CHOOSER_TITLE = "选择图片";
    public final static String SUPPORTED_MIME_TYPE = "image/*,video/*";
    private final static int FILECHOOSER_RESULTCODE = 1;
    private final String TAG = "WebViewWrapper";
    public ValueCallback<Uri[]> uploadMessage;
    protected Context mContext;
    private String mUrl;
    private WebView mWebView;
    private View mLayoutLoading;
    private View mLayoutError;
    private boolean mLoadUrlError = false;
    private Listener mListener;
    private WebviewClientListener mWebviewClientListener;
    private LoginListener mLoginListener;
    private PayListener mPayListener;
    private IShareBottomDialogListener mShareBottomDialogListener;
    private ShowPreviewListener mShowPreviewListener;
    private ShowQuestionIconListener mShowQuestionIconListener;
    private LogoutListener mLogoutListener;
    private IsShowToolbarListener mIsShowToolbarListener;
    private ShowOpenGuardListener mShowOpenGuardListener;
    private OpenGiftDialogListener mOpenGiftDialogListener;
    private H5GameListener h5GameListener;
    private BaseWebViewListener baseWebViewListener;
    private ValueCallback<Uri> mUploadMessage;

    public WebViewWrapper(Context context) {
        super(context);
        init(context);
    }

    public WebViewWrapper(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public WebViewWrapper(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mContext = context;

        if (isInEditMode()) {
            return;
        }

        LayoutInflater.from(mContext).inflate(R.layout.pl_libwebview_layout_webview, this, true);

        mLayoutLoading = findViewById(R.id.web_view_wrapper_layout_loading);
        mLayoutError = findViewById(R.id.web_view_wrapper_layout_load_error);
        mWebView = findViewById(R.id.web_view_wrapper_nativeWebView);
        mLayoutLoading.setVisibility(View.VISIBLE);
        mWebView.setVisibility(View.INVISIBLE);
        mLayoutError.setVisibility(GONE);

        ((CommonLoadErrorLayout) mLayoutError).setErrorIconMode(CommonLoadErrorLayout.WHITE);

        mWebView.setWebChromeClient(new FileUploadChromeClient());
        mWebView.setWebViewClient(new NativeWebViewClient(this));
        WebSettings settings = mWebView.getSettings();
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setTextZoom(100);
        mWebView.setBackgroundColor(0);
//        setWebViewCookies();
        mWebView.loadUrl(mUrl);
        mLayoutError.setOnClickListener(this);
        mLayoutLoading.setOnClickListener(this);
        mWebView.setWebContentsDebuggingEnabled(true);
    }

    public WebView getmWebView() {
        return mWebView;
    }

    private void removeWebviewCookie() {
        CookieSyncManager.createInstance(mContext);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
        CookieSyncManager.getInstance().sync();
    }

    public void setWebviewClientListener(WebviewClientListener webviewClientListener) {
        mWebviewClientListener = webviewClientListener;
    }

    public void setListener(Listener listener) {
        mListener = listener;
    }

    public void setLoginListener(LoginListener loginListener) {
        mLoginListener = loginListener;

    }

    public void setPayListener(PayListener payListener) {
        mPayListener = payListener;
    }

    public void setBottomDialogListener(IShareBottomDialogListener shareBottomDialogListener) {
        mShareBottomDialogListener = shareBottomDialogListener;
    }

    public void setShowPreviewListener(ShowPreviewListener mshowPreviewListener) {
        mShowPreviewListener = mshowPreviewListener;
    }

    public void setShowQuestionIconListener(ShowQuestionIconListener mshowQuestionIconListener) {
        mShowQuestionIconListener = mshowQuestionIconListener;
    }

    public void setLogoutListener(LogoutListener logoutListener) {
        mLogoutListener = logoutListener;
    }

    public void setIsShowToolbarListener(IsShowToolbarListener showToolbarListener) {
        mIsShowToolbarListener = showToolbarListener;
    }

    public void setShowOpenGuardListener(ShowOpenGuardListener showOpenGuardListener) {
        mShowOpenGuardListener = showOpenGuardListener;
    }

    public void setOpenGiftDialogListener(OpenGiftDialogListener openGiftDialogListener) {
        mOpenGiftDialogListener = openGiftDialogListener;
    }

    public void setH5GameListener(H5GameListener h5GameListener) {
        this.h5GameListener = h5GameListener;
    }

    public void setBaseWebViewListener(BaseWebViewListener baseWebViewListener) {
        this.baseWebViewListener = baseWebViewListener;
    }

//    public void setWebViewCookies() {
//        if (mContext != null) {
//            CookieSyncManager.createInstance(mContext);
//            CookieManager cookieManager = CookieManager.getInstance();
//            cookieManager.setAcceptCookie(true);
//            String domain = String.format(";domain=%s;path=%s", EnvUtils.API.getDomain(), "/");
//            List<Cookie> cookies = BaseNetImpl.getInstance().getCookies();
//            for (Cookie cookie : cookies) {
//                cookieManager.setCookie(EnvUtils.API.getDomain(), cookie.name() + "=" + cookie.value() + domain);
//            }
//            CookieSyncManager.getInstance().sync();
//        }
//    }

    public void loadurl(String url) {
        loadurl(url, false);
    }

    public void loadurl(String url, boolean addCommonParams) {
        mUrl = url;
        String host = "";
        boolean hasQuery = false;
        try {
            URL urlWrap = new URL(url);
            host = urlWrap.getHost();
            hasQuery = !TextUtils.isEmpty(urlWrap.getQuery());
        } catch (Exception e) {

        }
        if (host.endsWith(EnvUtils.API.getDomain()) || isDebug(url) || isGameDomain(url)) {
            try {
                InjdectJs.InjectAllJsNode(mWebView, this);
            } catch (Exception e) {
                PLog.e(TAG, e);
            }

            if (addCommonParams) {
                String div = hasQuery ? "&" : "?";

                Map<String, String> params = BaseNetImpl.getInstance().getUniversalParams();
                StringBuilder urlParam = new StringBuilder();
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
                    urlParam.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
                }

                url += div + urlParam.toString();
            }
        }
        mWebView.loadUrl(url);
    }

    private boolean isGameDomain(String url) {
        if (url == null) return false;
        String host = Uri.parse(url).getHost();
        return host != null && host.endsWith("leadercc.com");
    }

    private boolean isDebug(String url) {
        return PLog.isDebugMode() && "file:///android_asset/html/api.html".equals(url);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.web_view_wrapper_layout_load_error) {
            mLoadUrlError = false;
            mWebView.setVisibility(View.INVISIBLE);
            mLayoutError.setVisibility(View.GONE);
            mLayoutLoading.setVisibility(View.VISIBLE);
            mWebView.reload();
        }
    }

    private void injectJs(WebView view, String url) {
        String host = "";
        try {
            URL urlWrap = new URL(url);
            host = urlWrap.getHost();
        } catch (Exception e) {
        }

        if (host.endsWith(EnvUtils.API.getDomain())) {
            try {
                InjdectJs.InjectAllJsNode(view, this);
            } catch (Exception e) {
                PLog.e(TAG, e);
            }
        }
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        injectJs(view, url);
        if (mWebviewClientListener != null) {
            mWebviewClientListener.shouldOverrideUrlLoading(view, url);
        }
        try {
            Uri uri = Uri.parse(url);
            String path = uri.getPath();
            if (path != null) {
                if (path.endsWith(".apk") || path.endsWith(".APK")) {
                    final String apkUrl = url;
                    post(new Runnable() {
                        @Override
                        public void run() {
                            SysUtil.openSystemBrowser((Activity) mContext, apkUrl);
                        }
                    });
                    return true;
                }
            }

            String scheme = uri.getScheme();
            if (!TextUtils.isEmpty(scheme)) {
                if (scheme.equals(EnvUtils.getSchema())) {
                    mContext.startActivity(new Intent(Intent.ACTION_VIEW, uri));
                    return true;
                } else if (scheme.equals("mailto")) {
                    mContext.startActivity(new Intent(Intent.ACTION_SENDTO, uri));
                    return true;
                }
            }
        } catch (Exception e) {

        }
        return false;
    }

    /**
     * 调用js方法
     *
     * @param method 方法名字
     * @param param  参数、都为String类型
     */
    public void invokeJs(final String method, String... param) {
        String jsMethodStr = genInvokeJs(method, param);
        PLog.d(TAG, "jsMethodStr ：" + jsMethodStr);
        mWebView.evaluateJavascript(jsMethodStr, new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String value) {
                PLog.d(TAG, "onReceiveValue ：" + value);
            }
        });
    }

    private String genInvokeJs(String methodName, String... param) {
        StringBuilder sb = new StringBuilder();
        if (param != null) {
            for (String s : param) {
                sb.append("'").append(s).append("'");
            }
        }
        return "javascript:" + methodName + "(" + sb.toString() + ")";
    }

    @Override
    public void showWebviewError(String failingUrl) {
        mLayoutError.setVisibility(View.VISIBLE);
        mLayoutLoading.setVisibility(View.GONE);
        mWebView.setVisibility(View.INVISIBLE);
        mLoadUrlError = true;
        if (mWebviewClientListener != null) {
            mWebviewClientListener.showWebviewError(failingUrl);
        }
    }

    @Override
    public void onPageFinished(String url) {
        if (mLoadUrlError) {
            mLayoutError.setVisibility(View.VISIBLE);
            mWebView.setVisibility(View.INVISIBLE);
            mLayoutLoading.setVisibility(View.GONE);
        } else {
            mWebView.setVisibility(View.VISIBLE);
            mLayoutError.setVisibility(View.GONE);
            mLayoutLoading.setVisibility(View.GONE);
        }
        if (mWebviewClientListener != null) {
            mWebviewClientListener.onPageFinished(url);
        }

    }

    @Override
    public boolean onProceedSslError(String url) {
        if (mWebviewClientListener != null) {
            mWebviewClientListener.onProceedSslError(url);
        }
        return true;
    }

    @Override
    public void loginSuccess(String text) {
        Log.e(TAG, "loginSuccess: " + text);
        if (mLoginListener != null) {
            mLoginListener.onLoginSuccess(text);
        }
    }

    @Override
    public void registerSucc(final String text) {
    }

    @Override
    public void openUrl(String url) {
        if (mListener != null) {
            mListener.openUrl(url);
        }
    }

    @Override
    public void close() {
        if (mListener != null) {
            mListener.onClose();
        }
    }

    @Override
    public void showOpenGuard(String uid) {
        if (mShowOpenGuardListener != null) {
            mShowOpenGuardListener.showOpenGuard(uid);
        }
    }

    @Override
    public void openGiftDialog(String id) {
        if (mOpenGiftDialogListener != null) {
            mOpenGiftDialogListener.openGiftDialog(id);
        }
    }

    /**
     * 支付完成
     *
     * @param text
     */
    @Override
    public void payComplete(String text) {
        Log.e(TAG, "payComplete: " + text);
        if (mPayListener != null) {
            mPayListener.onPayComplete(text);
        }
    }

    @Override
    public void shareBottomDialog(String url, String shareContext, String shareUrl, String shareTitle) {
        if (mShareBottomDialogListener != null) {
            mShareBottomDialogListener.onShareBottomDialog(url, shareContext, shareUrl, shareTitle);
        }
    }

    @Override
    public void showShareIcon(boolean isVisibility, String url, String shareContext, String shareUrl, String shareTitle) {
        if (mShareBottomDialogListener != null) {
            mShareBottomDialogListener.showShareIcon(isVisibility, url, shareContext, shareUrl, shareTitle);
        }
    }

    @Override
    public void showPreviewBtn(String carId) {
        if (mShowPreviewListener != null) {
            mShowPreviewListener.onPreview(carId);
        }
    }

    @Override
    public void showQuestionIcon(String url) {
        if (mShowQuestionIconListener != null) {
            mShowQuestionIconListener.showQuestionIcon(url);
        }
    }

    @Override
    public void logout() {
        if (mLogoutListener != null) {
            mLogoutListener.logout();
        }
    }

    @Override
    public void showToolbar() {
        if (mIsShowToolbarListener != null) {
            mIsShowToolbarListener.showToolbar();
        }
    }

    @Override
    public void hideToolbar() {
        if (mIsShowToolbarListener != null) {
            mIsShowToolbarListener.hideToolbar();
        }
    }

    @Override
    public void XGPay() {
        if (h5GameListener != null) {
            h5GameListener.XGPay();
        }
    }

    @Override
    public void XGGameClose() {
        if (h5GameListener != null) {
            h5GameListener.XGGameClose();
        }
    }

    @Override
    public void openAppH5(String url, boolean addCommonParam) {
        if (baseWebViewListener != null) {
            baseWebViewListener.openAppH5(url, addCommonParam);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (requestCode == REQUEST_SELECT_FILE) {
                if (uploadMessage == null) {
                    return;
                }
                uploadMessage.onReceiveValue(WebChromeClient.FileChooserParams.parseResult(resultCode, data));
                uploadMessage = null;
            }
        } else if (requestCode == FILECHOOSER_RESULTCODE) {
            if (null == mUploadMessage) {
                return;
            }
            // Use WebDetailActivity.RESULT_OK if you're implementing WebView inside Fragment
            // Use RESULT_OK only if you're implementing WebView inside an Activity
            Uri result = data == null || resultCode != Activity.RESULT_OK ? null : data.getData();
            mUploadMessage.onReceiveValue(result);
            mUploadMessage = null;
        } else {
            // Failed to Upload Image
        }
    }

    public interface Listener {

        void onClose();

        /**
         * WebView中的超链接,点击跳转到手机浏览器中打开
         */
        void openUrl(String url);


        /**
         * Notify the host application of a change in the document title.
         *
         * @param title A String containing the new title of the document.
         */
        void onReceivedTitle(String title);

    }

    public interface LoginListener {
        void onLoginSuccess(String text);
    }

    public interface PayListener {
        void onPayComplete(String text);
    }

    public interface ShowPreviewListener {
        void onPreview(String text);
    }

    public interface ShowQuestionIconListener {
        void showQuestionIcon(String url);
    }

    public interface IShareBottomDialogListener {
        void onShareBottomDialog(String url, String shareContext, String shareUrl, String shareTitle);

        void showShareIcon(boolean isVisibility, String url, String shareContext, String shareUrl, String shareTitle);
    }

    public interface LogoutListener {
        void logout();
    }
    public interface IsShowToolbarListener {
        void showToolbar();

        void hideToolbar();
    }
    public interface ShowOpenGuardListener {
        void showOpenGuard(String uid);
    }
    public interface OpenGiftDialogListener {
        void openGiftDialog(String id);
    }
    public interface H5GameListener {
        /**
         * h5游戏充值
         */
        void XGPay();

        /**
         * h5游戏内关闭游戏
         */
        void XGGameClose();
    }
    public interface BaseWebViewListener {
        /**
         * webview 中新打开一个WebViewActivity
         */
        void openAppH5(String url, boolean addCommonParam);
    }

    public class FileUploadChromeClient extends _chromewebclient {
        // For 3.0+ Devices (Start)
        // onActivityResult attached before constructor
        protected void openFileChooser(ValueCallback uploadMsg, String acceptType) {
            mUploadMessage = uploadMsg;
            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.setType(SUPPORTED_MIME_TYPE);
            ((Activity) mContext).startActivityForResult(Intent.createChooser(i, CHOOSER_TITLE), FILECHOOSER_RESULTCODE);
        }


        // For Lollipop 5.0+ Devices
        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
            if (uploadMessage != null) {
                uploadMessage.onReceiveValue(null);
                uploadMessage = null;
            }

            uploadMessage = filePathCallback;

            Intent intent = fileChooserParams.createIntent();
            try {
                ((Activity) mContext).startActivityForResult(intent, REQUEST_SELECT_FILE);
            } catch (ActivityNotFoundException e) {
                uploadMessage = null;
                // Cannot Open File Chooser
                return false;
            }
            return true;
        }

        //For Android 4.1 only
        protected void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
            mUploadMessage = uploadMsg;
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType(SUPPORTED_MIME_TYPE);
            ((Activity) mContext).startActivityForResult(Intent.createChooser(intent, CHOOSER_TITLE), FILECHOOSER_RESULTCODE);
        }

        protected void openFileChooser(ValueCallback<Uri> uploadMsg) {
            mUploadMessage = uploadMsg;
            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.addCategory(Intent.CATEGORY_OPENABLE);
            i.setType(SUPPORTED_MIME_TYPE);
            ((Activity) mContext).startActivityForResult(Intent.createChooser(i, CHOOSER_TITLE), FILECHOOSER_RESULTCODE);
        }

        @Override
        public void onReceivedTitle(final WebView view, String title) {
            super.onReceivedTitle(view, title);
            //这里有可能会先设置一次和URL相同的title
            //webview控件在Android6.0上有一个bug,那就是onReceivedTitle()会调用两次,一次为网页的url,一次为网页真正的title,故这里需要做一个过滤
            if (!mUrl.equalsIgnoreCase(title)) {
                if (null != mListener) {
                    mListener.onReceivedTitle(title);
                }
            }
        }
    }
}