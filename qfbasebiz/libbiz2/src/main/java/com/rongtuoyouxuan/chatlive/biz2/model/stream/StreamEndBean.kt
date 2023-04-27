package com.rongtuoyouxuan.chatlive.biz2.model.stream

import com.rongtuoyouxuan.chatlive.net2.BaseModel

class StreamEndBean : BaseModel() {

    var data: DataBean? = DataBean()

    class DataBean {
        var view_count = 0
        var duration = 0//历史观看次数
        var fans_count = 0//历史观看人数
        var like_count = 0
        var hot_degree = 0
        var gift_income = 0
    }
}