package com.rongtuoyouxuan.chatlive.stream.view.msgspan

import android.content.Context
import android.text.SpannableStringBuilder
import android.widget.ImageView
import android.widget.TextView
import com.rongtuoyouxuan.chatlive.base.DialogUtils
import com.rongtuoyouxuan.chatlive.biz2.model.im.BaseRoomMessage
import com.rongtuoyouxuan.chatlive.biz2.model.im.msg.BaseMsg
import com.rongtuoyouxuan.chatlive.biz2.model.im.msg.MessageContent
import com.rongtuoyouxuan.chatlive.biz2.model.im.msg.cmdMsg.LikeAnchorMsg
import com.rongtuoyouxuan.chatlive.biz2.model.im.msg.cmdMsg.LiveJoinRoomMsg
import com.rongtuoyouxuan.chatlive.biz2.model.im.msg.cmdMsg.LiveKickPeopleMsg
import com.rongtuoyouxuan.chatlive.biz2.model.im.msg.ntfmsg.BannedMsg
import com.rongtuoyouxuan.chatlive.biz2.model.im.msg.textmsg.*
import com.rongtuoyouxuan.chatlive.image.ImgLoader
import com.rongtuoyouxuan.chatlive.log.upload.ULog
import com.rongtuoyouxuan.chatlive.stream.R
import com.rongtuoyouxuan.libuikit.widget.ClickiTextSpan

class MessageSpanMatcher(context: Context) : BaseMessageSpanMatcher(context) {
    private var callBack: MessageCallBack? = null

    override fun loadSpan(textView: TextView, message: BaseRoomMessage) {
        val spanString = SpannableStringBuilder()
        val fontHeight = getFontHeight(textView)
        when (message.itemType) {
            BaseRoomMessage.TYPE_MESSAGE -> {
                //添加聊天内容
                val txtMsg: RTTxtMsg = message as RTTxtMsg
                //添加聊天内容
                val message = txtMsg as RTTxtMsg
                var contentColor = mContext.resources.getColor(R.color.white)
                if (txtMsg.type == 1) {
                    contentColor =
                        mContext.resources.getColor(R.color.c_stream_live_convention)
                } else {
                    addCommonIcons(textView, txtMsg, spanString, fontHeight)
                    addNickName(textView, txtMsg, spanString, mContext.resources.getColor(R.color.c_stream_msg_nick_name), true, true)
                    contentColor = mContext.resources.getColor(R.color.white)
                }
                if (isSelf(message)) {
                    addTextSpan(spanString, textView, message.content, contentColor)
                } else {
                    addTextSpan(spanString, textView, message.content, contentColor, ClickiTextSpan.SpanClick {
                        DialogUtils.createReportDialog(mContext, message.roomIdStr, message.userIdStr, message.userName, message.content).show()
                    })
                }
                createMsgBackground(textView, txtMsg)
            }

            BaseRoomMessage.TYPE_ANNOUNCE -> {
                //添加聊天内容
                val announceMsg: RTAnnounceMsg = message as RTAnnounceMsg
                var contentColor =
                        mContext.resources.getColor(R.color.c_stream_live_convention)

                if (isSelf(message)) {
                    addTextSpan(spanString, textView, announceMsg.announce, contentColor)
                } else {
                    addTextSpan(spanString, textView, announceMsg.announce, contentColor, ClickiTextSpan.SpanClick {
                    })
                }
                createMsgBackground(textView, announceMsg)
            }

            BaseRoomMessage.TYPE_ENTER_ROOM -> {
                val enterMsg: RTEnterRoomMsg = message as RTEnterRoomMsg
                var contentColor =
                    mContext.resources.getColor(R.color.c_stream_msg_nick_name)
                addNickName(textView, enterMsg, spanString, mContext.resources.getColor(R.color.c_stream_enter_msg_nick_name), false, false)
                if (isSelf(message)) {
                    addTextSpan(spanString, textView, mContext.getString(R.string.stream_msg_enter_room), contentColor)
                } else {
                    addTextSpan(spanString, textView, mContext.getString(R.string.stream_msg_enter_room), contentColor, ClickiTextSpan.SpanClick {
                    })
                }
                createMsgBackground(textView, enterMsg)
            }

            BaseRoomMessage.TYPE_OUT_ROOM -> {
                val leaveRoomMsg: RTLeaveRoomMsg = message as RTLeaveRoomMsg
                var contentColor =
                    mContext.resources.getColor(R.color.c_stream_msg_nick_name)
                addNickName(textView, leaveRoomMsg, spanString, mContext.resources.getColor(R.color.c_stream_enter_msg_nick_name), false, false)
                if (isSelf(message)) {
                    addTextSpan(spanString, textView, mContext.getString(R.string.stream_msg_out_room), contentColor)
                } else {
                    addTextSpan(spanString, textView, mContext.getString(R.string.stream_msg_out_room), contentColor, ClickiTextSpan.SpanClick {
                    })
                }
                createMsgBackground(textView, leaveRoomMsg)
            }

            BaseRoomMessage.TYPE_FOLLOW -> {
                val followMsg: RTFollowMsg = message as RTFollowMsg
                var contentColor =
                    mContext.resources.getColor(R.color.c_stream_msg_nick_name)
                followMsg.userName = followMsg.fromNickName
                addNickName(textView, followMsg, spanString, mContext.resources.getColor(R.color.c_stream_enter_msg_nick_name), false, false)
                if (isSelf(message)) {
                    addTextSpan(spanString, textView, mContext.getString(R.string.stream_msg_follow_anchor), contentColor)
                } else {
                    addTextSpan(spanString, textView, mContext.getString(R.string.stream_msg_follow_anchor), contentColor, ClickiTextSpan.SpanClick {
                    })
                }
                createMsgBackground(textView, followMsg)
            }

            BaseRoomMessage.TYPE_BANNED -> {
                val msg: RTBannedMsg = message as RTBannedMsg
                var contentColor =
                    mContext.resources.getColor(R.color.c_stream_msg_nick_name)
                if (isSelf(message)) {
                    addTextSpan(spanString, textView, mContext.getString(R.string.stream_msg_banner), contentColor)
                } else {
                    addTextSpan(spanString, textView, mContext.getString(R.string.stream_msg_banner), contentColor, ClickiTextSpan.SpanClick {
                    })
                }
                createMsgBackground(textView, msg)
            }

            BaseRoomMessage.TYPE_BANNED_RELIEVE -> {
                val msg: RTBannedRelieveMsg = message as RTBannedRelieveMsg
                var contentColor =
                    mContext.resources.getColor(R.color.c_stream_msg_nick_name)
                if (isSelf(message)) {
                    addTextSpan(spanString, textView, mContext.getString(R.string.stream_msg_banner_relieve), contentColor)
                } else {
                    addTextSpan(spanString, textView, mContext.getString(R.string.stream_msg_banner_relieve), contentColor, ClickiTextSpan.SpanClick {
                    })
                }
                createMsgBackground(textView, msg)
            }

            BaseRoomMessage.TYPE_ROOM_MANAGER_ADD -> {
                val msg: RTRoomManagerAddMsg = message as RTRoomManagerAddMsg
                var contentColor =
                    mContext.resources.getColor(R.color.c_stream_msg_nick_name)
                if (isSelf(message)) {
                    addTextSpan(spanString, textView, mContext.getString(R.string.stream_msg_room_manager), contentColor)
                } else {
                    addTextSpan(spanString, textView, mContext.getString(R.string.stream_msg_room_manager), contentColor, ClickiTextSpan.SpanClick {
                    })
                }
                createMsgBackground(textView, msg)
            }

            BaseRoomMessage.TYPE_ROOM_MANAGER_RELIEVE -> {
                val msg: RTRoomManagerRelieveMsg = message as RTRoomManagerRelieveMsg
                var contentColor =
                    mContext.resources.getColor(R.color.c_stream_msg_nick_name)
                if (isSelf(message)) {
                    addTextSpan(spanString, textView, mContext.getString(R.string.stream_msg_room_manager_relieve), contentColor)
                } else {
                    addTextSpan(spanString, textView, mContext.getString(R.string.stream_msg_room_manager_relieve), contentColor, ClickiTextSpan.SpanClick {
                    })
                }
                createMsgBackground(textView, msg)
            }
        }
        textView.text = spanString

    }

