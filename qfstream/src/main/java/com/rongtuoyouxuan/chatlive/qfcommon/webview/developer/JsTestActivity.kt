package com.rongtuoyouxuan.chatlive.qfcommon.webview.developer

import android.view.ViewGroup
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.PathUtils
import com.just.agentweb.IAgentWebSettings
import com.just.agentweb.WebViewClient
import com.rongtuoyouxuan.chatlive.crtrouter.constants.RouterConstant
import com.rongtuoyouxuan.chatlive.stream.R
import kotlinx.android.synthetic.main.activity_js_test.*
import java.io.File

@Route(path = RouterConstant.PATH_JS_TEST)
class JsTestActivity : com.rongtuoyouxuan.chatlive.qfcommon.webview.base.BaseWebActivity() {

    override fun initListener() {
    }


    override fun initData() {
        super.initData()
//        lifecycle.addObserver(CocosLifeCyclePlugin())
        mAgentWeb.jsInterfaceHolder.addJavaObject(
            com.rongtuoyouxuan.chatlive.qfcommon.webview.androidinterface.AndroidInterfacePlugin.MOBILE,
            com.rongtuoyouxuan.chatlive.qfcommon.webview.androidinterface.AndroidInterfacePlugin(
                mAgentWeb,
                this
            )
        )
    }


    //本地
    override fun getUrl(): String {
        return PathUtils.getCachePathExternalFirst() + File.separator + "wawaji" + File.separator + "index.html"
    }


    override fun getAgentWebSettings(): IAgentWebSettings<*> {
        return com.rongtuoyouxuan.chatlive.qfcommon.webview.cocos.CocosSettings()
    }

    override fun getAgentWebParent(): ViewGroup = rootView


    override fun getWebViewClient(): WebViewClient {
        return com.rongtuoyouxuan.chatlive.qfcommon.webview.cocos.CocosWebViewClient()
    }

    override fun getLayoutResId(): Int = R.layout.activity_js_test
}