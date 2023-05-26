package com.rongtuoyouxuan.chatlive.crtbiz2.model.im.msg.textmsg

import com.google.gson.annotations.SerializedName
import com.rongtuoyouxuan.chatlive.crtbiz2.model.im.BaseRoomMessage

class RTGiftMsg: BaseRoomMessage() {
    @SerializedName("avatar")
    var avatar = ""
    @SerializedName("gift_id")
    var giftId = 0
    @SerializedName("gift_name")
    var giftName = ""
    @SerializedName("value")
    var value = 0
    @SerializedName("num")
    var num = 0
    @SerializedName("url_1x")
    var url_1x = ""
    @SerializedName("url_2x")
    var url_2x = ""
    @SerializedName("url_3x")
    var url_3x = ""


    override val itemType: Int
        get() = TYPE_GIFT
}