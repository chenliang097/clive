package com.rongtuoyouxuan.chatlive.crtbiz2.model.im.request


class EnterRoomMsgBean : com.rongtuoyouxuan.chatlive.crtnet.BaseModel(){
    var data = DataBean()

    data class DataBean(
        var room_title:String = "",
        var scene_user_count:Int = 0,
        var stream_id:String = "",
        var room_id:Long = 0L,
        var room_id_str:String = "",
        var scene_id:Long = 0L,
        var scene_id_str:String = "",
        var room_number:String = "",
        var anchor_id:String = "",
        var anchor_name:String = "",
        var anchor_pic:String = "",
        var token:String = "",
        var is_room_admin:Boolean = false,
        var is_ban_chat:Boolean = false,
        var is_follow:Boolean = false
    )


}