package com.rongtuoyouxuan.chatlive.biz2.model.stream

import com.rongtuoyouxuan.chatlive.net2.BaseModel

class StreamHeartBeatBean :BaseModel() {
    var data  = DataBean()

    class DataBean{
        var live_status = "" // living/end
    }
}