package com.rongtuoyouxuan.chatlive.crtbiz2.model.stream

class MainLiveEnterBean: com.rongtuoyouxuan.chatlive.crtnet.BaseModel() {

    var data: DataBean? = DataBean()

    class DataBean {
        var room_title = ""
        var room_user_total = 0
        var stream_id = ""
        var room_id = 0L
        var room_id_str = ""
        var scene_id = 0L
        var scene_id_str = ""
        var room_number = ""
        var anchor_id = ""
        var anchor_name = ""
        var anchor_avatar = ""
        var medium_cover_image = ""
    }
}