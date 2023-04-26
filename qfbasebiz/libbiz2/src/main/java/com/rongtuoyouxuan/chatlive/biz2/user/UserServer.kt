package com.rongtuoyouxuan.chatlive.biz2.user

import com.rongtuoyouxuan.chatlive.biz2.model.login.request.*
import com.rongtuoyouxuan.chatlive.biz2.model.login.response.*
import com.rongtuoyouxuan.chatlive.net2.BaseModel
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

/*
*Create by {Mr秦} on 2022/7/23
*/
interface UserServer {

    /**
     * 登录
     * */
    @POST("/login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>


    /**
     * 上传文件
     * */
    @POST("/system/upload")
    fun uploadFile(@Body request: RequestBody): Call<UploadFileResponse>

    /**
     * 退出登录
     * */
    @DELETE("/logout")
    fun logout(): Call<BaseModel>


    /**
     * 关注列表
     */
    @GET("/follows")
    fun getFollowsList(@QueryMap map: Map<String, @JvmSuppressWildcards Any>): Call<ContactListResponse>

    /**
     * 粉丝列表
     */
    @GET("/fans")
    fun getFansList(@QueryMap map: Map<String, @JvmSuppressWildcards Any>): Call<ContactListResponse>

    /**
     * 好友列表
     */
    @GET("/friends")
    fun getGoodFriendsList(@QueryMap map: Map<String, @JvmSuppressWildcards Any>): Call<ContactListResponse>

}