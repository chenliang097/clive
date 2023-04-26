package com.rongtuoyouxuan.chatlive.biz2.model.face

import com.rongtuoyouxuan.chatlive.net2.BaseModel

class FaceIdStatusBean:BaseModel() {


    var data: DataBean? = DataBean()

    class DataBean{
        var status = false
    }

}