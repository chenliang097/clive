package com.rongtuoyouxuan.chatlive.stream.view.dialog

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import com.blankj.utilcode.util.StringUtils
import com.blankj.utilcode.util.Utils
import com.rongtuoyouxuan.chatlive.databus.DataBus
import com.rongtuoyouxuan.chatlive.image.util.GlideUtils
import com.rongtuoyouxuan.chatlive.stream.R
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.enums.PopupAnimation
import com.lxj.xpopup.impl.FullScreenPopupView
import kotlinx.android.synthetic.main.dialog_follow_fans.view.*

/*
*Create by {Mr秦} on 2022/10/20
*/
@SuppressLint("ViewConstructor")
class FollowFansDialog(
    context: Context,
    private val userName: String?,
    val avatar: String?,
    val type: String,
    val callBack: Utils.Consumer<String>?,
) : FullScreenPopupView(context) {
    override fun getImplLayoutId(): Int {
        return R.layout.dialog_follow_fans
    }

    override fun onCreate() {
        rl_root.setOnClickListener {
            dismiss()
        }
        GlideUtils.loadCircleImage(context, avatar, im,
            R.drawable.shape_image_placeholder)
        username.text = userName ?: ""
        when (type) {
            FOLLOW -> {
                btn_im.visibility = View.GONE
                content.text = StringUtils.getString(R.string.live_public_chat_area_follow_tv)
                btn_tv.text = StringUtils.getString(R.string.stream_user_card_follow)
            }
            FANS -> {
                btn_im.visibility = View.VISIBLE
                content.text = StringUtils.getString(R.string.live_public_chat_area_fans)
                btn_tv.text = StringUtils.getString(R.string.live_public_chat_area_join)
            }
        }
        container.setOnClickListener {
            callBack?.accept(type)
            if (!DataBus.instance().isVisitor) {
                dismiss()
            }
        }
    }

    companion object {
        const val FANS = "fans"//粉丝团
        const val FOLLOW = "follow"//关注
        fun showDialog(
            context: Context,
            userName: String?,
            avatar: String?,
            type: String,
            callBack: Utils.Consumer<String>?,
        ) {
            XPopup.Builder(context)
                .dismissOnTouchOutside(true)
                .dismissOnBackPressed(true)
                .hasNavigationBar(true)
                .hasStatusBar(true)
                .popupAnimation(PopupAnimation.TranslateAlphaFromBottom)
                .isViewMode(true)
                .hasShadowBg(false)
                .asCustom(FollowFansDialog(context, userName, avatar, type, callBack))
                .show()
        }
    }

}