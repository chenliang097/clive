package com.rongtuoyouxuan.libuikit.dialog

import android.content.Context
import android.graphics.Color
import androidx.annotation.StringRes
import com.rongtuoyouxuan.libuikit.R
import com.rongtuoyouxuan.libuikit.dialog.factory.DialogFactory.createCenterDialog

class ConfirmDialog private constructor(
    @StringRes val content: Int,
    val okClick: () -> Unit = {},
    val cancelClick: () -> Unit = {},
    @StringRes val cancel: Int = R.string.cancel,
    @StringRes val ok: Int = R.string.alert_dialog_ok
) {
    companion object {
        @JvmStatic
        @JvmOverloads
        fun create(
            @StringRes content: Int,
            okClick: () -> Unit = {},
            cancelClick: () -> Unit = {},
            @StringRes cancel: Int = R.string.cancel,
            @StringRes ok: Int = R.string.alert_dialog_ok
        ): ConfirmDialog = ConfirmDialog(content, okClick, cancelClick, cancel, ok)
    }

    val commonSystemDialogRender = CommonSystemDialogRender(R.layout.common_sysytem_dialog_style_1)

    @JvmOverloads
    fun show(context: Context, cancelable: Boolean = true) {
        commonSystemDialogRender.setContent(context.resources.getString(content), Color.parseColor("#1D212C"))
            .setCancelAction(context.resources.getString(cancel), Color.parseColor("#A6B0BD")) { _, _ ->
                cancelClick()
            }.setOkAction(
                context.resources.getString(ok), Color.parseColor("#5BE2B2")
            ) { _, _ ->
                okClick()
            }
        createCenterDialog(context, commonSystemDialogRender).apply {
            setCancelable(cancelable)
        }.show()
    }

}