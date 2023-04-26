package com.rongtuoyouxuan.libuikit.widget

import android.annotation.SuppressLint
import android.content.Context
import androidx.core.view.isVisible
import com.blankj.utilcode.util.StringUtils
import com.rongtuoyouxuan.libuikit.R
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.core.BottomPopupView
import kotlinx.android.synthetic.main.dialog_confirm_bottom_sheet.view.*

/*
*Create by {Mr秦} on 2022/7/20
*/
@SuppressLint("ViewConstructor")
class ConfirmBottomSheetDialog(
    context: Context,
    val content0: String?,
    val content1: String?,
    val content2: String?,
    val content3: String?,
    val cancel: String?,
    val callBack: CallBack?,
) : BottomPopupView(context) {

    override fun onCreate() {
        tv_content0.text = content0
        tv_content1.text = content1
        tv_content2.text = content2
        tv_content3.text = content3
        tv_cancel.text = cancel

        container_tv_content0.isVisible = !StringUtils.isTrimEmpty(content0)
        container_tv_content1.isVisible = !StringUtils.isTrimEmpty(content1)
        container_tv_content2.isVisible = !StringUtils.isTrimEmpty(content2)
        container_tv_content3.isVisible = !StringUtils.isTrimEmpty(content3)

        container_tv_content0.setOnClickListener {
            callBack?.callBack(centerItem0)
            dismiss()
        }
        container_tv_content1.setOnClickListener {
            callBack?.callBack(centerItem1)
            dismiss()
        }
        container_tv_content2.setOnClickListener {
            callBack?.callBack(centerItem2)
            dismiss()
        }
        container_tv_content3.setOnClickListener {
            callBack?.callBack(centerItem3)
            dismiss()
        }
        container_cancel.setOnClickListener {
            callBack?.cancel()
            dismiss()
        }
    }


    companion object {
        const val centerItem0: Int = 0
        const val centerItem1: Int = 1
        const val centerItem2: Int = 2
        const val centerItem3: Int = 3

        fun showDialog(
            context: Context,
            content0: String? = null,
            content1: String? = null,
            content2: String? = null,
            content3: String? = null,
            cancel: String? = null,
            callBack: CallBack? = null,
        ) {
            XPopup.Builder(context)
                .dismissOnTouchOutside(true)
                .dismissOnBackPressed(true)
                .hasNavigationBar(false)
                .hasStatusBar(false)
                .isViewMode(true)
                .hasShadowBg(true)
                .asCustom(
                    ConfirmBottomSheetDialog(
                        context,
                        content0,
                        content1,
                        content2,
                        content3,
                        cancel,
                        callBack
                    )
                )
                .show()
        }
    }

    override fun getImplLayoutId(): Int = R.layout.dialog_confirm_bottom_sheet


    interface CallBack {
        fun callBack(value: Int)
        fun cancel()
    }
}