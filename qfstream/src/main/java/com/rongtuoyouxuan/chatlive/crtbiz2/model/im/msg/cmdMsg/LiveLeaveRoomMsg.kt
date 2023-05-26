package com.rongtuoyouxuan.chatlive.crtbiz2.model.im.msg.cmdMsg

import com.rongtuoyouxuan.chatlive.crtbiz2.model.im.msg.BaseMsg
import com.google.gson.annotations.SerializedName

/**
 *
 * date:2022/8/18-10:37
 * des: 27. 用户离开直播间(主动离开、踢人(移除)、拉黑)
 */
class LiveLeaveRoomMsg : BaseMsg.MsgBody() {
    @SerializedName("room_id")
    val roomId: Long = 0L

    val total: Long = 0
}