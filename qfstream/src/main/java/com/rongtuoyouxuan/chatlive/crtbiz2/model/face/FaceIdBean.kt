package com.rongtuoyouxuan.chatlive.crtbiz2.model.face

class FaceIdBean: com.rongtuoyouxuan.chatlive.crtnet.BaseModel() {


    var data: DataBean? = DataBean()

    class DataBean{
        var face_id = ""
        var order_no = ""
        var nonce = ""
        var sign = ""
    }

}