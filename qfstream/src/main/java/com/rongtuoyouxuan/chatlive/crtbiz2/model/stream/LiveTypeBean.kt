package com.rongtuoyouxuan.chatlive.crtbiz2.model.stream

class LiveTypeBean : com.rongtuoyouxuan.chatlive.crtnet.BaseModel() {
    var data  = DataBean()

    class DataBean{
        var classify = 1 //直播分类：1，视频，2，游戏
    }
}