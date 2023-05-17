package com.rongtuoyouxuan.chatlive.biz2.gift

import com.rongtuoyouxuan.chatlive.biz2.constanst.UrlConstanst
import com.rongtuoyouxuan.chatlive.biz2.model.gift.GiftBagData
import com.rongtuoyouxuan.chatlive.biz2.model.gift.GiftPanelResData
import com.rongtuoyouxuan.chatlive.biz2.model.gift.GiftSendReq
import com.rongtuoyouxuan.chatlive.biz2.model.gift.GiftSendResData
import com.rongtuoyouxuan.chatlive.net2.BaseModel
import com.rongtuoyouxuan.chatlive.net2.NetWorks2
import com.rongtuoyouxuan.chatlive.net2.RequestListener
import newNetworkForGift

/**
 *
 * date:2022/8/3-15:17
 * des:新的礼物
 */
object GiftNewBiz {

    const val resourceTypes = "pag,mp4"

    fun getService(): GiftNewServer {
        return NetWorks2<BaseModel>(null).getRetrofit(UrlConstanst.BASE_URL_LIVE_API_BOBOO_COM)
            .create(GiftNewServer::class.java)
    }

    fun getService2(): GiftNewServer {
        return NetWorks2<BaseModel>(null).getRetrofit(UrlConstanst.BASE_URL_LIVE_API_BOBOO_COM)
            .create(GiftNewServer::class.java)
    }

    fun getPanel(userId: String, listener: RequestListener<GiftPanelResData>) {
        newNetworkForGift(
            null,
            listener,
            "",
        ) {
            it.create(GiftNewServer::class.java).getPanel(userId)
        }
    }

    fun sendGift(
        giftId: Int,
        roomId: String,
        sceneId: String,
        userId: String,
        anchorId: String,
        count: Int,
        userName: String,
        listener: RequestListener<GiftSendResData>
    ) {
        newNetworkForGift(
            null,
            listener,
            "",
        ) {
            it.create(GiftNewServer::class.java)
                .sendGift(
                    GiftSendReq(
                        giftId,
                        roomId,
                        sceneId,
                        userId,
                        anchorId,
                        count,
                        userName)
                )
        }
    }
}