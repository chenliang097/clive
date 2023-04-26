package com.rongtuoyouxuan.chatlive.biz2.model.stream

import com.rongtuoyouxuan.chatlive.net2.BaseModel

class LiveRoomListRequest {

    var user_id_str = ""
    var base_64_scene_ids = ""
    constructor(user_id_str: String, base_64_scene_ids: String){
        this.user_id_str = user_id_str
        this.base_64_scene_ids = base_64_scene_ids
    }
}