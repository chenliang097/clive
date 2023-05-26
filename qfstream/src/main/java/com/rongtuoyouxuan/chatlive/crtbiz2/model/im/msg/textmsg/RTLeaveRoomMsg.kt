package com.rongtuoyouxuan.chatlive.crtbiz2.model.im.msg.textmsg

import com.rongtuoyouxuan.chatlive.crtbiz2.model.im.BaseRoomMessage

class RTLeaveRoomMsg: BaseRoomMessage() {


    override val itemType: Int
        get() = TYPE_OUT_ROOM
}