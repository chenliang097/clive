package com.rongtuoyouxuan.chatlive.biz2.stream

import com.rongtuoyouxuan.chatlive.biz2.model.stream.LiveUserData
import retrofit2.http.GET
import com.rongtuoyouxuan.chatlive.biz2.model.stream.UserCardModel
import com.rongtuoyouxuan.chatlive.biz2.model.user.UserCardInfoBean
import com.rongtuoyouxuan.chatlive.biz2.model.user.UserCardInfoRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query
interface UserCardServer {
    @GET("www")
    fun getUserCardInfo(@Query("uid") uid: String?): Call<UserCardModel?>?

    //直播间-资料卡
    @POST("/userProxy/v1/user/userDetail")
    fun getLiveUserCardInfo(@Body request: UserCardInfoRequest): Call<UserCardInfoBean>
}