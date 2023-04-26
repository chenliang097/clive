package com.rongtuoyouxuan.chatlive.biz2.model.stream

import com.rongtuoyouxuan.chatlive.net2.BaseModel

class LiveTypeBean :BaseModel() {
    var data  = DataBean()

    class DataBean{
        var classify = 1 //直播分类：1，视频，2，游戏
    }
}