package com.rongtuoyouxuan.chatlive.biz2.model.im.msg.textmsg

import com.google.gson.annotations.SerializedName
import com.rongtuoyouxuan.chatlive.biz2.model.im.BaseRoomMessage

class RTFollowMsg: BaseRoomMessage() {
    @SerializedName("user_nick_name")
    var fromNickName = ""
    @SerializedName("follow_nick_name")
    var followNickName = 0
    @SerializedName("scene_id")
    var sceneId = 0
    @SerializedName("scene_id_str")
    var sceneIdStr = ""
    @SerializedName("follow_id")
    var followId = 0


    override val itemType: Int
        get() = TYPE_GIFT

    class GiftExtra {
        var type = 0
        var num = 0
    }
}