package com.rongtuoyouxuan.chatlive.stream.view.msgspan

import android.content.Context
import android.text.SpannableStringBuilder
import android.widget.ImageView
import android.widget.TextView
import com.rongtuoyouxuan.chatlive.biz2.model.im.msg.BaseMsg
import com.rongtuoyouxuan.chatlive.biz2.model.im.msg.MessageContent
import com.rongtuoyouxuan.chatlive.biz2.model.im.msg.cmdMsg.LikeAnchorMsg
import com.rongtuoyouxuan.chatlive.biz2.model.im.msg.cmdMsg.LiveJoinRoomMsg
import com.rongtuoyouxuan.chatlive.biz2.model.im.msg.cmdMsg.LiveKickPeopleMsg
import com.rongtuoyouxuan.chatlive.biz2.model.im.msg.ntfmsg.BannedMsg
import com.rongtuoyouxuan.chatlive.biz2.model.im.msg.textmsg.GifMsg
import com.rongtuoyouxuan.chatlive.biz2.model.im.msg.textmsg.GiftMsg
import com.rongtuoyouxuan.chatlive.biz2.model.im.msg.textmsg.TxtMsg
import com.rongtuoyouxuan.chatlive.image.ImgLoader
import com.rongtuoyouxuan.chatlive.log.upload.ULog
import com.rongtuoyouxuan.chatlive.stream.R
import com.rongtuoyouxuan.libuikit.widget.ClickiTextSpan

class MessageSpanMatcher(context: Context) : BaseMessageSpanMatcher(context) {
    private var callBack: MessageCallBack? = null

    override fun loadSpan(textView: TextView, message: BaseMsg) {
        val spanString = SpannableStringBuilder()
        val fontHeight = getFontHeight(textView)
        when (message.itemType) {
            MessageContent.MSG_TEXT.type -> {
                //添加聊天内容
                val txtMsg: TxtMsg = message.body as TxtMsg
                var contentColor = mContext.resources.getColor(R.color.white)
                if (txtMsg.type == 1) {
                    contentColor =
                        mContext.resources.getColor(R.color.c_stream_live_convention)
                } else {
                    addCommonIcons(textView, message, spanString, fontHeight)
                    addNickName(textView, message, spanString, 0, true)
                    contentColor = mContext.resources.getColor(R.color.white)
                }
                if (isSelf(message)) {
                    addTextSpan(spanString, textView, txtMsg.content, contentColor)
                } else {
                    addTextSpan(spanString,
                        textView,
                        txtMsg.content,
                        contentColor,
                        ClickiTextSpan.SpanClick {
                            ""
                        })
                }
                createMsgBackground(textView, message)
            }

            MessageContent.MSG_LIKE_ANCHOR.type -> {//关注主播、给主播点赞、分享主播
                createLikeAnchorMessage(textView, message, spanString, fontHeight)
                createMsgBackground(textView, message)
            }

            MessageContent.MSG_LIVE_JOIN.type -> {
                var contentRes = R.string.stream_live_enter_room
                val liveJoinRoomMsg: LiveJoinRoomMsg = message.body as LiveJoinRoomMsg
                if (liveJoinRoomMsg.userType != 0) {
                    addCommonIcons(textView, message, spanString, fontHeight)
                } else {
                    addRoleDrawableSpan(spanString, 0, fontHeight)
                }
                addNickName(textView, message, spanString, R.color.c_fec421, false)
                addKnownContent(textView, contentRes, spanString, R.color.white)
                createMsgBackground(textView, message)
            }

            MessageContent.MSG_GIFT.type -> {
                createGiftMessage(textView, message, spanString, fontHeight)
                createMsgBackground(textView, message)
            }
            MessageContent.MSG_BANNED.type -> {//禁言
                createBannedMsg(textView, message, spanString)
                createMsgBackground(textView, message)
            }
            MessageContent.MSG_LIVE_KICK.type -> {//移除/拉黑
                var liveKickPeopleMsg: LiveKickPeopleMsg = message.body as LiveKickPeopleMsg
                var content: String
                //1-移出直播间 2-拉黑并踢出直播间
                if (liveKickPeopleMsg.operateType == 1) {
                    if (isSelf(message)) {
                        content = mContext.getString(R.string.chat_remove_live_tip)
                    }else{
                        content = mContext.getString(R.string.chat_remove_live_tip_audience, liveKickPeopleMsg.operateUser.nickname)
                    }
                } else {
                    content = mContext.getString(R.string.chat_balck_live_tip)
                }
                addKnownContent(textView, content, spanString, R.color.white)
                createMsgBackground(textView, message)
            }
        }
        textView.text = spanString
    }

