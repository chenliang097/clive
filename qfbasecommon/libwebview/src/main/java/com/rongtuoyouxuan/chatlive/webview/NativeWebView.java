package com.rongtuoyouxuan.chatlive.webview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebSettings.PluginState;

import com.rongtuoyouxuan.chatlive.util.EnvUtils;
import webview._webview;

public class NativeWebView extends _webview {

    public OnScrollChangedListenser mOnScrollChangedListenser;

    /**
     * @param context
     */
    public NativeWebView(Context context) {
		super(context);
		initSettings();
    }

    /**
     * @param context
     * @param attrs
     * @param defStyle
     */
    public NativeWebView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initSettings();
    }

    /**
     * @param context
     * @param attrs
     */
    public NativeWebView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initSettings();
    }

    @SuppressLint("SetJavaScriptEnabled")
    @SuppressWarnings("deprecation")
    private void initSettings() {
		WebSettings settings = getSettings();
		settings.setJavaScriptCanOpenWindowsAutomatically(true);
		settings.setJavaScriptEnabled(true);
		/**
		 * 这一行引起悬浮窗webview再次打开后滑动奔溃，貌似ontouch与缩放冲突，坑爹啊==
		 */
		//settings.setBuiltInZoomControls(true);
		settings.setSupportZoom(true);
		settings.setRenderPriority(WebSettings.RenderPriority.HIGH);
		settings.setUseWideViewPort(true);
		settings.setDefaultTextEncodingName("utf-8");
		settings.setDomStorageEnabled(true);
		settings.setLoadWithOverviewMode(true);
		settings.setPluginState(PluginState.ON);
		settings.setAllowFileAccess(false);
		//settings.setPluginsEnabled(true);
		settings.setGeolocationEnabled(true);
		settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
		settings.setNeedInitialFocus(false);
		settings.setLoadWithOverviewMode(true);
		//settings.setUserAgentString(settings.getUserAgentString() + " mso_app ("+DeviceUtils.getVersionName()+")");
		settings.setDatabaseEnabled(true);
		//settings.setDatabasePath(Config.DB_CACHE_PATH);
		//settings.setGeolocationDatabasePath(Config.DB_CACHE_PATH);

		// 添加统一的UA
		settings.setUserAgentString(settings.getUserAgentString() + " hwzjr/"+ EnvUtils.getAppVersion(null)+" Host");

	/*	String appCachePath = MyApplication.getInstance().getCacheDir().getAbsolutePath();
		settings.setAppCachePath(appCachePath);
		String dir = MyApplication.getInstance().getDir("database", Context.MODE_PRIVATE).getPath();
		settings.setDatabasePath(dir);*/
		String appCachePath = getContext().getCacheDir().getAbsolutePath();
		settings.setDatabasePath("/data/data/" + getContext().getPackageName() + "/databases/");

		setScrollbarFadingEnabled(true);
		setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
		setMapTrackballToArrowKeys(false); // use trackball direct

		removeJavascriptInterfaceCompat("searchBoxJavaBridge_");
		removeSearchBoxImpl();
	}

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		if (mOnScrollChangedListenser != null)
	    	mOnScrollChangedListenser.onScrollChanged(l, t, oldl, oldt);
		super.onScrollChanged(l, t, oldl, oldt);
    }
   
    public void setOnScrollChangedListener(OnScrollChangedListenser l) {
    	mOnScrollChangedListenser = l;
    }

    /**
     * 是否能向下滚动
     *
     * @return
     */
    public boolean canScrollDown() {
		// WebView的总高度
		float webViewContentHeight = getContentHeight() * getScale();
		// WebView的现高度
		float webViewCurrentHeight = (getHeight() + getScrollY());
		if ((webViewContentHeight - webViewCurrentHeight) == 0)
			return false;
		else
			return true;
    }
   
    /**
     * 能否向左边滑动
     *
     * @return
     */
    public boolean canScrollLeft() {
		return getScrollX() == 0;
    }

    /**
     * 能否向右边滑动
     *
     * @return
     */
    public boolean canScrollRight() {
		final int offset = computeHorizontalScrollOffset();
		final int range = computeHorizontalScrollRange() - computeHorizontalScrollExtent();
		if (range == 0)
			return false;
		return offset < range - 1;
    }

    public interface OnScrollChangedListenser {
	public void onScrollChanged(int l, int t, int oldl, int oldt);
    }

    
}
