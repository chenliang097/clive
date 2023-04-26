package com.rongtuoyouxuan.chatlive.biz2.gift

import com.rongtuoyouxuan.chatlive.biz2.model.gift.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

/**
 * 
 * date:2022/8/3-15:15
 * des: 新的礼物
 */
interface GiftNewServer {

    @GET("/gift/v1/list")
    fun getPanel(): Call<GiftPanelResData>

    //礼物资源全量/增量接口
    @GET("/gift/resource")
    suspend fun getGiftResource(
        @Query("timestamp") timestamp: Long,
        @Query("resource_types") resourceTypes: String
    ): GiftResponseData

    //座驾资源全量/增量接口
    @GET("/car/resource")
    suspend fun getCarResource(
        @Query("timestamp") timestamp: Long,
        @Query("resource_types") resourceTypes: String
    ): CarResponseData

    //发送礼物
    @POST("/gift/v1/give")
    fun sendGift(@Body request: GiftSendReq): Call<GiftSendResData>

    //下载文件
    @Streaming
    @GET
    suspend fun downloadFileUrl(@Url fileUrl: String): ResponseBody
}