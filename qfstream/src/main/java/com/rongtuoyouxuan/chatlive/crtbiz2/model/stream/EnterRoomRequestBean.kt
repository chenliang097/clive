package com.rongtuoyouxuan.chatlive.crtbiz2.model.stream

class EnterRoomRequestBean {
    var room_id_str = ""
    var scene_id_str = ""
    var user_id_str = ""
    var is_login = false

    constructor(room_id_str: String, scene_id_str:String, user_id_str:String, is_login:Boolean){
        this.room_id_str = room_id_str
        this.scene_id_str = scene_id_str
        this.user_id_str = user_id_str
        this.is_login = is_login
    }
}