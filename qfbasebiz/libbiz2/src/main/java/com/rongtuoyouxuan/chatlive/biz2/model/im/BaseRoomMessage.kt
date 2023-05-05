package com.rongtuoyouxuan.chatlive.biz2.model.im

import com.chad.library.adapter.base.entity.MultiItemEntity
import com.google.gson.annotations.SerializedName

abstract class BaseRoomMessage : MultiItemEntity {
    companion object {
        const val TYPE_UNKNOWN = 0
        const val TYPE_MESSAGE = 2001
        const val TYPE_GIFT = 2
        const val TYPE_ANNOUNCE = 2002
        const val TYPE_ENTER_ROOM = 2007
        const val TYPE_HOT_CHANGE = 2013
        const val TYPE_COMMON_TEMPLATEMSG = 13 // 模板消息

    }
    @SerializedName("user_id")
    var userId = ""
    @SerializedName("user_name")
    var userName = ""
    @SerializedName("is_super_admin")
    var isSuperAdmin = false
    @SerializedName("is_room_admin")
    var isRoomAdmin = false
    @SerializedName("is_anchor")
    var isAnchor = false
    @SerializedName("user_id_str")
    var userIdStr = ""
    @SerializedName("room_id")
    var roomId = ""
    @SerializedName("room_id_str")
    var roomIdStr = ""
}