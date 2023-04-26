package webview;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;

import utils.JavaScriptFilter;
import utils.LogUtils;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.webkit.JsPromptResult;
import android.webkit.WebView;

public class _webview extends WebView {

    private final static String javaScriptTag = "javascript:";
	private final String BLANK_URL = "about:blank";
	private final String VAR_ARG_PREFIX = "arg";
	private final String MSG_PROMPT_HEADER = "MyApp:";
	private final String KEY_INTERFACE_NAME = "obj";
	private final String KEY_FUNCTION_NAME = "func";
	private final String KEY_ARG_ARRAY = "args";
	private final String[] mFilterMethods = { "getClass", "hashCode", "notify",
			"notifyAll", "equals", "toString", "wait", };
	private HashMap<String, Object> mJsInterfaceMap;
	private String mJsStringCache = null;
	private List<Pattern> mInjectJsWhite = new ArrayList<Pattern>();
	@SuppressLint("UseSparseArrays")
	private Map<String, String> mJavaScriptMap = new HashMap<String, String>();

	public _webview(Context context) {
		super(context);
		if (mJsInterfaceMap == null)
			mJsInterfaceMap = new HashMap<String, Object>();
	}

	public _webview(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public _webview(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/**
	 * 检查内核SDK版本是否在4.2以上（包含4.2）
	 *
	 * @return：如是，返回true；否则返回false
	 */
	public static boolean hasJellyBeanMR1() {
		return Build.VERSION.SDK_INT >= 17;
	}

	/**
	 * 在页面加载时（开始、完成）调用，完成Java对象方法向js环境的暴露、及预先定义js脚本向页面的注入
	 *
	 * @param webView
	 * @param url
	 */
	public static void injectJavascriptInterfaces(WebView webView, String url) {
		if (!(webView instanceof _webview) || webView == null) {
			return;
		}

		_webview safeweb = (_webview) webView;
		boolean bisIndect = false;
		if (!TextUtils.isEmpty(url)) {
			for (Pattern mPattern : safeweb.mInjectJsWhite) {
				if (mPattern == null) {
					continue;
				}
				bisIndect = mPattern.matcher(url).matches();
				if (bisIndect) {
					break;
				}
			}
		} else {
			bisIndect = true;
		}

		if (bisIndect) {
			// 先完成Java对象方法向js环境的暴露（4.2版本以下）
			if (!hasJellyBeanMR1()) {
				safeweb.injectJavascriptInterfaces();
			}

            // by mingbin: 将所有需要注入的JS脚本Merge到一起一次注入，提高JS注入性能
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(javaScriptTag);
            for(String javaScript : safeweb.mJavaScriptMap.values()) {
                stringBuilder.append(javaScript);
                stringBuilder.append(";");
            }

            String jsInjectMerged = stringBuilder.toString();
            if (!TextUtils.isEmpty(jsInjectMerged)) {
                safeweb.loadUrl(jsInjectMerged);
            }
		}
	}

	public void AddJavaScriptString(String key, String javaScript) {
		if (TextUtils.isEmpty(javaScript) || TextUtils.isEmpty(key)) {
			return;
		}

        javaScript = javaScript.trim();
        if (javaScript.startsWith(javaScriptTag)) {
            javaScript = javaScript.substring(javaScriptTag.length());
        }

		mJavaScriptMap.put(key, javaScript);
	}
	
	public void reAddJsString(String key, String javaScript) {
		if (TextUtils.isEmpty(javaScript) || TextUtils.isEmpty(key)) {
			return;
		}

        javaScript = javaScript.trim();
        if (javaScript.startsWith(javaScriptTag)) {
            javaScript = javaScript.substring(javaScriptTag.length());
        }

		mJavaScriptMap.put(key, javaScript);
		injectJavascriptInterfaces(this, getUrl());
	}

	/**
	 * fix the clear view not perform immediately
	 */
	public void LoadBankUrl() {
		super.loadUrl(BLANK_URL);
	}

	/**
	 * 重写loadurl可以过滤脚本注入
	 * @author wangzefeng
	 */
	public void loadUrl(String url){
		if (!TextUtils.isEmpty(url)) {
			if(!JavaScriptFilter.mJsInjectOn){
                if(url.contains(javaScriptTag)) {
                    return;
                }
			}
			super.loadUrl(url);
		}
	}

	public boolean IsBlankUrl(String url) {
		if (TextUtils.isEmpty(url)) {
			return false;
		}
		return url.equalsIgnoreCase(BLANK_URL);
	}

	@SuppressLint("NewApi")
	public void removeJavascriptInterfaceCompat(String methedName) {
        try {
            // 删除反射隐患
            if (Build.VERSION.SDK_INT >= 11) {
                super.removeJavascriptInterface(methedName);
            } else {
                try {
                    Class<? extends _webview> aClass = getClass();
                    if (aClass != null) {
                        Method removeJavascriptInterface = aClass.getMethod("removeJavascriptInterface");
                        if (removeJavascriptInterface != null) {
                            removeJavascriptInterface.invoke(this,
                                    new Object[]{methedName});
                        }
                    }

                } catch (Exception e) {
                    LogUtils.e(e);
                }

            }
        } catch (Exception ignored) {
        }catch (Error error) {}
	}

	@SuppressLint("NewApi")
	protected boolean removeSearchBoxImpl() {
		try {
			if (hasHoneycomb() && !hasJellyBeanMR1()) {
				super.removeJavascriptInterface("searchBoxJavaBridge_");
				return true;
			}
		} catch (Exception ignore) {
		}
		return false;
	}

	@SuppressLint("NewApi")
	@Override
	public void removeJavascriptInterface(String interfaceName) {
        try {
            if (hasJellyBeanMR1()) {
                super.removeJavascriptInterface(interfaceName);
            } else {
                if (mJsInterfaceMap != null)
                    mJsInterfaceMap.remove(interfaceName);
                mJsStringCache = null;
                injectJavascriptInterfaces();
            }
        } catch (Exception ignored) {
        }
    }

	/**
	 * Override不安全的addJavascriptInterface（向js脚本暴露Java对象）
	 * 对4.2以上（含4.2），原方法是安全的，直接调用 对4.2一下，采用了更安全的方法
	 *
	 * 注：_webview的继承类（如QihooWebView），可以安全地使用此方法做Java对象暴露
	 *
	 */
	@Override
	public void addJavascriptInterface(Object obj, String interfaceName) {
		if (TextUtils.isEmpty(interfaceName)) {
			return;
		}
		// 如果在4.2以上，直接调用基类的方法来注册
        try {
            if (hasJellyBeanMR1()) {
                super.addJavascriptInterface(obj, interfaceName);
            } else {
                if (mJsInterfaceMap == null)
                    mJsInterfaceMap = new HashMap<String, Object>();
                mJsInterfaceMap.put(interfaceName, obj);
            }
        } catch (Exception ignored) {
        }
    }

	public void ReIndectReady() {
		mJsStringCache = null;
		injectJavascriptInterfaces();
	}

	public boolean hasHoneycomb() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
	}

	/**
	 * 供_chromewebclient类的onJsPrompt()调用，以安全的方式完成js对Java对象方法的invoke
	 * 方法实现中调用了invokeJSInterfaceMethod()方法
	 *
	 * @param view
	 * @param url
	 * @param message 以json串的形式保存js调用的Java对象方法
	 * @param defaultValue
	 * @param result
	 * @return
	 */
	public boolean handleJsInterface(WebView view, String url, String message,
			String defaultValue, JsPromptResult result) {
		String prefix = MSG_PROMPT_HEADER;
		if (!message.startsWith(prefix)) {
			return false;
		}

		String jsonStr = message.substring(prefix.length());
		try {
			JSONObject jsonObj = new JSONObject(jsonStr);
			String interfaceName = jsonObj.getString(KEY_INTERFACE_NAME);
			String methodName = jsonObj.getString(KEY_FUNCTION_NAME);
			JSONArray argsArray = jsonObj.getJSONArray(KEY_ARG_ARRAY);
			Object[] args = null;
			if (null != argsArray) {
				int count = argsArray.length();
				if (count > 0) {
					args = new Object[count];

					for (int i = 0; i < count; ++i) {
						args[i] = argsArray.get(i);
					}
				}
			}

			if (invokeJSInterfaceMethod(result, interfaceName, methodName, args)) {
				return true;
			}
		} catch (Exception e) {
			LogUtils.e(e);
		}
		result.cancel();
		return false;
	}

	/**
	 * 供handleJsInterface()调用的私有方法，真正实现了js对Java对象方法的invoke
	 *
	 * @param result
	 * @param interfaceName
	 * @param methodName
	 * @param args
	 * @return
	 */
	private boolean invokeJSInterfaceMethod(JsPromptResult result,
			String interfaceName, String methodName, Object[] args) {

		boolean succeed = false;
		final Object obj = mJsInterfaceMap.get(interfaceName);
		if (null == obj) {
			result.cancel();
			return false;
		}

		Class<?>[] parameterTypes = null;
		int count = 0;
		if (args != null) {
			count = args.length;
		}

		if (count > 0) {
			parameterTypes = new Class[count];
			for (int i = 0; i < count; ++i) {
				parameterTypes[i] = getClassFromJsonObject(args[i]);
			}
		}

		try {
			Method method = obj.getClass().getMethod(methodName, parameterTypes);
			Object returnObj = method.invoke(obj, args); // 执行接口调用
			boolean isVoid = returnObj == null || returnObj.getClass() == void.class;
			String returnValue = isVoid ? "" : returnObj.toString();
			result.confirm(returnValue); // 通过prompt返回调用结果
			succeed = true;
		} catch (NoSuchMethodException e) {
			LogUtils.e(e);
		} catch (Exception e) {
			LogUtils.e(e);
		}

		result.cancel();
		return succeed;
	}

	private Class<?> getClassFromJsonObject(Object obj) {
		Class<?> cls = obj.getClass();

		// js对象只支持int boolean string三种类型
		if (cls == Integer.class) {
			cls = Integer.TYPE;
		} else if (cls == Boolean.class) {
			cls = Boolean.TYPE;
		} else {
			cls = String.class;
		}

		return cls;
	}

	/**
	 * 以安全的方式，完成Java对象方法向js环境的暴露
	 */
	public void injectJavascriptInterfaces() {
		if (TextUtils.isEmpty(mJsStringCache)) {
			String jsString = genJavascriptInterfacesString();
			if (!TextUtils.isEmpty(jsString)) {
				mJsStringCache = jsString;
			}
		}
		loadJavascriptInterfaces();
	}

	private void loadJavascriptInterfaces() {
		if (!TextUtils.isEmpty(mJsStringCache)) {
			loadUrl(mJsStringCache);
		}
	}

	/**
	 * 供injectJavascriptInterfaces()调用的私有方法
	 * 4.2以下（不含4.2）版本的addJavascriptInterface()有安全漏洞，此方法（及调用它的方法）均为修补漏洞用
	 * 
     * 要注入的JS的格式，其中XXX为注入的对象的方法名，例如注入的对象中有一个方法A，那么这个XXX就是A
	 * 如果这个对象中有多个方法，则会注册多个window.XXX_js_interface_name块，我们是用反射的方法遍历
	 * 注入对象中的所有带有@JavaScripterInterface标注的方法
	 * 
	 * 欲向js暴露的Java对象定义在HashMap对象mJsInterfaceMap中（ 通过此类override的addJavascriptInterface方法定义）
	 * 此方法基于mJsInterfaceMap，生成注入的js脚本。具体如下：
	 * 
	 * 假定XXX为注入对象的方法名（供js脚本使用）
	 * 如果这个注入对象中有多个方法，则会注册多个window.XXX_js_interface_name块
	 * 我们是用反射的方法遍历注入对象中的所有带有@JavaScripterInterface标注的方法
	 * 将这些方法定义成js的promt函数，暴露的Java对象名及对象以json串的形式作为prompt函数的参数
	 * 
	 * 原来对Java对象方法的调用，变成了对prompt函数的调用，继而触发了onJsPrompt()方法，
	 * 在那里检查调用的安全性，对安全的调用实现invoke
	 * 
	 * 生成的JS脚本的格式（注意里面的prompt）：
	 * javascript:(function JsAddJavascriptInterface_(){
	 *   if (typeof(window.XXX_js_interface_name)!='undefined') {
	 *     console.log('window.XXX_js_interface_name is exist!!'); 
	 *   } else {
	 *     window.XXX_js_interface_name={ 
	 *       XXX: function(arg0,arg1) { 
	 *         return prompt(
	 *           'MyApp:'+JSON.stringify({
	 *                      obj:'XXX_js_interface_name',
	 *                      func:'XXX_',
	 *                      args:[arg0,arg1]})); 
	 *       }, 
	 *     }; 
	 *   } 
	 * })()
	 * 
	 * @return：生成如上格式的js脚本串
	 */
	private String genJavascriptInterfacesString() {
    	if(mJsInterfaceMap == null){
    		return null;
    	}
		if (mJsInterfaceMap.size() == 0) {
			mJsStringCache = null;
			return null;
		}

		// 生成js脚本串的头
		StringBuilder script = new StringBuilder();
		script.append("javascript:(function JsAddJavascriptInterface_(){");

		// 为mJsInterfaceMap中定义的每个注入对象，生成js脚本中对应的方法定义
		Iterator<Entry<String, Object>> iterator = mJsInterfaceMap.entrySet().iterator();
		try {
			while (iterator.hasNext()) {
				Entry<String, Object> entry = iterator.next();
				String interfaceName = entry.getKey();
				Object obj = entry.getValue();

				createJsMethod(interfaceName, obj, script);
			}
		} catch (Exception e) {
			LogUtils.e(e);
		}

		// 完成js脚本
		script.append("})()");
		return script.toString();
	}

	/**
	 * 供genJavascriptInterfacesString()调用的私有方法： 基于输入参数interfaceName和obj，产生一段js脚本串
	 * 脚本串将暴露的Java对象方法定义为js的prompt函数，暴露的对象名及对象以json串的形式传递给prompt作为参数
	 * 
	 * interfaceName和obj是mJsInterfaceMap（类成员变量，HashMap对象）的一项
	 * 
	 * @param interfaceName：供js引用的暴露的Java对象名
	 * @param obj：向js暴露的Java对象
	 * 
	 * @param script：此方法的输出字符串在这里
	 */
	private void createJsMethod(String interfaceName, Object obj,
			StringBuilder script) {
		if (TextUtils.isEmpty(interfaceName) || null == obj || null == script) {
			return;
		}

		Class<? extends Object> objClass = obj.getClass();

		script.append("if(typeof(window.").append(interfaceName).append(")!='undefined'){");

		script.append("}else {");
		script.append("    window.").append(interfaceName).append("={");

		// Add methods
		Method[] methods = objClass.getMethods();
		for (Method method : methods) {
			String methodName = method.getName();
			// 过滤掉Object类的方法，包括getClass()方法，因为在Js中就是通过getClass()方法来得到Runtime实例
			if (filterMethods(methodName)) {
				continue;
			}

			script.append("        ").append(methodName).append(":function(");
			// 添加方法的参数
			int argCount = method.getParameterTypes().length;
			if (argCount > 0) {
				int maxCount = argCount - 1;
				for (int i = 0; i < maxCount; ++i) {
					script.append(VAR_ARG_PREFIX).append(i).append(",");
				}
				script.append(VAR_ARG_PREFIX).append(argCount - 1);
			}

			script.append(") {");

			// Add implementation
			if (method.getReturnType() != void.class) {
				script.append("            return ").append("prompt('").append(MSG_PROMPT_HEADER)
					.append("'+");
			} else {
				script.append("            prompt('").append(MSG_PROMPT_HEADER).append("'+");
			}

			// Begin JSON
			script.append("JSON.stringify({");
			script.append(KEY_INTERFACE_NAME).append(":'").append(interfaceName).append("',");
			script.append(KEY_FUNCTION_NAME).append(":'").append(methodName).append("',");
			script.append(KEY_ARG_ARRAY).append(":[");
			// 添加参数到JSON串中
			if (argCount > 0) {
				int max = argCount - 1;
				for (int i = 0; i < max; i++) {
					script.append(VAR_ARG_PREFIX).append(i).append(",");
				}
				script.append(VAR_ARG_PREFIX).append(max);
			}

			// End JSON
			script.append("]})");
			// End prompt
			script.append(");");
			// End function
			script.append("        }, ");
		}

		// End of obj
		script.append("    };");
		// End of if or else
		script.append("}");
	}

	private boolean filterMethods(String methodName) {
		for (String method : mFilterMethods) {
			if (method.equals(methodName)) {
				return true;
			}
		}
		return false;
	}

}
