package com.rongtuoyouxuan.chatlive.biz2.model.im.msg.textmsg

import com.google.gson.annotations.SerializedName
import com.rongtuoyouxuan.chatlive.biz2.model.im.BaseRoomMessage

class RTHotChangeMsg: BaseRoomMessage() {
    @SerializedName("scene_id")
    var sceneId = ""
    @SerializedName("scene_id_str")
    var sceneIdStr = ""
    @SerializedName("fire")
    var fire = 0
    @SerializedName("user_count")
    var userCount = 0
    @SerializedName("timestamp")
    var timestamp = 0

    var type = 0//1：直播公约


    override val itemType: Int
        get() = TYPE_HOT_CHANGE
}