    //gif图
    override fun loadSpan(textView: TextView, img: ImageView, message: BaseMsg) {
        val spanString = SpannableStringBuilder()
        val fontHeight = getFontHeight(textView)
        var gifMsg: GifMsg = message.body as GifMsg
        addCommonIcons(textView, message, spanString, fontHeight)
        ULog.d("clll", "gif:" + gifMsg.from.nickname)
        addNickName(textView, message, spanString, 0, false)
        textView.text = spanString
//        var layoutParams = textView.layoutParams
//        var width = UIUtils.screenWidth(mContext) - UIUtils.dip2px(mContext, 194)
//        if(getTextWidth(spanString.toString(), textView) > width){
//            layoutParams.width = width
//            textView.layoutParams = layoutParams
//            textView.maxEms = 20
//        }
        createMsgBackground(textView, message)
        ImgLoader.with(mContext).load(gifMsg.gifUrl).into(img)
    }

    override fun setItemClickCallBack(callBack: MessageCallBack) {
        this.callBack = callBack
    }

    private fun createBannedMsg(
        textView: TextView,
        message: BaseMsg,
        spanString: SpannableStringBuilder,
    ) {
        val bannedMsg: BannedMsg = message.body as BannedMsg
        when (bannedMsg.bannedType) {//1-全员禁言 2-解除全员禁言 3-单用户禁言 4-单用户解除禁言
            1 -> {
                addNickName(textView, message, spanString, 0, false)
                addKnownContent(textView,
                    R.string.stream_setting_all_banned,
                    spanString,
                    R.color.white)
            }
            2 -> {
                addNickName(textView, message, spanString, 0, false)
                addKnownContent(textView,
                    R.string.stream_setting_all_banned_no,
                    spanString,
                    R.color.white)
            }
            3 -> {
                addKnownContent(textView,
                    mContext.getString(R.string.stream_setting_single_banned,
                        bannedMsg.bannedUsers[0].nickname,
                        mContext.getString(R.string.stream_anchor)),
                    spanString,
                    R.color.white)
            }
            4 -> {
                addKnownContent(textView,
                    mContext.getString(R.string.stream_setting_single_banned_no,
                        bannedMsg.bannedUsers[0].nickname,
                        mContext.getString(R.string.stream_anchor)),
                    spanString,
                    R.color.white)
            }
        }
    }

    private fun createLikeAnchorMessage(
        textView: TextView,
        message: BaseMsg,
        spanString: SpannableStringBuilder,
        fontHeight: Int
    ) {
        addCommonIcons(textView, message, spanString, fontHeight)
        val nickColor = if (isSelf(message)) R.color.c_0cdbf3 else R.color.c_fec421
        addNickName(textView, message, spanString, nickColor, false)
        var likeAnchorMsg: LikeAnchorMsg = message.body as LikeAnchorMsg
        //1-follow关注主播 2-点赞主播 3-分享主播
        var content: String = ""
        when (likeAnchorMsg.likeType) {
            1 -> content = mContext.getString(R.string.stream_followed)
            2 -> content = mContext.getString(R.string.stream_zaned)
            3 -> content = mContext.getString(R.string.stream_shared)
        }
        addKnownContent(textView, content, spanString, R.color.c_stream_msg_nick_name)
    }

    private fun createGiftMessage(
        textView: TextView,
        message: BaseMsg,
        spanString: SpannableStringBuilder,
        fontHeight: Int,
    ) {
        //添加礼物文案
        var giftMsg: GiftMsg = message.body as GiftMsg
        var gitftext = String.format(
            mContext.getString(
                R.string.chat_send_gift_tip_4,
                giftMsg.giftName))

        addCommonIcons(textView, message, spanString, fontHeight)
        addNickName(textView, message, spanString, false)
        addKnownContent(textView, gitftext, spanString, R.color.c_stream_msg_common)
        addGiftImg(fontHeight, textView, spanString, giftMsg)
        addKnownContent(textView,
            "×" + giftMsg.num.toString(),
            spanString,
            R.color.c_stream_msg_common)

//        if(getTextWidth(spanString.toString(), textView) == width){

//        }
//        var layoutParams = textView.layoutParams
//        var dimen = UIUtils.px2dip(mContext, mContext.resources.getDimension(R.dimen.dp_119))
//        var width = UIUtils.screenWidth(mContext) - UIUtils.dip2px(mContext, dimen)
//        layoutParams.width = width
//        textView.layoutParams = layoutParams
//        ULog.d("cllll", "礼物textview 长度：" + getTextWidth(spanString.toString(), textView))
    }

}
