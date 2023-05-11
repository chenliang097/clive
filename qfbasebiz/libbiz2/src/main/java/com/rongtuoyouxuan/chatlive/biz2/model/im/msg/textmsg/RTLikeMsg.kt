package com.rongtuoyouxuan.chatlive.biz2.model.im.msg.textmsg

import com.google.gson.annotations.SerializedName
import com.rongtuoyouxuan.chatlive.biz2.model.im.BaseRoomMessage

class RTLikeMsg: BaseRoomMessage() {
    @SerializedName("like_count")
    var likeCount = 0L


    override val itemType: Int
        get() = TYPE_LIKE

    class GiftExtra {
        var type = 0
        var num = 0
    }
}