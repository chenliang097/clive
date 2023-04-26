package com.rongtuoyouxuan.chatlive.biz2.model.im.request

import androidx.annotation.Keep


@Keep
class EnterRoomMsgRequest {
    var action = ""
    var room_id_str = ""
    var scene_id_str = ""
    var user_id_str = ""
    var nick_name = ""
    var is_login = false

    constructor(action: String, room_id_str: String,
        scene_id_str: String, user_id_str: String, nick_name: String, is_login:Boolean
    ) {
        this.action = action
        this.room_id_str = room_id_str
        this.scene_id_str = scene_id_str
        this.user_id_str = user_id_str
        this.nick_name = nick_name
        this.is_login = is_login
    }

}