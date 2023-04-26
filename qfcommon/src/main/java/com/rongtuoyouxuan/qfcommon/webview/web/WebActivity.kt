package com.rongtuoyouxuan.qfcommon.webview.web

import android.content.Intent
import android.view.ViewGroup
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.StringUtils
import com.rongtuoyouxuan.chatlive.router.constants.RouterConstant
import com.rongtuoyouxuan.chatlive.router.constants.RouterParams
import com.rongtuoyouxuan.qfcommon.R
import com.rongtuoyouxuan.qfcommon.viewmodel.CommonViewModel
import com.rongtuoyouxuan.qfcommon.webview.androidinterface.AndroidInterfacePlugin
import com.rongtuoyouxuan.qfcommon.webview.androidinterface.WebInterfacePlugin
import com.rongtuoyouxuan.qfcommon.webview.base.BaseWebActivity
import com.just.agentweb.IAgentWebSettings
import com.just.agentweb.WebViewClient
import kotlinx.android.synthetic.main.activity_web.*

@Route(path = RouterConstant.PATH_WEB)
class WebActivity : BaseWebActivity() {
    @JvmField
    @Autowired(name = RouterParams.URL)
    var targetUrl: String = "" //url 路由

    @JvmField
    @Autowired(name = RouterParams.IS_TO_MAIN_ACTIVITY)
    var isToMainActivity: Boolean = false //用户ID

    private val commonViewModel by lazy {
        getViewModel(CommonViewModel::class.java)
    }

    private lateinit var lifeCyclePlugin: WebLifeCyclePlugin
    private lateinit var webInterfacePlugin: WebInterfacePlugin


    override fun initListener() {

    }


    override fun initData() {
        if (StringUtils.isTrimEmpty(targetUrl)) {
            onBackPressed()
            return
        }
        LogUtils.e("targetUrl:->$targetUrl")
        super.initData()

        lifeCyclePlugin = WebLifeCyclePlugin(mAgentWeb)
        lifecycle.addObserver(lifeCyclePlugin)


        webInterfacePlugin = WebInterfacePlugin(mAgentWeb,lifecycle,commonViewModel)
        mAgentWeb.jsInterfaceHolder.addJavaObject(AndroidInterfacePlugin.MOBILE,
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
        return CustomWebViewClient()
    }

    override fun getAgentWebSettings(): IAgentWebSettings<*> {
        return WebSettings()
    }

    override fun getLayoutResId(): Int = R.layout.activity_web

    override fun statusBarSetting() {
        BarUtils.transparentStatusBar(this)
        BarUtils.setStatusBarLightMode(this, true)
    }
}