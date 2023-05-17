package com.rongtuoyouxuan.qfcommon.webview.androidinterface;

import android.app.Activity;
import android.content.Context;
import android.util.Pair;
import android.webkit.JavascriptInterface;

import androidx.lifecycle.LifecycleOwner;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.MapUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.StringUtils;
import com.rongtuoyouxuan.chatlive.databus.DataBus;
import com.rongtuoyouxuan.chatlive.databus.language.LocaleManager;
import com.rongtuoyouxuan.chatlive.log.upload.ULog;
import com.rongtuoyouxuan.chatlive.net2.BaseNetImpl;
import com.rongtuoyouxuan.chatlive.util.UIUtils;
import com.rongtuoyouxuan.qfcommon.webview.base.BaseWebActivity;
import com.rongtuoyouxuan.qfcommon.webview.model.ComInterfaceData;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.JsAccessEntrace;

//==================================================样例代码======================================================================
//    public void example() {
//        ThreadUtils.runOnUiThreadDelayed(() -> {
//            jsAccessEntrace.quickCallJs("onBalanceChange",
//                    GsonUtils.toJson(MapUtils.newHashMap(new Pair<>("gold_bean_balance", "100"),
//                            new Pair<>("gold_coin_balance", "200"))));
//
//            jsAccessEntrace.quickCallJs("onLiveChatMessage",
//                    GsonUtils.toJson(MapUtils.newHashMap(new Pair<>("user_id", "456"),
//                            new Pair<>("content", "你好啊"))));
//
//            jsAccessEntrace.quickCallJs("onTestAlert", GsonUtils.toJson(MapUtils.newHashMap(new Pair<>("", ""))));
//        }, 5000);
//    }
//
//    @JavascriptInterface
//    public void getToken(String json) {
//        LogUtils.e("getToken:" + json);
//        ComInterfaceData data = GsonUtils.fromJson(json, ComInterfaceData.class);
//        jsAccessEntrace.quickCallJs(data.getJsCallback(),
//                GsonUtils.toJson(MapUtils.newHashMap(new Pair<>("token", "123"))));
//    }
//
//    @JavascriptInterface
//    public void hideLoading(String json) {
//        LogUtils.e("hideLoading:" + json);
//        ComInterfaceData data = GsonUtils.fromJson(json, ComInterfaceData.class);
//        jsAccessEntrace.quickCallJs(data.getJsCallback(),
//                GsonUtils.toJson(MapUtils.newHashMap(new Pair<>("type", data.getType()),
//                        new Pair<>("result", "0"))));
//    }
public class AndroidInterfacePlugin {
    public static final String MOBILE = "NativeBridge";
    protected Context context;
    protected JsAccessEntrace jsAccessEntrace;

    public AndroidInterfacePlugin(AgentWeb mAgentWeb, Context context) {
        this.context = context;
        jsAccessEntrace = mAgentWeb.getJsAccessEntrace();
        mAgentWeb.getJsInterfaceHolder().addJavaObject(AndroidInterfacePlugin.MOBILE,
                this);
    }

    //获取语言
    //languageType:手机语言类型。//中文、繁体中文、英文、印尼语、马来语、泰语、越南语=>对应0-6
    @JavascriptInterface
    public void getLanguage(String json) {
        ULog.d("js", "getLanguage");
        ComInterfaceData data = GsonUtils.fromJson(json, ComInterfaceData.class);
        String englishValue = DataBus.instance().getLocaleManager().getLanguage().getValue();
        int result = 0;
        if (!StringUtils.isTrimEmpty(englishValue)) {
            switch (englishValue) {
                case LocaleManager.LANGUAGE_ENGLISH:
                    result = 2;
                    break;
                case LocaleManager.LANGUAGE_SIMPLIFIED_CHINESE:
                    result = 0;
                    break;
                case LocaleManager.LANGUAGE_ZH_TW:
                    result = 1;
                    break;
                case LocaleManager.LANGUAGE_TH:
                    result = 5;
                    break;
                case LocaleManager.LANGUAGE_IN:
                    result = 3;
                    break;
                case LocaleManager.LANGUAGE_MS:
                    result = 4;
                    break;
                case LocaleManager.LANGUAGE_VIETNAM:
                    result = 6;
                    break;
                case LocaleManager.LANGUAGE_AR:
                    result = 7;
                    break;
            }
        }
        callJs(data.getJsCallback(),
                new Pair<>("languageType", result)
        );
    }

    @SafeVarargs
    protected final <K, V> void callJs(String method, final Pair<K, V>... pairs) {
        jsAccessEntrace.quickCallJs(method,
                GsonUtils.toJson(
                        MapUtils.newHashMap(pairs
                        ))
        );
    }

    protected final void callJs(String method) {
        jsAccessEntrace.quickCallJs(method);
    }

    @JavascriptInterface
    public void getAppVersion(String json) {
        ULog.d("js", "getAppVersion");
        try {
            ComInterfaceData data = GsonUtils.fromJson(json, ComInterfaceData.class);
            callJs(
                    data.getJsCallback(),
                    new Pair<>("currentVersion", AppUtils.getAppVersionName())
            );
        } catch (Exception ex) {
            ULog.e("js:getAppVersion", ex.getMessage());
        }
    }

    //刷新token
    public void refreshToken(LifecycleOwner owner) {
        ULog.d("js", "refreshToken");
    }

    //获取底部菜单栏高度
    @JavascriptInterface
    public void getBottomMenuHeight(String json) {
        try {
            ULog.d("js", "getBottomMenuHeight:" + SizeUtils.dp2px(45f) + ">>sh:" + UIUtils.screenHeight(context));

            ComInterfaceData data = GsonUtils.fromJson(json, ComInterfaceData.class);
            callJs(
                    data.getJsCallback(),
                    new Pair<>("bottomHeight", SizeUtils.dp2px(45f))
            );
        } catch (Exception ex) {
            ULog.e("js:getGameSoundStatus", ex.getMessage());
        }
    }

    //获取游戏音量开关状态
    @JavascriptInterface
    public void getGameSoundStatus(String json) {
        ULog.d("js", "getGameSoundStatus");
        try {
            ComInterfaceData data = GsonUtils.fromJson(json, ComInterfaceData.class);
            int status = SPUtils.getInstance().getInt("key_game_" + data.getScene(), 1);
            callJs(
                    data.getJsCallback(),
                    new Pair<>("status", status)
            );
        } catch (Exception ex) {
            ULog.e("js:getGameSoundStatus", ex.getMessage());
        }
    }

    //设置游戏音量开关状态
    @JavascriptInterface
    public void setGameSoundStatus(String json) {
        ULog.d("js", "setGameSoundStatus");
        try {
            ComInterfaceData data = GsonUtils.fromJson(json, ComInterfaceData.class);
            SPUtils.getInstance().put("key_game_" + data.getScene(), data.getStatus());
        } catch (Exception ex) {
            ULog.e("js:setGameSoundStatus", ex.getMessage());
        }
    }

    //刷新H5余额
    public void accountBalance() {
    }
}
