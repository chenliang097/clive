package com.rongtuoyouxuan.libuikit.dialog.factory

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.WindowManager
import com.rongtuoyouxuan.libuikit.R

object DialogFactory {
    /* 普遍80%宽度 */
    private const val WIDTH_FACTOR = 0.8F

    @JvmOverloads
    @JvmStatic
    fun createBottomDialog(context: Context,
                           render: DialogRender,
                           config: BaseDialog.Config = BaseDialog.Config(
                                   heightFactor = 0.75F,
                                   widthFactor = WindowManager.LayoutParams.MATCH_PARENT.toFloat())
    ): Dialog = BaseDialog(context, render, config)

    @JvmOverloads
    @JvmStatic
    fun createCenterDialog(context: Context,
                           render: DialogRender,
                           config: BaseDialog.Config = BaseDialog.Config(
                                   heightFactor = WindowManager.LayoutParams.WRAP_CONTENT.toFloat(),
                                   gravity = Gravity.CENTER,
                                   widthFactor = WIDTH_FACTOR,
                                   themeResId = R.style.commenDialogStyle)
    ): Dialog = BaseDialog(context, render, config)
}