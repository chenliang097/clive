package com.rongtuoyouxuan.chatlive.biz2.model.stream

import com.rongtuoyouxuan.chatlive.net2.BaseModel

class RoomMaskWordsBean: BaseModel() {
    var data: DataBean? = DataBean()

    class DataBean {
        var words = ArrayList<String>()
    }
}