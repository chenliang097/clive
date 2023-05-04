package com.rongtuoyouxuan.chatlive.biz2.model.user

import com.google.gson.annotations.SerializedName
import com.rongtuoyouxuan.chatlive.net2.BaseModel

class UserCardInfoBean : BaseModel() {
    @SerializedName("data")
    var data = DataBean()

    class DataBean {
        var fans_count = 0
        var follow_count = 0
        var is_follow = false
        var nick_name = ""
        var avatar = ""
        var sex = ""
        var location = ""
        var is_room_admin = false
        var is_verify = false
        var is_super_admin = false
        var follow_id = ""
        var is_forbid_speak = false
    }

    data class ProfileUserData(val follow_id: String, val nick_name: String, var is_super_admin: Boolean, var is_room_admin: Boolean, var is_forbid_speak: Boolean)

}