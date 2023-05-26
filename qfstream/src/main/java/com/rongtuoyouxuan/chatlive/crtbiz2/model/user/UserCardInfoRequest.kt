package com.rongtuoyouxuan.chatlive.crtbiz2.model.user

class UserCardInfoRequest {
    var follow_id = ""
    var room_id = ""
    var scene_id = ""
    var user_id = ""

    constructor(follow_id: String,room_id: String, scene_id:String, user_id:String){
        this.follow_id = follow_id
        this.room_id = room_id
        this.scene_id = scene_id
        this.user_id = user_id
    }
}