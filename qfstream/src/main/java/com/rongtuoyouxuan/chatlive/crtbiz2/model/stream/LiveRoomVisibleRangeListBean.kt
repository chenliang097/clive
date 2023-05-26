package com.rongtuoyouxuan.chatlive.crtbiz2.model.stream

class LiveRoomVisibleRangeListBean: com.rongtuoyouxuan.chatlive.crtnet.BaseModel() {

    var data: DataBean? = DataBean()

    class DataBean {
        var is_self = false //  是否是自己
        var total = 0 // 总共条数
        var page = 0  // 当前页数
        var size = 0
        var fans_list:MutableList<FansItemBean> = ArrayList()
    }

    class FansItemBean{
        var id = ""
        var nick_name = ""
        var avatar = ""
        var status = false  // 关注状态
        var allow = false  //
    }
}