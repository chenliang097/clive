package com.rongtuoyouxuan.chatlive.biz2.model.im.msg.textmsg

import com.google.gson.annotations.SerializedName
import com.rongtuoyouxuan.chatlive.biz2.model.im.BaseRoomMessage

class RTTxtMsg: BaseRoomMessage() {
    @SerializedName("scene_id")
    var sceneId = 0L
    @SerializedName("anchor_id")
    var anchorId = 0L
    @SerializedName("content")
    var content = ""
    @SerializedName("user_avatar")
    var userAvatar = ""
    @SerializedName("time")
    var time = 0L
    @SerializedName("check_status")
    var check_status = ""
    @SerializedName("chat_id")
    var chat_id = 0L

    var type = 0//1：直播公约


    override val itemType: Int
        get() = TYPE_MESSAGE
}