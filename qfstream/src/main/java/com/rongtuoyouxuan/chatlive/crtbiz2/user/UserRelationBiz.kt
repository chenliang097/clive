package com.rongtuoyouxuan.chatlive.crtbiz2.user

import androidx.lifecycle.LifecycleOwner
import com.rongtuoyouxuan.chatlive.crtbiz2.constanst.UrlConstanst
import com.rongtuoyouxuan.chatlive.crtbiz2.model.stream.FansListBean
import com.rongtuoyouxuan.chatlive.crtbiz2.model.stream.FollowStatusBean
import com.rongtuoyouxuan.chatlive.crtbiz2.model.stream.LiveRoomVisibleRangeListBean
import com.rongtuoyouxuan.chatlive.crtbiz2.model.user.FansListRequest
import com.rongtuoyouxuan.chatlive.crtbiz2.model.user.FansListUserRequest
import com.rongtuoyouxuan.chatlive.crtbiz2.model.user.FollowRequest
import com.rongtuoyouxuan.chatlive.crtbiz2.model.user.ReportRequest
import retrofit2.Call
import retrofit2.Retrofit

class UserRelationBiz {

    fun getStartLiveFansList(
        lifecycleOwner: LifecycleOwner?,
        userId: String?,
        followId: String,
        roomId: String,
        sceneId: String,
        page: Int,
        size: Int,
        listener: com.rongtuoyouxuan.chatlive.crtnet.RequestListener<LiveRoomVisibleRangeListBean?>?
    ) {
        object : com.rongtuoyouxuan.chatlive.crtnet.NetWorks<LiveRoomVisibleRangeListBean?>(lifecycleOwner, listener) {
            override fun createCall(retrofit: Retrofit): Call<LiveRoomVisibleRangeListBean?>? {
                return retrofit.create(UserRelationServer::class.java).getStartLiveFansList(
                    UrlConstanst.BASE_URL_FANS_API_BOBOO_COM + "?page=$page&size=$size", userId?.let {
                        FansListRequest(
                            it, followId, roomId, sceneId
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

    fun getFansList(
        lifecycleOwner: LifecycleOwner?,
        userId: String?,
        followId: String,
        page: Int,
        size: Int,
        listener: com.rongtuoyouxuan.chatlive.crtnet.RequestListener<FansListBean?>
    ) {
        object : com.rongtuoyouxuan.chatlive.crtnet.NetWorks<FansListBean?>(lifecycleOwner, listener) {
            override fun createCall(retrofit: Retrofit): Call<FansListBean?>? {
                return retrofit.create(UserRelationServer::class.java).getFansList(
                    UrlConstanst.BASE_URL_FANS_API_BOBOO_COM + "?page=$page&size=$size",
                    userId?.let { FansListUserRequest(it, followId) }
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

    fun addFollow(
        lifecycleOwner: LifecycleOwner?,
        fUserId: String,
        tUserId: String,
        listener: com.rongtuoyouxuan.chatlive.crtnet.RequestListener<FollowStatusBean?>
    ) {
        object : com.rongtuoyouxuan.chatlive.crtnet.NetWorks<FollowStatusBean?>(lifecycleOwner, listener) {
            override fun createCall(retrofit: Retrofit): Call<FollowStatusBean?>? {
                return retrofit.create(UserRelationServer::class.java).followAdd(FollowRequest(fUserId, tUserId))
            }

            override fun getReqId(): String {
                return ""
            }

            override fun getBaseUrl(): String {
                return UrlConstanst.BASE_URL_LIVE_API_BOBOO_COM
            }
        }.start()
    }

    fun cancelFollow(
        lifecycleOwner: LifecycleOwner?,
        fUserId: String,
        tUserId: String,
        listener: com.rongtuoyouxuan.chatlive.crtnet.RequestListener<FollowStatusBean?>
    ) {

        object : com.rongtuoyouxuan.chatlive.crtnet.NetWorks<FollowStatusBean?>(lifecycleOwner, listener) {
            override fun createCall(retrofit: Retrofit): Call<FollowStatusBean?>? {
                return retrofit.create(UserRelationServer::class.java).followDel(FollowRequest(fUserId, tUserId))
            }

            override fun getReqId(): String {
                return ""
            }

            override fun getBaseUrl(): String {
                return UrlConstanst.BASE_URL_LIVE_API_BOBOO_COM
            }
        }.start()
    }

    fun reportUser(
        lifecycleOwner: LifecycleOwner?,
        reportRequest: ReportRequest,
        listener: com.rongtuoyouxuan.chatlive.crtnet.RequestListener<com.rongtuoyouxuan.chatlive.crtnet.BaseModel?>
    ) {
        object : com.rongtuoyouxuan.chatlive.crtnet.NetWorks<com.rongtuoyouxuan.chatlive.crtnet.BaseModel?>(lifecycleOwner, listener) {
            override fun createCall(retrofit: Retrofit): Call<com.rongtuoyouxuan.chatlive.crtnet.BaseModel?>? {
                return retrofit.create(UserRelationServer::class.java).reportUser(reportRequest)
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