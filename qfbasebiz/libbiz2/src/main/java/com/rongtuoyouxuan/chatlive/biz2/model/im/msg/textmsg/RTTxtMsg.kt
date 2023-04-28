package com.rongtuoyouxuan.chatlive.biz2.model.im.msg.textmsg

import com.google.gson.annotations.SerializedName
import com.rongtuoyouxuan.chatlive.biz2.model.im.BaseRoomMessage

class RTTxtMsg: BaseRoomMessage() {
    @SerializedName("room_id")
    var roomId = ""
    @SerializedName("scene_id")
    var sceneId = ""
    @SerializedName("anchor_id")
    var anchorId = ""
    @SerializedName("content")
    var content = ""
    @SerializedName("user_avatar")
    var userAvatar = ""
    @SerializedName("time")
    var time = ""
    @SerializedName("check_status")
    var check_status = ""
    @SerializedName("chat_id")
    var chat_id = ""

    var type = 0//1：直播公约


    override val itemType: Int
        get() = TYPE_MESSAGE
}