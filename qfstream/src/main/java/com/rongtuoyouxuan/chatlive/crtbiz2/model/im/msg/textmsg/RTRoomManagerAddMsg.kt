package com.rongtuoyouxuan.chatlive.crtbiz2.model.im.msg.textmsg

import com.google.gson.annotations.SerializedName
import com.rongtuoyouxuan.chatlive.crtbiz2.model.im.BaseRoomMessage

class RTRoomManagerAddMsg: BaseRoomMessage() {
    @SerializedName("room_admin_id")
    var roomAdminId = ""
    override val itemType: Int
        get() = TYPE_ROOM_MANAGER_ADD
}