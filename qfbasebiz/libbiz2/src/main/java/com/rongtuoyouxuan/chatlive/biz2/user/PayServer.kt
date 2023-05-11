package com.rongtuoyouxuan.chatlive.biz2.user

import com.rongtuoyouxuan.chatlive.biz2.model.login.request.*
import com.rongtuoyouxuan.chatlive.biz2.model.login.response.*
import com.rongtuoyouxuan.chatlive.biz2.model.user.PayInfoBean
import com.rongtuoyouxuan.chatlive.biz2.model.user.PayInfoRequest
import com.rongtuoyouxuan.chatlive.biz2.model.user.WalletBean
import retrofit2.Call
import retrofit2.http.*
interface PayServer {

    /**
     * 获取订单信息
     */
    @POST("/order/v1/pay/order")
    fun getOrderInfo(@Body request:PayInfoRequest): Call<PayInfoBean>

    @GET("/wallet/v1/wallet/my")
    fun getMyWallet(@Query("user_id") userId:String): Call<WalletBean>

    @GET("/wallet/v1/wallet/balance")
    fun getBalance(@Query("user_id") userId:String): Call<WalletBean>

}