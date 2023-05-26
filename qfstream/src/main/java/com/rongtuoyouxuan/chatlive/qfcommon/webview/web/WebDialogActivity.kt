package com.rongtuoyouxuan.chatlive.qfcommon.webview.web

import android.content.Intent
import android.view.Gravity
import android.view.ViewGroup
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.StringUtils
import com.rongtuoyouxuan.chatlive.crtutil.util.UIUtils
import com.just.agentweb.IAgentWebSettings
import com.just.agentweb.WebViewClient
import com.rongtuoyouxuan.chatlive.crtrouter.constants.RouterConstant
import com.rongtuoyouxuan.chatlive.crtrouter.constants.RouterParams
import com.rongtuoyouxuan.chatlive.stream.R
import kotlinx.android.synthetic.main.activity_web.*

@Route(path = RouterConstant.PATH_WEB_DIALOG)
class WebDialogActivity : com.rongtuoyouxuan.chatlive.qfcommon.webview.base.BaseWebActivity() {
    @JvmField
    @Autowired(name = RouterParams.URL)
    var targetUrl: String = "" //url 路由

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

//        var webView = mAgentWeb?.webCreator?.webView
//        var layoutParams = webView?.layoutParams as FrameLayout.LayoutParams
//        layoutParams.topMargin = UIUtils.dip2px(this, -25)
//        webView.layoutParams = layoutParams

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

    override fun getLayoutResId(): Int{
        setWindowLocation()
        return R.layout.activity_web
    }

    override fun statusBarSetting() {
//        BarUtils.setStatusBarLightMode(this, true)
//        BarUtils.addMarginTopEqualStatusBarHeight(mRootView)
    }

    private fun setWindowLocation() {
        val win = this.window
        win.decorView.setPadding(0, 0, 0, 0)
        val lp = win.attributes
        lp.width = (UIUtils.screenWidth(this))
        lp.height = (UIUtils.screenHeight(this) * 0.6).toInt()
        lp.gravity = Gravity.BOTTOM //设置对话框底部显示
        win.attributes = lp
        win?.decorView?.setBackgroundResource(R.drawable.corner_circle_white_top)
        win?.setWindowAnimations(R.style.CommonDialogStyleAnimation)
    }
}