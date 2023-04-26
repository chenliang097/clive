package com.rongtuoyouxuan.chatlive.webview.jsinterface;

import android.content.Context;
import android.text.TextUtils;
import android.webkit.WebView;

import java.lang.reflect.Constructor;

import com.rongtuoyouxuan.chatlive.webview.NativeWebView;

public class InjdectJs {
	public InjdectJs() {
	}

	public static boolean InjectAllJsNode(WebView web, WebviewListener listener) {
		if(web == null) {
			return false;
		}
		for (JsNode type : JsNode.values()) {
			JsCallbackResult obj = type.GetObject();
			if((obj != null) && obj.canInject()) {
			    obj.setListener(web.getContext(), listener);
				web.addJavascriptInterface(obj, type.name);
			}
		}
		return true;
	}

	public static boolean ReInjectAll(Context context, WebView web) {
		if(web == null) {
			return false;
		}
        try {
        	 NativeWebView nativeWebView = (NativeWebView)web;
        	 for (JsNode type : JsNode.values()) {
    			JsCallbackResult obj = type.GetObject();
    			if( (obj != null) && obj.canInject()) {
    				web.addJavascriptInterface(obj, type.name);
    			}
    		}
        } catch (Exception e) {
            //LogUtils.e(e);
        }
        return true;
	}

	public static boolean CheckIsOurInterface(String jsname) {
		if(TextUtils.isEmpty(jsname)) {
			return false;
		}
		for (JsNode type : JsNode.values()) {
			if(jsname.equalsIgnoreCase(type.name)) {
				return true;
			}
		}
		return false;
	}

	public enum JsNode {
		JS_COMMODITY_RESULT("BobooClient", null, JsCallbackResult.class);
		private String name;
		private JsCallbackResult mobj;
		private Class<? extends JsCallbackResult> fclass;

		private JsNode(String name,JsCallbackResult obj,Class<? extends JsCallbackResult> fclass) {
			this.name = name;
			this.mobj = obj;
			this.fclass = fclass;
		}

		public JsCallbackResult GetObject() {
			if(this.mobj != null) {
				return this.mobj;
			}
			if(this.fclass != null) {
				JsCallbackResult obj = null;
				try {
					Constructor<? extends JsCallbackResult> constructor = this.fclass.getConstructor();
					if(constructor != null) {
						obj = constructor.newInstance();
					}
				} catch (Exception e) {
					//LogUtils.e(e);
				}
				return obj;
			}
			return null;
		}
	}
}
