package webview;

import android.webkit.JsPromptResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

public class _chromewebclient extends WebChromeClient {
	private boolean isInjected;
	/**
	 * 4.2以下（不含4.2）版本的addJavascriptInterface()有安全漏洞，
	 * 修补策略是：所有暴露的Java对象方法，实际重新定义为一个prompt()函数，暴露的对象名及对象以json串的形式作为prompt()的参数
	 *         当js调用暴露的对象方法时，实际执行的是prompt()函数，通过WebView的回调机制会触发这里的onJsPrompt()方法
	 *         此方法实现中，又调用了_webview中定义的handleJsInterface()方法，那里真正完成对暴露的Java对象方法的invoke
	 * 
	 * @param message：以json串的形式保存js调用的Java对象方法
	 */
	@Override
	public final boolean onJsPrompt(WebView view, String url, String message,
			String defaultValue, JsPromptResult result) {
		if ((view != null) && (view instanceof _webview)) {
			_webview myview = (_webview) view;
			if (myview.handleJsInterface(view, url, message, defaultValue,result)) {
				return true;
			}
		}
		return super.onJsPrompt(view, url, message, defaultValue, result);
	}

	@Override
	public void onProgressChanged(WebView view, int newProgress) {
        // 参考safe-java-js-webview-bridge做法
        if(newProgress<=25) {
            isInjected = false;
        } else if(!isInjected) {
            _webview.injectJavascriptInterfaces(view, view.getUrl());
            isInjected = true;
        }
		super.onProgressChanged(view, newProgress);
	}

	@Override
	public void onReceivedTitle(final WebView view, String title) {
//		_webview.injectJavascriptInterfaces(view, view.getUrl());
		super.onReceivedTitle(view, title);
	}
}
