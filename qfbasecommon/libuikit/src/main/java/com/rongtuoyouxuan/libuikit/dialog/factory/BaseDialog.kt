package com.rongtuoyouxuan.libuikit.dialog.factory

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import com.rongtuoyouxuan.chatlive.util.UIUtils
import com.rongtuoyouxuan.libuikit.R

class BaseDialog constructor(context: Context,
                             private val render: DialogRender,
                             private val config: Config = Config()) : Dialog(context, config.themeResId) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val rootView: View = (context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
                .inflate(render.getLayoutResId(), null)
        setContentView(rootView)
        setWindowLocation()
        render.render(savedInstanceState, rootView, this)
    }

    private fun setWindowLocation() {
        val win = this.window ?: return
        win.decorView.setPadding(0, 0, 0, 0)
        val lp = win.attributes
        lp.width = if (config.widthFactor > 0) (UIUtils.screenWidth(context) * config.widthFactor).toInt()
        else config.widthFactor.toInt()
        lp.height = if (config.heightFactor > 0) (UIUtils.screenHeight(context) * config.heightFactor).toInt()
        else config.heightFactor.toInt()

        lp.gravity = config.gravity
        win.attributes = lp
        win.setWindowAnimations(R.style.pl_libutil_AnimBottom)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        render.onAttachedToWindow()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        render.onDetachedFromWindow()
    }

    override fun dismiss() {
        super.dismiss()
        render.dismiss()
    }

    data class Config(
            /* 屏幕高度占比*/
            val heightFactor: Float = 0.5F,
            /* 屏幕高度占比*/
            val widthFactor: Float = 0.8F,
            /* gravity */
            val gravity: Int = Gravity.BOTTOM,
            /* Theme */
            val themeResId: Int = R.style.pl_libutil_BottomDialog) {
    }
}