package com.rongtuoyouxuan.chatlive.biz2.model.stream

import com.rongtuoyouxuan.chatlive.net2.BaseModel
import com.google.gson.annotations.SerializedName

/**
 *	@Description : 是否关注某个人
 *	@Author : jianbo
 *	@Date : 2022/10/25  17:50
 */
class FollowStatusModel : BaseModel() {

    var data: DataBean? = DataBean()

    class DataBean {

        @SerializedName("result")
        var isFollowed: Boolean? = false
    }

}