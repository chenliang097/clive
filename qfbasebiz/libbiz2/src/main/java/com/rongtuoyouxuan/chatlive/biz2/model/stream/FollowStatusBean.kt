package com.rongtuoyouxuan.chatlive.biz2.model.stream

import com.rongtuoyouxuan.chatlive.net2.BaseModel

class FollowStatusBean: BaseModel() {
    var data: DataBean? = DataBean()

    class DataBean {
        var position = 0
        var followBean:FansListBean.ItemBean = FansListBean.ItemBean()
    }
}