package com.rongtuoyouxuan.chatlive.biz2.model.im.msg.textmsg

import com.google.gson.annotations.SerializedName
import com.rongtuoyouxuan.chatlive.net2.BaseModel

class RTTxtMsg:BaseModel(){
    @SerializedName("room_id")
    var roomId = ""
    @SerializedName("scene_id")
    var sceneId = ""
    @SerializedName("anchor_id")
    var anchorId = ""
    @SerializedName("content")
    var content = ""
    @SerializedName("is_super_admin")
    var isSuperAdmin = false
    @SerializedName("is_room_admin")
    var isRoomAdmin = false
    @SerializedName("is_anchor")
    var isAnchor = false
    @SerializedName("user_avatar")
    var userAvatar = ""
    @SerializedName("user_id")
    var userId = ""
    @SerializedName("user_id")
    var userName = ""
    @SerializedName("time")
    var time = ""
    @SerializedName("check_status")
    var check_status = ""
    @SerializedName("chat_id")
    var chat_id = ""
}