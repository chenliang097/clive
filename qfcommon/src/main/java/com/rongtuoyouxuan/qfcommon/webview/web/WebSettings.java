package com.rongtuoyouxuan.qfcommon.webview.web;

import android.os.Build;
import android.webkit.WebView;

import com.just.agentweb.AbsAgentWebSettings;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.IAgentWebSettings;


public class WebSettings extends AbsAgentWebSettings {

    public WebSettings() {
        super();
    }

    private AgentWeb mAgentWeb;

    @Override
    protected void bindAgentWebSupport(AgentWeb agentWeb) {
        this.mAgentWeb = agentWeb;
    }


    @Override
    public IAgentWebSettings toSetting(WebView webView) {
        super.toSetting(webView);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            getWebSettings().setAllowFileAccessFromFileURLs(true); //通过 file mUrl 加载的 Javascript 读取其他的本地文件 .建议关闭
            getWebSettings().setAllowUniversalAccessFromFileURLs(true);//
        }
        return this;
    }

}
