package com.rongtuoyouxuan.chatlive.crtbiz2.model.stream

class RoomMaskWordsBean: com.rongtuoyouxuan.chatlive.crtnet.BaseModel() {
    var data: DataBean? = DataBean()

    class DataBean {
        var words = ArrayList<String>()
    }
}