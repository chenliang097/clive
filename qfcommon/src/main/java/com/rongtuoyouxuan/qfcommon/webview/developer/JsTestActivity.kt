package com.rongtuoyouxuan.qfcommon.webview.developer

import android.view.ViewGroup
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.PathUtils
import com.rongtuoyouxuan.chatlive.router.constants.RouterConstant
import com.rongtuoyouxuan.qfcommon.R
import com.rongtuoyouxuan.qfcommon.webview.androidinterface.AndroidInterfacePlugin
import com.rongtuoyouxuan.qfcommon.webview.base.BaseWebActivity
import com.rongtuoyouxuan.qfcommon.webview.cocos.CocosWebViewClient
import com.rongtuoyouxuan.qfcommon.webview.cocos.CocosSettings
import com.just.agentweb.IAgentWebSettings
import com.just.agentweb.WebViewClient
import kotlinx.android.synthetic.main.activity_js_test.*
import java.io.File

@Route(path = RouterConstant.PATH_JS_TEST)
class JsTestActivity : BaseWebActivity() {

    override fun initListener() {
    }


    override fun initData() {
        super.initData()
//        lifecycle.addObserver(CocosLifeCyclePlugin())
        mAgentWeb.jsInterfaceHolder.addJavaObject(AndroidInterfacePlugin.MOBILE,
            AndroidInterfacePlugin(
                mAgentWeb,
                this))
    }


    //本地
    override fun getUrl(): String {
        return PathUtils.getCachePathExternalFirst() + File.separator + "wawaji" + File.separator + "index.html"
    }


    override fun getAgentWebSettings(): IAgentWebSettings<*> {
        return CocosSettings()
    }

    override fun getAgentWebParent(): ViewGroup = rootView


    override fun getWebViewClient(): WebViewClient {
        return CocosWebViewClient()
    }

    override fun getLayoutResId(): Int = R.layout.activity_js_test
}