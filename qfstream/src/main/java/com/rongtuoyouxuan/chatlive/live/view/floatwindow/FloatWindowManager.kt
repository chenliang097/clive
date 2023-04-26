package com.rongtuoyouxuan.chatlive.live.view.floatwindow

import android.content.Context
import android.content.Intent
import com.rongtuoyouxuan.chatlive.databus.DataBus
import com.rongtuoyouxuan.chatlive.databus.R
import com.rongtuoyouxuan.chatlive.live.view.ZegoLiveplay
import com.rongtuoyouxuan.chatlive.live.view.floatwindow.view.AbstractFloatWindowView
import com.rongtuoyouxuan.chatlive.live.view.floatwindow.view.VideoFloatWindow
import com.rongtuoyouxuan.chatlive.util.DisplayUtil

object FloatWindowManager {

    private var width = DisplayUtil.dipToPixels(DataBus.instance().appContext, 100F)
    private var height = DisplayUtil.dipToPixels(DataBus.instance().appContext, 140F)
    private val screenWidth by lazy {
        DataBus.instance().appContext.resources.displayMetrics.widthPixels
    }
    private val screenHeight by lazy {
        DataBus.instance().appContext.resources.displayMetrics.heightPixels
    }
    private var floatWindowView: AbstractFloatWindowView? = null
    private val floatingWindowHelper by lazy {
        FloatingWindowHelper(DataBus.instance().appContext)
    }

    /**
     *  context : Context
     */
    @JvmStatic
    fun showVideoFloatWindow(context: Context, param: Intent, exit: (() -> Unit)) {
        width = context.resources.getDimensionPixelSize(R.dimen.dp_100)
        height = context.resources.getDimensionPixelSize(R.dimen.dp_140)

        removeFloatWindowView()
        floatWindowView = VideoFloatWindow(context, param) {
            removeFloatWindowView()
            exit.invoke()
        }
        //屏幕右侧中间位置
        val x = screenWidth - width
        val y = (screenHeight - height) / 2
        floatingWindowHelper.addView(floatWindowView, x, y, true, width, height)
    }

    @JvmStatic
    fun hideVideoFloatWindow(context: Context, exit: (() -> Unit)) {
        ZegoLiveplay.instance.onFloatViewDestroy()
        removeFloatWindowView()
        exit.invoke()
    }

    /**
     * 删除 float window view
     */
    fun removeFloatWindowView() {
        floatWindowView?.apply {
            floatingWindowHelper.removeView(floatWindowView)
        }
        floatWindowView = null
    }
}