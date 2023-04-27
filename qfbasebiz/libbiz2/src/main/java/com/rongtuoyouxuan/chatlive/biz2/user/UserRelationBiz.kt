package com.rongtuoyouxuan.chatlive.biz2.user

import androidx.lifecycle.LifecycleOwner
import com.rongtuoyouxuan.chatlive.biz2.ReqId
import com.rongtuoyouxuan.chatlive.biz2.constanst.UrlConstanst
import com.rongtuoyouxuan.chatlive.biz2.model.stream.LiveRoomVisibleRangeListBean
import com.rongtuoyouxuan.chatlive.biz2.model.user.FansListRequest
import com.rongtuoyouxuan.chatlive.biz2.model.user.FollowResponseModel
import com.rongtuoyouxuan.chatlive.net2.NetWorks
import com.rongtuoyouxuan.chatlive.net2.RequestListener
import retrofit2.Call
import retrofit2.Retrofit

class UserRelationBiz {
    /**
     * add
     */
    fun followAdd(
        lifecycleOwner: LifecycleOwner?,
        hostId: String?,
        position: String?,
        listener: RequestListener<FollowResponseModel?>?
    ) {
        object : NetWorks<FollowResponseModel?>(lifecycleOwner, listener) {
            override fun createCall(retrofit: Retrofit): Call<FollowResponseModel?>? {
                return retrofit.create(UserRelationServer::class.java).followAdd(hostId)
            }

            override fun getReqId(): String {
                return ReqId.USER_ADD_FOLLOW
            }

            override fun getBaseUrl(): String {
                return UrlConstanst.BASE_URL_LIVE_API_BOBOO_COM
            }
        }.start()
    }

    /**
     * del
     */
    fun followDel(
        lifecycleOwner: LifecycleOwner?,
        hostId: String?,
        listener: RequestListener<FollowResponseModel?>?
    ) {
        object : NetWorks<FollowResponseModel?>(lifecycleOwner, listener) {
            override fun createCall(retrofit: Retrofit): Call<FollowResponseModel?>? {
                return retrofit.create(UserRelationServer::class.java).followDel(hostId)
            }

            override fun getReqId(): String {
                return ReqId.USER_DEL_FOLLOW
            }

            override fun getBaseUrl(): String {
                return UrlConstanst.BASE_URL_LIVE_API_BOBOO_COM
            }
        }.start()
    }

    fun getFansList(
        lifecycleOwner: LifecycleOwner?,
        userId: String?,
        status: Int,
        page: Int,
        size: Int,
        listener: RequestListener<LiveRoomVisibleRangeListBean?>?
    ) {
        object : NetWorks<LiveRoomVisibleRangeListBean?>(lifecycleOwner, listener) {
            override fun createCall(retrofit: Retrofit): Call<LiveRoomVisibleRangeListBean?>? {
                return retrofit.create(UserRelationServer::class.java).getFansList(
                    UrlConstanst.BASE_URL_FANS_API_BOBOO_COM + "?page=$page&size=$size", userId?.let {
                        FansListRequest(
                            it, status
                        )
                    }
                )
            }

            override fun getReqId(): String {
                return ""
            }

            override fun getBaseUrl(): String {
                return UrlConstanst.BASE_URL_LIVE_API_BOBOO_COM
            }
        }.start()
    }

    companion object {
        @Volatile
        var instance: UserRelationBiz? = null
            get() {
                if (field == null) {
                    synchronized(UserRelationBiz::class.java) {
                        if (field == null) {
                            field = UserRelationBiz()
                        }
                    }
                }
                return field
            }
            private set
    }
}