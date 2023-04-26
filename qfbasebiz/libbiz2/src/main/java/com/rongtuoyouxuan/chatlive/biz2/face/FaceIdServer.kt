package com.rongtuoyouxuan.chatlive.biz2.face

import com.rongtuoyouxuan.chatlive.biz2.model.face.*
import com.rongtuoyouxuan.chatlive.biz2.model.stream.*
import com.rongtuoyouxuan.chatlive.net2.BaseModel
import retrofit2.Call
import retrofit2.http.*

interface FaceIdServer {

    @POST("/userProxy/v1/user/faceIdentification")
    fun getTencentFaceData(@Body request: FaceIdRequest): Call<FaceIdBean>

    @POST("/userProxy/v1/user/faceResult")
    fun sendTencentFaceResult(@Body request: FaceIdResultRequest): Call<FaceIdResultBean>

    @POST("/userProxy/v1/user/getVerificationStatus")
    fun getTencentIdentificationStatus(@Query("user_id")userId:String): Call<FaceIdStatusBean>


}