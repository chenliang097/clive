package com.rongtuoyouxuan.chatlive.crtbiz2.model.stream

class AnchorRoomSettingRequest {
    var room_id = ""
    var user_admin_id = ""
    constructor(room_id: String,user_admin_id: String){
        this.room_id = room_id
        this.user_admin_id = user_admin_id
    }
}