package com.rongtuoyouxuan.chatlive.biz2.model.stream

import com.google.gson.annotations.SerializedName
import com.rongtuoyouxuan.chatlive.net2.BaseModel

class EnterRoomBean: BaseModel() {
    var data: DataBean? = DataBean()

    class DataBean {
        var room_title = ""
        var scene_user_count = 0
        var stream_id = ""
        var room_id = 0L
        var room_id_str = ""
        var scene_id = 0L
        var scene_id_str = ""
        var room_number = ""
        var anchor_id = ""
        var anchor_name = ""
        var anchor_pic = ""
        var token = ""
        var is_room_admin = false
        var is_ban_chat = false
        var is_follow = false
        var is_super_admin = false
        var is_anchor = false
        @SerializedName("anchor_avatar")
        var user_avatar = ""
        var user_id = ""
        var user_name = ""
        var is_black = false
        var is_live = false
        var gift_switch = false
    }
}