    override fun loadSpan(textView: TextView, img: ImageView, message: BaseRoomMessage) {
    }

    override fun setItemClickCallBack(callBack: MessageCallBack) {
        this.callBack = callBack
    }

//    private fun createBannedMsg(
//        textView: TextView,
//        message: BaseRoomMessage,
//        spanString: SpannableStringBuilder,
//    ) {
//        val bannedMsg: BannedMsg = message as BannedMsg
//        when (bannedMsg.bannedType) {//1-全员禁言 2-解除全员禁言 3-单用户禁言 4-单用户解除禁言
//            1 -> {
//                addNickName(textView, message, spanString, 0, false)
//                addKnownContent(textView,
//                    R.string.stream_setting_all_banned,
//                    spanString,
//                    R.color.white)
//            }
//            2 -> {
//                addNickName(textView, message, spanString, 0, false)
//                addKnownContent(textView,
//                    R.string.stream_setting_all_banned_no,
//                    spanString,
//                    R.color.white)
//            }
//            3 -> {
//                addKnownContent(textView,
//                    mContext.getString(R.string.stream_setting_single_banned,
//                        bannedMsg.bannedUsers[0].nickname,
//                        mContext.getString(R.string.stream_anchor)),
//                    spanString,
//                    R.color.white)
//            }
//            4 -> {
//                addKnownContent(textView,
//                    mContext.getString(R.string.stream_setting_single_banned_no,
//                        bannedMsg.bannedUsers[0].nickname,
//                        mContext.getString(R.string.stream_anchor)),
//                    spanString,
//                    R.color.white)
//            }
//        }
//    }

}

