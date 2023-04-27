package com.rongtuoyouxuan.chatlive.biz2.user

import com.rongtuoyouxuan.chatlive.biz2.model.stream.LiveRoomVisibleRangeListBean
import com.rongtuoyouxuan.chatlive.biz2.model.user.FansListRequest
import com.rongtuoyouxuan.chatlive.biz2.model.user.FollowResponseModel
import retrofit2.Call
import retrofit2.http.*

interface UserRelationServer {
    @FormUrlEncoded
    @POST("/follow/add")
    fun followAdd(@Field("uid") hostid: String?): Call<FollowResponseModel?>?

    @FormUrlEncoded
    @POST("/follow/del")
    fun followDel(@Field("uid") hostid: String?): Call<FollowResponseModel?>?

    @POST
    fun getFansList(
        @Url url: String?,
        @Body request: FansListRequest?
    ): Call<LiveRoomVisibleRangeListBean?>?
}