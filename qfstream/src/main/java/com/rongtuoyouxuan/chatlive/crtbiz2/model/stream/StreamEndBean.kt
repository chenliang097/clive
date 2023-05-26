package com.rongtuoyouxuan.chatlive.crtbiz2.model.stream

class StreamEndBean : com.rongtuoyouxuan.chatlive.crtnet.BaseModel() {

    var data: DataBean? = DataBean()

    class DataBean {
        var max_fire = 0
        var scene_fans = 0
        var max_user_count = 0
        var living_time_minutes = 0
        var living_start_time = 0
        var living_off_time = 0
        var medium_fans_view_count = 0
        var liking_count = 0

        var view_count = 0
        var duration = 0//历史观看次数
        var fans_count = 0//历史观看人数
        var like_count = 0
        var hot_degree = 0
        var income = 0
    }
}