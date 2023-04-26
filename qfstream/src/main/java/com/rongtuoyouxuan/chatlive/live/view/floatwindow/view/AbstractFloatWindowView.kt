package com.rongtuoyouxuan.chatlive.live.view.floatwindow.view

import android.content.Context
import android.content.Intent
import android.widget.FrameLayout
import com.rongtuoyouxuan.chatlive.live.view.ZegoLiveplay

abstract class AbstractFloatWindowView(context: Context,
                                       param: Intent,
                                       private val exitFloatWindow: (() -> Unit)) : FrameLayout(context) {

    val anchorId: String = param.getStringExtra(ZegoLiveplay.ANCHORID) ?: ""
    val streamId: String = param.getStringExtra(ZegoLiveplay.STREAM_ID) ?: ""
//    val baseUrl: String = param.getStringExtra(ZegoLiveplay.BASE_URL) ?: ""
//    val authParams: String = param.getStringExtra(ZegoLiveplay.AUTH_PARAMS) ?: ""
//    val fromSource = param.getStringExtra(ZegoLiveplay.FROAM_SOURCE)

    open fun getLogTag(): String = "AbstractFloatWindowView"

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
    }

    open fun exit() {
        exitFloatWindow.invoke()
    }
}

