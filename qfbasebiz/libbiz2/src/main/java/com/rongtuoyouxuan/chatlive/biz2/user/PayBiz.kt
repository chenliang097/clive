package com.rongtuoyouxuan.chatlive.biz2.user

import com.rongtuoyouxuan.chatlive.biz2.model.login.request.*
import com.rongtuoyouxuan.chatlive.biz2.model.login.response.*
import com.rongtuoyouxuan.chatlive.biz2.model.user.PayInfoBean
import com.rongtuoyouxuan.chatlive.biz2.model.user.PayInfoRequest
import com.rongtuoyouxuan.chatlive.biz2.model.user.WalletBean
import com.rongtuoyouxuan.chatlive.net2.RequestListener
import newNetworkForGift

object PayBiz {

    fun getPayOrderInfo(request: PayInfoRequest, listener: RequestListener<PayInfoBean>) =
        newNetworkForGift(
            null,
            listener,
            "",
        ) {
            it.create(PayServer::class.java).getOrderInfo(request)
        }

    fun getMyWallet(userId: String, listener: RequestListener<WalletBean>) =
        newNetworkForGift(
            null,
            listener,
            "",
        ) {
            it.create(PayServer::class.java).getMyWallet(userId)
        }

}