package com.rongtuoyouxuan.chatlive.crtbiz2.model.stream

class StreamHeartBeatBean : com.rongtuoyouxuan.chatlive.crtnet.BaseModel() {
    var data  = DataBean()

    class DataBean{
        var live_status = "" // living/end
    }
}