package com.rongtuoyouxuan.chatlive.biz2.model.im

import com.chad.library.adapter.base.entity.MultiItemEntity
import com.google.gson.annotations.SerializedName

abstract class BaseRoomMessage : MultiItemEntity {
    companion object {
        const val TYPE_UNKNOWN = 0
        const val TYPE_MESSAGE = 1
        const val TYPE_GIFT = 2
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
}