package com.rongtuoyouxuan.chatlive.crtbiz2.model.user

class FansListRequest {
    var user_id = ""
    var follow_id = ""
    var room_id = ""
    var scene_id = ""

    constructor(user_id: String,follow_id: String, room_id: String,scene_id: String){
        this.user_id = user_id
        this.follow_id = follow_id
        this.room_id = room_id
        this.scene_id = scene_id
    }
}