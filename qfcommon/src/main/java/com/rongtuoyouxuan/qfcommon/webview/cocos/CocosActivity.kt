package com.rongtuoyouxuan.qfcommon.webview.cocos

import android.content.Intent
import android.view.ViewGroup
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.StringUtils
import com.rongtuoyouxuan.chatlive.router.constants.RouterConstant
import com.rongtuoyouxuan.chatlive.router.constants.RouterParams
import com.rongtuoyouxuan.qfcommon.R
import com.rongtuoyouxuan.qfcommon.webview.androidinterface.CocosInterfacePlugin
import com.rongtuoyouxuan.qfcommon.webview.base.BaseWebActivity
import com.just.agentweb.IAgentWebSettings
import com.just.agentweb.WebViewClient
import kotlinx.android.synthetic.main.activity_cocos.*

@Route(path = RouterConstant.PATH_COCOS)
class CocosActivity : BaseWebActivity() {
    @JvmField
    @Autowired(name = RouterParams.URL)
    var targetUrl: String = "" //url 路由
    private lateinit var lifeCyclePlugin: CocosLifeCyclePlugin
    private lateinit var cocosLifeCyclePlugin: CocosInterfacePlugin

    override fun initListener() {

    }

    override fun initData() {
        if (StringUtils.isTrimEmpty(targetUrl)) {
            onBackPressed()
            return
        }
        LogUtils.e("targetUrl:->$targetUrl")
        super.initData()
        lifeCyclePlugin = CocosLifeCyclePlugin(mAgentWeb)
        lifecycle.addObserver(lifeCyclePlugin)

        cocosLifeCyclePlugin = CocosInterfacePlugin(
            mAgentWeb,
            this)
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
        return CocosWebViewClient()
    }


    override fun getAgentWebSettings(): IAgentWebSettings<*> {
        return CocosSettings()
    }

    override fun getLayoutResId(): Int = R.layout.activity_cocos
}