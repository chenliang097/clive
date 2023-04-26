package com.rongtuoyouxuan.chatlive.biz2.model.stream

import com.rongtuoyouxuan.chatlive.net2.BaseModel

class LiveRoomListBean:BaseModel() {

    var data: DataBean? = DataBean()

    class DataBean {
        var base_64_room_ids = ""
        var count = 0
        var rooms_info:MutableList<RoomItemBean> = ArrayList<RoomItemBean>()
    }

    class RoomItemBean{
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