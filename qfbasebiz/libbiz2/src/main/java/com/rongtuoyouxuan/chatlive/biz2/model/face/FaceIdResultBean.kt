package com.rongtuoyouxuan.chatlive.biz2.model.face

import com.rongtuoyouxuan.chatlive.net2.BaseModel

class FaceIdResultBean:BaseModel() {


    var data: DataBean? = DataBean()

    class DataBean{
        var identification_result = false // 验证结果   true 通过 false 不通过
    }

}