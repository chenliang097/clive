package com.rongtuoyouxuan.chatlive.crtbiz2.model.stream

class StreamEndRequest {
    var user_id = ""
    var room_id = ""
    var scene_id = ""
    constructor(user_id: String, room_id: String, scene_id: String, ){
        this.user_id = user_id
        this.room_id = room_id
        this.scene_id = scene_id
    }
}