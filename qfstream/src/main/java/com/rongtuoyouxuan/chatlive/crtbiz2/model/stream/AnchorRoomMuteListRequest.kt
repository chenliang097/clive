package com.rongtuoyouxuan.chatlive.crtbiz2.model.stream

class AnchorRoomMuteListRequest {
    var room_id = ""
    var scene_id = ""
    constructor(room_id: String,scene_id: String){
        this.room_id = room_id
        this.scene_id = scene_id
    }
}