package com.rongtuoyouxuan.chatlive.biz2.user

import com.rongtuoyouxuan.chatlive.biz2.model.stream.FansListBean
import com.rongtuoyouxuan.chatlive.biz2.model.stream.FollowStatusBean
import com.rongtuoyouxuan.chatlive.biz2.model.stream.LiveRoomVisibleRangeListBean
import com.rongtuoyouxuan.chatlive.biz2.model.stream.LiveRoomVisibleRangeRequest
import com.rongtuoyouxuan.chatlive.biz2.model.user.FansListRequest
import com.rongtuoyouxuan.chatlive.biz2.model.user.FollowRequest
import com.rongtuoyouxuan.chatlive.biz2.model.user.FollowResponseModel
import com.rongtuoyouxuan.chatlive.net2.BaseModel
import retrofit2.Call
import retrofit2.http.*

interface UserRelationServer {
    @POST("/userProxy/v1/user/follow")
    fun followAdd(@Body request:FollowRequest?): Call<FollowStatusBean?>?

    @POST("/userProxy/v1/user/cancelFollow")
    fun followDel(@Body request:FollowRequest?): Call<FollowStatusBean?>?

    @POST
    fun getStartLiveFansList(
        @Url url: String?,
        @Body request: FansListRequest?
    ): Call<LiveRoomVisibleRangeListBean?>?

    @POST
    fun getFansList(
        @Url url: String?,
        @Body request: FansListRequest?
    ): Call<FansListBean?>?

}