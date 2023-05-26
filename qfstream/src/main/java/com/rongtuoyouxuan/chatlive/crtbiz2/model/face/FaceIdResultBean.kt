package com.rongtuoyouxuan.chatlive.crtbiz2.model.face

class FaceIdResultBean: com.rongtuoyouxuan.chatlive.crtnet.BaseModel() {


    var data: DataBean? = DataBean()

    class DataBean{
        var identification_result = false // 验证结果   true 通过 false 不通过
    }

}