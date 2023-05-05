package com.rongtuoyouxuan.chatlive.biz2.model.im.msg.textmsg

import com.google.gson.annotations.SerializedName
import com.rongtuoyouxuan.chatlive.biz2.model.im.BaseRoomMessage

class RTAnnounceMsg: BaseRoomMessage() {
    @SerializedName("scene_id")
    var sceneId = ""
    @SerializedName("scene_id_str")
    var sceneIdStr = ""
    @SerializedName("announce")
    var announce = ""

    var type = 0//1：直播公约


    override val itemType: Int
        get() = TYPE_ANNOUNCE
}