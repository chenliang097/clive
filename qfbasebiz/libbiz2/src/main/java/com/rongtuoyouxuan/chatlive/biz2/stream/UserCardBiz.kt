package com.rongtuoyouxuan.chatlive.biz2.stream

import androidx.lifecycle.LifecycleOwner
import com.rongtuoyouxuan.chatlive.biz2.model.stream.UserCardModel
import com.rongtuoyouxuan.chatlive.net2.NetWorks
import retrofit2.Retrofit
import com.rongtuoyouxuan.chatlive.biz2.ReqId
import com.rongtuoyouxuan.chatlive.biz2.constanst.UrlConstanst
import com.rongtuoyouxuan.chatlive.biz2.model.stream.LiveUserData
import com.rongtuoyouxuan.chatlive.net2.RequestListener
import retrofit2.Call

object UserCardBiz {

    /**
     * @param uid
     * @param listener
     */
    fun getUserCardInfo(lifecycleOwner: LifecycleOwner?, uid: String?, listener: RequestListener<UserCardModel?>?) {
        object : NetWorks<UserCardModel?>(lifecycleOwner, listener) {
            override fun createCall(retrofit: Retrofit): Call<UserCardModel?>? {
                return retrofit.create(UserCardServer::class.java).getUserCardInfo(uid)
            }

            override fun getReqId(): String {
                return ReqId.USER_CARD_INFO
            }

            override fun getBaseUrl(): String {
                return UrlConstanst.BASE_URL_LIVE_API_BOBOO_COM
            }
        }.start()
    }

    /**
     * @param uid
     * @param listener
     * 获取资料卡
     */
    fun getLiveUserCardInfo(
        uid: Long,
        lifecycleOwner: LifecycleOwner? = null,
        listener: RequestListener<LiveUserData>
    ) {
        object : NetWorks<LiveUserData>(lifecycleOwner, listener) {
            override fun createCall(retrofit: Retrofit): Call<LiveUserData> {
                return retrofit.create(UserCardServer::class.java).getLiveUserCardInfo(uid)
            }

            override fun getReqId(): String {
                return ReqId.USER_CARD_INFO
            }

            override fun getBaseUrl(): String {
                return UrlConstanst.BASE_URL_LIVE_API_BOBOO_COM
            }
        }.start()
    }
}