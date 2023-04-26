package webview;

import cn.qihoo.safewebview.R;
import utils.AdPattern;
import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Build;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

@SuppressLint("NewApi")
public class _WebViewClient extends WebViewClient {

    private String onloadUrl = null;
    private int AdFilters=0;

    // Bug 66568 - 【广告过滤】一个页面有时广告过滤的toast会弹两次
    // 部分手机上，一次页面加载后续会触发多次PageFinished，导致多次广告过滤条数toast展现，
    // 这里做个简单的限制，即使有多次PageFinished，也只在第一次出广告过滤toast
    private boolean adToasted = false;

    @Override
    public void onPageStarted(final WebView view, String url, Bitmap favicon) {
        onloadUrl = url;
        AdFilters=0;
        adToasted = false;

        // Fix在网页输入框中输入完毕后软键盘不收起的问题；
        if (view != null) {
            try {
                InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(view.getContext().INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            } catch (Exception e) {
            }
        }

//        _webview.injectJavascriptInterfaces(view, url);
        super.onPageStarted(view, url, favicon);
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        return super.shouldOverrideUrlLoading(view, url);
    }

    @Override
    public void onPageFinished(final WebView view, String url) {
        _webview.injectJavascriptInterfaces(view, url);
        if(AdFilters>0 && AdPattern.get(view.getContext()).IsShowTip()){
            if (!adToasted) {
                Toast sToast = Toast.makeText(view.getContext(), R.string.pl_libsafewebview_web_filter + AdFilters + R.string.pl_libsafewebview_web_adfilter, Toast.LENGTH_SHORT);
                sToast.setDuration(2000);
                sToast.show();
            }
			AdFilters=0;
        }
        adToasted = true;
        super.onPageFinished(view, url);
    }

    @Override
    public void onLoadResource(WebView view, String url) {
        super.onLoadResource(view, url);
    }

    public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
        if (Build.VERSION.SDK_INT >= 11 && AdPattern.get(view.getContext()).matches(url, onloadUrl,view)) {
        	AdFilters++;
        	return new WebResourceResponse(null, null, null);
        }
        return null;
    }

    @Override
    public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
//        _webview.injectJavascriptInterfaces(view, url);
        super.doUpdateVisitedHistory(view, url, isReload);
    }
}
