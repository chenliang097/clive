package com.rongtuoyouxuan.chatlive.crtbiz2.model.stream

import com.google.gson.annotations.SerializedName

/**
 *	@Description : 是否关注某个人
 *	@Author : jianbo
 *	@Date : 2022/10/25  17:50
 */
class FollowStatusModel : com.rongtuoyouxuan.chatlive.crtnet.BaseModel() {

    var data: DataBean? = DataBean()

    class DataBean {

        @SerializedName("result")
        var isFollowed: Boolean? = false
    }

}