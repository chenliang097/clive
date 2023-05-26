package com.rongtuoyouxuan.chatlive.qfcommon.webview.cocos

import android.content.Intent
import android.view.ViewGroup
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.StringUtils
import com.just.agentweb.IAgentWebSettings
import com.just.agentweb.WebViewClient
import com.rongtuoyouxuan.chatlive.crtrouter.constants.RouterConstant
import com.rongtuoyouxuan.chatlive.crtrouter.constants.RouterParams
import com.rongtuoyouxuan.chatlive.stream.R
import kotlinx.android.synthetic.main.activity_cocos.*

@Route(path = RouterConstant.PATH_COCOS)
class CocosActivity : com.rongtuoyouxuan.chatlive.qfcommon.webview.base.BaseWebActivity() {
    @JvmField
    @Autowired(name = RouterParams.URL)
    var targetUrl: String = "" //url 路由
    private lateinit var lifeCyclePlugin: com.rongtuoyouxuan.chatlive.qfcommon.webview.cocos.CocosLifeCyclePlugin
    private lateinit var cocosLifeCyclePlugin: com.rongtuoyouxuan.chatlive.qfcommon.webview.androidinterface.CocosInterfacePlugin

    override fun initListener() {

    }

    override fun initData() {
        if (StringUtils.isTrimEmpty(targetUrl)) {
            onBackPressed()
            return
        }
        LogUtils.e("targetUrl:->$targetUrl")
        super.initData()
        lifeCyclePlugin =
            com.rongtuoyouxuan.chatlive.qfcommon.webview.cocos.CocosLifeCyclePlugin(mAgentWeb)
        lifecycle.addObserver(lifeCyclePlugin)

        cocosLifeCyclePlugin =
            com.rongtuoyouxuan.chatlive.qfcommon.webview.androidinterface.CocosInterfacePlugin(
                mAgentWeb,
                this
            )
    }


    override fun getAgentWebParent(): ViewGroup = rootView


//    override fun getUrl(): String {
//        return "file:///android_asset/js_interaction/hello.html"
//    }

    override fun getUrl(): String = targetUrl


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        lifeCyclePlugin.onActivityResult(requestCode, resultCode, data)
        cocosLifeCyclePlugin.onActivityResult(requestCode, resultCode, data)
    }

    //=======================================================================================================================

    override fun getWebViewClient(): WebViewClient {
        return com.rongtuoyouxuan.chatlive.qfcommon.webview.cocos.CocosWebViewClient()
    }


    override fun getAgentWebSettings(): IAgentWebSettings<*> {
        return com.rongtuoyouxuan.chatlive.qfcommon.webview.cocos.CocosSettings()
    }

    override fun getLayoutResId(): Int = R.layout.activity_cocos
}