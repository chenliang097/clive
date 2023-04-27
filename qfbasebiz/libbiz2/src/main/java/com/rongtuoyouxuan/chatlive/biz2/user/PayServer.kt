package com.rongtuoyouxuan.chatlive.biz2.user

import com.rongtuoyouxuan.chatlive.biz2.model.login.request.*
import com.rongtuoyouxuan.chatlive.biz2.model.login.response.*
import com.rongtuoyouxuan.chatlive.biz2.model.user.PayInfoBean
import com.rongtuoyouxuan.chatlive.biz2.model.user.PayInfoRequest
import retrofit2.Call
import retrofit2.http.*
interface PayServer {

    /**
     * 获取订单信息
     */
    @POST("/order/v1/pay")
    fun getOrderInfo(@Body request:PayInfoRequest): Call<PayInfoBean>

}