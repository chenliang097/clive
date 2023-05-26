package com.rongtuoyouxuan.chatlive.crtbiz2.user

import com.rongtuoyouxuan.chatlive.crtbiz2.model.user.PayInfoBean
import com.rongtuoyouxuan.chatlive.crtbiz2.model.user.PayInfoRequest
import com.rongtuoyouxuan.chatlive.crtbiz2.model.user.WalletBean
import newNetworkForGift

object PayBiz {

    fun getPayOrderInfo(request: PayInfoRequest, listener: com.rongtuoyouxuan.chatlive.crtnet.RequestListener<PayInfoBean>) =
        newNetworkForGift(
            null,
            listener,
            "",
        ) {
            it.create(PayServer::class.java).getOrderInfo(request)
        }

    fun getMyWallet(userId: String, listener: com.rongtuoyouxuan.chatlive.crtnet.RequestListener<WalletBean>) =
        newNetworkForGift(
            null,
            listener,
            "",
        ) {
            it.create(PayServer::class.java).getMyWallet(userId)
        }

    fun getBalance(userId: String, listener: com.rongtuoyouxuan.chatlive.crtnet.RequestListener<WalletBean>) =
        newNetworkForGift(
            null,
            listener,
            "",
        ) {
            it.create(PayServer::class.java).getBalance(userId)
        }

}