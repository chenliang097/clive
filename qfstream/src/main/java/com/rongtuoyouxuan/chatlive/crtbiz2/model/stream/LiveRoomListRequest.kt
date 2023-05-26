package com.rongtuoyouxuan.chatlive.crtbiz2.model.stream

class LiveRoomListRequest {

    var user_id_str = ""
    var base_64_scene_ids = ""
    constructor(user_id_str: String, base_64_scene_ids: String){
        this.user_id_str = user_id_str
        this.base_64_scene_ids = base_64_scene_ids
    }
}