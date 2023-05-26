package com.rongtuoyouxuan.chatlive.crtbiz2.model.stream

class FollowStatusBean: com.rongtuoyouxuan.chatlive.crtnet.BaseModel() {
    var data: DataBean? = DataBean()

    class DataBean {
        var position = 0
        var followBean:FansListBean.ItemBean = FansListBean.ItemBean()
    }
}