package com.rongtuoyouxuan.chatlive.biz2.model.im.request

import androidx.annotation.Keep

@Keep
class MuteRequest constructor(
    var user_id: String,
    var fid: String, //  禁言用户ID
    var room_id: String,
    var scene_id: String
)