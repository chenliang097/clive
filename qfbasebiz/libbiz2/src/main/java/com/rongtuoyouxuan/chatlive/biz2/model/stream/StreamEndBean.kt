package com.rongtuoyouxuan.chatlive.biz2.model.stream

import com.rongtuoyouxuan.chatlive.net2.BaseModel

class StreamEndBean : BaseModel() {

    var data: DataBean? = DataBean()

    class DataBean {
        var now_live = LiveBean()
        var prev_live = LiveBean()
        var anchor = AnchorInfo()
    }

    class LiveBean{
        var anchor_id = 0
        var audienceNum_total = 0//历史观看次数
        var audience_num = 0//历史观看人数
        var diamond_total = 0
        var id = 0
        var increment_fans = 0
        var living_time = 0
        var send_gift_people = 0
        var pic = ""
        var gold_bean_total = 0
        var game_people = 0
    }
}