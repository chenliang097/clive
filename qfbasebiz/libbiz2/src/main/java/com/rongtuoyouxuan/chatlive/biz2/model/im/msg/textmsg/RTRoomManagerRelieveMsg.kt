package com.rongtuoyouxuan.chatlive.biz2.model.im.msg.textmsg

import com.google.gson.annotations.SerializedName
import com.rongtuoyouxuan.chatlive.biz2.model.im.BaseRoomMessage

class RTRoomManagerRelieveMsg: BaseRoomMessage() {

    override val itemType: Int
        get() = TYPE_ROOM_MANAGER_RELIEVE
}