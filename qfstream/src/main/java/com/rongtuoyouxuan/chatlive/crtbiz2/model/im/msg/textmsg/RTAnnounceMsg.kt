package com.rongtuoyouxuan.chatlive.crtbiz2.model.im.msg.textmsg

import com.google.gson.annotations.SerializedName
import com.rongtuoyouxuan.chatlive.crtbiz2.model.im.BaseRoomMessage

class RTAnnounceMsg: BaseRoomMessage() {
    @SerializedName("scene_id")
    var sceneId = 0
    @SerializedName("scene_id_str")
    var sceneIdStr = ""
    @SerializedName("announce")
    var announce = ""


    override val itemType: Int
        get() = TYPE_ANNOUNCE
}