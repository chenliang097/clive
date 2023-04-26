package com.rongtuoyouxuan.chatlive.biz2.model.stream

import com.rongtuoyouxuan.chatlive.net2.BaseModel

class RoomInfoExtraBean: BaseModel() {
    var data: DataBean? = DataBean()

    class DataBean {
        var scene_user_count = 0
        var room_id = 0L
        var room_id_str = ""
        var scene_id = 0L
        var scene_id_str = ""
        var room_number = ""
        var anchor_id = 0L
        var anchor_id_str = ""
        var is_anchor = false
        var liking_count = 0
        var fire = 0
    }
}