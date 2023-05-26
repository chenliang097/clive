package com.rongtuoyouxuan.chatlive.crtbiz2.model.im.msg.textmsg

class RTTxtMsgRequest {
    var room_id = ""
    var scene_id = ""
    var anchor_id = ""
    var content = ""
    var is_super_admin = false
    var is_room_admin = false
    var is_anchor = false
    var user_avatar = ""
    var user_id = ""
    var user_name = ""

    constructor(room_id:String,scene_id:String,anchor_id:String,content:String,
                is_super_admin:Boolean,is_room_admin:Boolean,
                is_anchor:Boolean,user_avatar:String, user_id:String, user_name:String){
        this.room_id = room_id
        this.scene_id = scene_id
        this.anchor_id = anchor_id
        this.content = content
        this.is_super_admin = is_super_admin
        this.is_room_admin = is_room_admin
        this.is_anchor = is_anchor
        this.user_avatar = user_avatar
        this.user_id = user_id
        this.user_name = user_name
    }
}