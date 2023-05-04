package com.rongtuoyouxuan.chatlive.biz2.model.im.request

import androidx.annotation.Keep

@Keep
class SetRoomManagerRequest constructor(
    //操作者
    var user_id: String,
    var room_id: String,
    //被操作者
    var room_admin_id: String,
    var u_nick_name: String,
    var r_nic_name: String
)