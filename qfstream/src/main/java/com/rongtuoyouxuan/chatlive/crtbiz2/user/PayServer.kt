package com.rongtuoyouxuan.chatlive.crtbiz2.user

import com.rongtuoyouxuan.chatlive.crtbiz2.model.login.request.*
import com.rongtuoyouxuan.chatlive.crtbiz2.model.login.response.*
import com.rongtuoyouxuan.chatlive.crtbiz2.model.user.PayInfoBean
import com.rongtuoyouxuan.chatlive.crtbiz2.model.user.PayInfoRequest
import com.rongtuoyouxuan.chatlive.crtbiz2.model.user.WalletBean
import retrofit2.Call
import retrofit2.http.*
interface PayServer {

    /**
     * 获取订单信息
     */
    @POST("/wallet/v1/pay/recharge")
    fun getOrderInfo(@Body request:PayInfoRequest): Call<PayInfoBean>

    @GET("/wallet/v1/wallet/my")
    fun getMyWallet(@Query("user_id") userId:String): Call<WalletBean>

    @GET("/wallet/v1/user/balance")
    fun getBalance(@Query("user_id") userId:String): Call<WalletBean>

}