package com.rongtuoyouxuan.chatlive.qfcommon.dialog

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import com.blankj.utilcode.util.*
import com.rongtuoyouxuan.chatlive.crtbiz2.constanst.Config
import com.rongtuoyouxuan.chatlive.crtbiz2.constanst.SpConfig
import com.rongtuoyouxuan.chatlive.crtutil.util.LaToastUtil
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.enums.PopupAnimation
import com.lxj.xpopup.impl.FullScreenPopupView
import com.rongtuoyouxuan.chatlive.crtdatabus.DataBus
import com.rongtuoyouxuan.chatlive.stream.R
import kotlinx.android.synthetic.main.dialog_share.view.*

/*
*Create by {Mr秦} on 2022/8/30
*/
@SuppressLint("ViewConstructor")
class ShareDialog(
    context: Context,
    var content: String?,
    var shareLinkUrl: String?,
    var callBack: com.rongtuoyouxuan.chatlive.qfcommon.dialog.ShareDialog.CallBack?,
) : FullScreenPopupView(context) {



    override fun onCreate() {
        initView()
        initListener()

    }

    private fun initView() {
    }

    override fun dismiss() {
        callBack?.dismiss()
        super.dismiss()
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    }

    private fun initListener() {
        container_facebook.setOnClickListener {
            if (!DebouncingUtils.isValid(it)) return@setOnClickListener
            val channelName = Config.PLAT_FACEBOOK
            onEvent(channelName)
            callBack?.itemClick(channelName)
        }
        container_twitter.setOnClickListener {
            if (!DebouncingUtils.isValid(it)) return@setOnClickListener
            val channelName = Config.PLAT_TWITTER
            onEvent(channelName)
            callBack?.itemClick(channelName)
        }
        container_line.setOnClickListener {
            if (!DebouncingUtils.isValid(it)) return@setOnClickListener
            val channelName = Config.PLAT_LINE
            onEvent(channelName)
            callBack?.itemClick(channelName)
        }
        container_copy_link.setOnClickListener {
            if (!DebouncingUtils.isValid(it)) return@setOnClickListener
            if (!StringUtils.isTrimEmpty(shareLinkUrl)) {
                ClipboardUtils.copyText(shareLinkUrl)
                LaToastUtil.showShort(StringUtils.getString(R.string.rt_copy_suc))
            }
        }

        root.setOnClickListener {
            dismiss()
        }

    }

    private fun onEvent(st: String) {
        val spKey =
            "share_${st}_${DataBus.instance().userInfo.value?.user_info?.userId}"
        if (SPUtils
                .getInstance(SpConfig.SP_SAVE)
                .getBoolean(spKey,
                    false)
        ) return

        SPUtils
            .getInstance(SpConfig.SP_SAVE).put(spKey, true, true)

    }

    override fun getImplLayoutId(): Int {
        return R.layout.dialog_share
    }

    companion object {
        /**
         * 网站链接和文本分享
         * content 文本
         * shareLinkUrl
         */
        fun showDialog(
            context: Context,
            content: String?,
            shareLinkUrl: String?,
            callBack: com.rongtuoyouxuan.chatlive.qfcommon.dialog.ShareDialog.CallBack?,
        ): com.rongtuoyouxuan.chatlive.qfcommon.dialog.ShareDialog {
            val dialog = com.rongtuoyouxuan.chatlive.qfcommon.dialog.ShareDialog(
                context,
                content,
                shareLinkUrl,
                callBack
            )
            XPopup.Builder(context)
                .dismissOnTouchOutside(true)
                .dismissOnBackPressed(true)
                .hasNavigationBar(false)
                .hasStatusBar(false)
                .popupAnimation(PopupAnimation.TranslateAlphaFromBottom)
                .isViewMode(true)
                .hasShadowBg(true)
                .asCustom(dialog)
                .show()
            return dialog
        }
    }

    interface CallBack {
        fun itemClick(channel: String)
        fun dismiss()
    }
}