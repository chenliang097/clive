package com.rongtuoyouxuan.chatlive.biz2.stream

import com.rongtuoyouxuan.chatlive.biz2.model.stream.LiveUserData
import retrofit2.http.GET
import com.rongtuoyouxuan.chatlive.biz2.model.stream.UserCardModel
import retrofit2.Call
import retrofit2.http.Query
interface UserCardServer {
    @GET("www")
    fun getUserCardInfo(@Query("uid") uid: String?): Call<UserCardModel?>?

    //直播间-资料卡
    @GET("/user/data")
    fun getLiveUserCardInfo(@Query("user_id") uid: Long): Call<LiveUserData>
}