package com.rongtuoyouxuan.chatlive.crtbiz2.model.user

import com.google.gson.annotations.SerializedName

class PersonalCenterInfoBean : com.rongtuoyouxuan.chatlive.crtnet.BaseModel() {
    @SerializedName("data")
    var data = DataBean()

    class DataBean {
        var user_center = ItemBean()
        var fans_count = 0
        var follow_count = 0
        var view_count = 0
        var like_count = 0
    }

    class ItemBean {
        var user_name = ""
        var avatar = ""
        var describe = ""
        var status = 0 //  是否认证
        var relation = 0 //   关注关系       0：未关注  1 ：关注
        var sex = 0
        var location = ""
    }

}