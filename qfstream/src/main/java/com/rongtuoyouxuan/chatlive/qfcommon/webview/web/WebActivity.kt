package com.rongtuoyouxuan.chatlive.qfcommon.webview.web

import android.content.Intent
import android.view.ViewGroup
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.StringUtils
import com.just.agentweb.IAgentWebSettings
import com.just.agentweb.WebViewClient
import com.rongtuoyouxuan.chatlive.crtrouter.constants.RouterConstant
import com.rongtuoyouxuan.chatlive.crtrouter.constants.RouterParams
import com.rongtuoyouxuan.chatlive.stream.R
import kotlinx.android.synthetic.main.activity_web.*

@Route(path = RouterConstant.PATH_WEB)
class WebActivity : com.rongtuoyouxuan.chatlive.qfcommon.webview.base.BaseWebActivity() {
    @JvmField
    @Autowired(name = RouterParams.URL)
    var targetUrl: String = "" //url 路由

    @JvmField
    @Autowired(name = RouterParams.IS_TO_MAIN_ACTIVITY)
    var isToMainActivity: Boolean = false //用户ID

    private val commonViewModel by lazy {
        getViewModel(com.rongtuoyouxuan.chatlive.qfcommon.viewmodel.CommonViewModel::class.java)
    }

    private lateinit var lifeCyclePlugin: com.rongtuoyouxuan.chatlive.qfcommon.webview.web.WebLifeCyclePlugin
    private lateinit var webInterfacePlugin: com.rongtuoyouxuan.chatlive.qfcommon.webview.androidinterface.WebInterfacePlugin


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
            com.rongtuoyouxuan.chatlive.qfcommon.webview.web.WebLifeCyclePlugin(mAgentWeb)
        lifecycle.addObserver(lifeCyclePlugin)


        webInterfacePlugin =
            com.rongtuoyouxuan.chatlive.qfcommon.webview.androidinterface.WebInterfacePlugin(
                mAgentWeb,
                lifecycle,
                commonViewModel
            )
        mAgentWeb.jsInterfaceHolder.addJavaObject(
            com.rongtuoyouxuan.chatlive.qfcommon.webview.androidinterface.AndroidInterfacePlugin.MOBILE,
            webInterfacePlugin)
    }


    override fun getAgentWebParent(): ViewGroup = rootView


    override fun getUrl(): String = targetUrl

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        lifeCyclePlugin.onActivityResult(requestCode, resultCode, data)
        webInterfacePlugin.onActivityResult(requestCode, resultCode, data)
    }

    //=======================================================================================================================
    override fun getWebViewClient(): WebViewClient {
        return com.rongtuoyouxuan.chatlive.qfcommon.webview.web.CustomWebViewClient()
    }

    override fun getAgentWebSettings(): IAgentWebSettings<*> {
        return com.rongtuoyouxuan.chatlive.qfcommon.webview.web.WebSettings()
    }

    override fun getLayoutResId(): Int = R.layout.activity_web

    override fun statusBarSetting() {
        BarUtils.transparentStatusBar(this)
        BarUtils.setStatusBarLightMode(this, true)
    }
}