package com.rongtuoyouxuan.chatlive.crtbiz2.model.im

import com.chad.library.adapter.base.entity.MultiItemEntity
import com.google.gson.annotations.SerializedName

abstract class BaseRoomMessage : MultiItemEntity {
    companion object {
        const val TYPE_UNKNOWN = 0
        const val TYPE_MESSAGE = 1001
        const val TYPE_GIFT = 4001
        const val TYPE_ANNOUNCE = 2002
        const val TYPE_LIVE_END = 2004
        const val TYPE_LIKE = 2005
        const val TYPE_ANCHOR_LIVE_END = 2006
        const val TYPE_ENTER_ROOM = 2007
        const val TYPE_OUT_ROOM = 2008
        const val TYPE_FOLLOW = 2010
        const val TYPE_HOT_CHANGE = 2013
        const val TYPE_ENTER_ROOM_TO_SERVER = 2020
        const val TYPE_BANNED = 3001
        const val TYPE_BANNED_RELIEVE = 3002
        const val TYPE_ROOM_MANAGER_ADD = 3003
        const val TYPE_ROOM_MANAGER_RELIEVE = 3006
        const val TYPE_ROOM_BLACK = 3004

        const val TYPE_COMMON_TEMPLATEMSG = 13 // 模板消息

    }
    @SerializedName("user_id")
    var userId = 0
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
    var roomId = 0
    @SerializedName("room_id_str")
    var roomIdStr = ""
}