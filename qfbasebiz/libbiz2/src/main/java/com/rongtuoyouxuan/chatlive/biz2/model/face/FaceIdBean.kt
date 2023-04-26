package com.rongtuoyouxuan.chatlive.biz2.model.face

import com.rongtuoyouxuan.chatlive.net2.BaseModel

class FaceIdBean:BaseModel() {


    var data: DataBean? = DataBean()

    class DataBean{
        var face_id = ""
        var order_no = ""
        var nonce = ""
        var sign = ""
    }

}