package com.rongtuoyouxuan.chatlive.biz2.stream

import androidx.lifecycle.LifecycleOwner
import com.rongtuoyouxuan.chatlive.biz2.ReqId
import com.rongtuoyouxuan.chatlive.biz2.constanst.UrlConstanst
import com.rongtuoyouxuan.chatlive.biz2.model.live.LiveRoomBean
import com.rongtuoyouxuan.chatlive.biz2.model.live.LiveRoomExtraBean
import com.rongtuoyouxuan.chatlive.biz2.model.live.StreamOnlineModel
import com.rongtuoyouxuan.chatlive.biz2.model.stream.*
import com.rongtuoyouxuan.chatlive.biz2.model.stream.im.PushStreamHeartBeatRequest
import com.rongtuoyouxuan.chatlive.biz2.model.stream.im.StreamStartRequest
import com.rongtuoyouxuan.chatlive.net2.BaseModel
import com.rongtuoyouxuan.chatlive.net2.NetWorks
import com.rongtuoyouxuan.chatlive.net2.RequestListener
import newNetworks
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit

object StreamBiz {

    fun startlive(
        lifecycleOwner: LifecycleOwner?,
        classify_id: Int?, title: String?, pic: String?, longitude: Double?, latitude: Double?,
        listener: RequestListener<StartStreamBean?>?,
    ) {
//        object : NetWorks<StartStreamBean?>(lifecycleOwner, listener) {
//            override fun getBaseUrl(): String {
//                return UrlConstanst.BASE_URL_LIVE_API_BOBOO_COM
//            }
//
//            override fun createCall(retrofit: Retrofit): Call<StartStreamBean?>? {
//                return retrofit.create(StreamServer::class.java)
//                    .startlive(StreamStartRequest(classify_id, title, pic, longitude, latitude))
//            }
//
//            override fun getReqId(): String {
//                return ReqId.STREAM_CONMEN
//            }
//        }.start()
    }

    fun startlive(
        lifecycleOwner: LifecycleOwner?,
        userId: String?, userName: String?,
        listener: RequestListener<StartStreamBean?>?,
    ) {
        object : NetWorks<StartStreamBean?>(lifecycleOwner, listener) {
            override fun getBaseUrl(): String {
                return UrlConstanst.BASE_URL_LIVE_API_BOBOO_COM
            }

            override fun createCall(retrofit: Retrofit): Call<StartStreamBean?>? {
                return retrofit.create(StreamServer::class.java)
                    .startlive(StreamStartRequest(userId, userName))
            }

            override fun getReqId(): String {
                return ReqId.STREAM_CONMEN
            }
        }.start()
    }

    fun streamEndlive(
        lifecycleOwner: LifecycleOwner?,
        liveId: String,
        listener: RequestListener<StreamEndBean>,
    ) {
        object : NetWorks<StreamEndBean>(lifecycleOwner, listener) {
            override fun getBaseUrl(): String {
                return UrlConstanst.BASE_URL_LIVE_API_BOBOO_COM
            }

            override fun createCall(retrofit: Retrofit): Call<StreamEndBean?>? {
                return retrofit.create(StreamServer::class.java)
                    .streamEndLive(StreamEndRequest(liveId))
            }

            override fun getReqId(): String {
                return ReqId.STREAM_CONMEN
            }
        }.start()
    }

    fun getOnlineList(
        lifecycleOwner: LifecycleOwner?,
        chatroomId: String?,
        anchorId: String?,
        page: Int?,
        size: Int?,
        listener: RequestListener<StreamOnlineModel>,
    ) {
        object : NetWorks<StreamOnlineModel>(lifecycleOwner, listener) {
            override fun getBaseUrl(): String {
                return UrlConstanst.BASE_URL_LIVE_API_BOBOO_COM
            }

            override fun createCall(retrofit: Retrofit): Call<StreamOnlineModel?>? {
                return retrofit.create(StreamServer::class.java)
                    .getOnlineList(chatroomId, anchorId, page, size)
            }

            override fun getReqId(): String {
                return ReqId.STREAM_CONMEN
            }
        }.start()
    }

    fun getRoomInfo(
        lifecycleOwner: LifecycleOwner?,
        srteamId: String?,
        listener: RequestListener<LiveRoomBean?>?,
    ) {
        object : NetWorks<LiveRoomBean?>(lifecycleOwner, listener) {
            override fun getBaseUrl(): String {
                return UrlConstanst.BASE_URL_LIVE_API_BOBOO_COM
            }

            override fun createCall(retrofit: Retrofit): Call<LiveRoomBean?>? {
                return retrofit.create(StreamServer::class.java).getRoomInfo(srteamId)
            }

            override fun getReqId(): String {
                return ReqId.STREAM_CONMEN
            }
        }.start()
    }

    fun liveLike(
        liveId: Long,
        likeNum: Int,
        lifecycleOwner: LifecycleOwner?,
        listener: RequestListener<LikeLiveData>,
    ) {
        object : NetWorks<LikeLiveData>(lifecycleOwner, listener) {
            override fun getBaseUrl(): String {
                return UrlConstanst.BASE_URL_LIVE_API_BOBOO_COM
            }

            override fun createCall(retrofit: Retrofit): Call<LikeLiveData> {
                return retrofit.create(StreamServer::class.java)
                    .liveLike(LikeLiveReq(liveId, likeNum))
            }

            override fun getReqId(): String {
                return ""
            }
        }.start()
    }

    fun liveAudienceLike(
        liveId: Long,
        likeNum: Int,
        lifecycleOwner: LifecycleOwner?,
        listener: RequestListener<LikeLiveData>,
    ) {
        object : NetWorks<LikeLiveData>(lifecycleOwner, listener) {
            override fun getBaseUrl(): String {
                return UrlConstanst.BASE_URL_LIVE_API_BOBOO_COM
            }

            override fun createCall(retrofit: Retrofit): Call<LikeLiveData> {
                return retrofit.create(StreamServer::class.java)
                    .liveAudienceLike(LikeLiveReq(liveId, likeNum))
            }

            override fun getReqId(): String {
                return ""
            }
        }.start()
    }

    fun liveChatroomUsers(
        liveId: String,
        page: Int,
        pageSize: Int,
        anchorId: String,
        lifecycleOwner: LifecycleOwner?,
        listener: RequestListener<LiveChatRoomUserData>,
    ) {
        object : NetWorks<LiveChatRoomUserData>(lifecycleOwner, listener) {
            override fun getBaseUrl(): String {
                return UrlConstanst.BASE_URL_LIVE_API_BOBOO_COM
            }

            override fun createCall(retrofit: Retrofit): Call<LiveChatRoomUserData> {
                return retrofit.create(StreamServer::class.java)
                    .liveChatroomUsers(liveId, page, pageSize, anchorId)
            }

            override fun getReqId(): String {
                return ""
            }
        }.start()
    }

    //关注
    fun liveFollows(
        followId: String,
        userId: String,
        roomId: String? = null,
        sceneId: String? = null,
        status: Int? = null,
        lifecycleOwner: LifecycleOwner?,
        listener: RequestListener<BaseModel>,
    ) {
        object : NetWorks<BaseModel>(lifecycleOwner, listener) {
            override fun getBaseUrl(): String {
                return UrlConstanst.BASE_URL_LIVE_API_BOBOO_COM
            }

            override fun createCall(retrofit: Retrofit): Call<BaseModel> {
                return retrofit.create(StreamServer::class.java)
                    .liveFollows(LiveFollowsReq(followId, userId, roomId, sceneId, 1))
            }

            override fun getReqId(): String {
                return ""
            }
        }.start()
    }

    //是否关注某个人
    fun getFollowStatus(
        followId: Long,
        userId: Long,
        lifecycleOwner: LifecycleOwner?,
        listener: RequestListener<FollowStatusModel>,
    ) {
        object : NetWorks<FollowStatusModel>(lifecycleOwner, listener) {
            override fun getBaseUrl(): String {
                return UrlConstanst.BASE_URL_LIVE_API_BOBOO_COM
            }

            override fun createCall(retrofit: Retrofit): Call<FollowStatusModel> {
                return retrofit.create(StreamServer::class.java)
                    .getFollowStatus(userId, followId)
            }

            override fun getReqId(): String {
                return ""
            }
        }.start()
    }

    fun getRoomExtraInfo(
        lifecycleOwner: LifecycleOwner?,
        anchorId: Long?,
        srteamId: Long?,
        listener: RequestListener<LiveRoomExtraBean?>?,
    ) {
        object : NetWorks<LiveRoomExtraBean?>(lifecycleOwner, listener) {
            override fun getBaseUrl(): String {
                return UrlConstanst.BASE_URL_LIVE_API_BOBOO_COM
            }

            override fun createCall(retrofit: Retrofit): Call<LiveRoomExtraBean?>? {
                return retrofit.create(StreamServer::class.java)
                    .getRoomExtraInfo(srteamId, anchorId)
            }

            override fun getReqId(): String {
                return ReqId.STREAM_CONMEN
            }
        }.start()
    }

    //加入粉丝团
    fun liveJoinGroup(
        anchorId: Long,
        liveId: Long,
        lifecycleOwner: LifecycleOwner?,
        listener: RequestListener<LiveJoinGroupResData>,
    ) {
        object : NetWorks<LiveJoinGroupResData>(lifecycleOwner, listener) {
            override fun getBaseUrl(): String {
                return UrlConstanst.BASE_URL_LIVE_API_BOBOO_COM
            }

            override fun createCall(retrofit: Retrofit): Call<LiveJoinGroupResData> {
                return retrofit.create(StreamServer::class.java)
                    .liveJoinGroup(LiveJoinGroupReq(anchorId, liveId))
            }

            override fun getReqId(): String {
                return ""
            }
        }.start()
    }

    //分享增加经验值
    fun sharedLive(
        request: ShareLiveRequest,
        lifecycleOwner: LifecycleOwner? = null,
        listener: RequestListener<BaseModel>,
    ) {
        object : NetWorks<BaseModel>(lifecycleOwner, listener) {
            override fun getBaseUrl(): String {
                return UrlConstanst.BASE_URL_LIVE_API_BOBOO_COM
            }

            override fun createCall(retrofit: Retrofit): Call<BaseModel> {
                return retrofit.create(StreamServer::class.java)
                    .sharedLive(request)
            }

            override fun getReqId(): String {
                return ""
            }
        }.start()
    }

    fun getRoomInfo(
        roomId: String, sceneId: String, userId: String,
        requestListener: RequestListener<EnterRoomBean>,
    ) {
        newNetworks(null, requestListener, "") {
            it.create(StreamServer::class.java)
                .getRoomInfo(EnterRoomRequestBean(roomId, sceneId, userId, true))
        }
    }

    fun getRoomInfoExtra(
        roomId: String, sceneId: String, userId: String,
        requestListener: RequestListener<RoomInfoExtraBean>,
    ) {
        newNetworks(null, requestListener, "") {
            it.create(StreamServer::class.java)
                .getRoomInfoExtra(EnterRoomRequestBean(roomId, sceneId, userId, true))
        }
    }

    fun audienceExitRoom(
        live_id: String,
        link_id: String,
        requestListener: RequestListener<BaseModel>,
    ) {
        newNetworks(null, requestListener, ReqId.LINK_MIC_AUDIENCE_AGREE_INVITE) {
            it.create(StreamServer::class.java)
                .audienceExitRoom(AudienceExitRoomRequest(live_id, link_id))
        }
    }

    fun getShareInfo(requestListener: RequestListener<BaseModel>) {
        newNetworks(null, requestListener, ReqId.LINK_MIC_AUDIENCE_AGREE_INVITE) {
            it.create(StreamServer::class.java).getShareInfo()
        }
    }

    //获取直播分类
    fun getLiveType(liveId: String, requestListener: RequestListener<LiveTypeBean>) {
        newNetworks(null, requestListener, "") {
            it.create(StreamServer::class.java).getLiveType(liveId)
        }
    }

    fun uploadPushStreamInfo(info: String, requestListener: RequestListener<BaseModel>) {
        newNetworks(null, requestListener, "") {
            it.create(StreamServer::class.java).uploadPushStreamInfo(PushStreamInfoRequest(info))
        }
    }

    fun pushStreamHeartbeat(
        streamId: String,
        msg: String,
        requestListener: RequestListener<StreamHeartBeatBean>,
    ) {
        newNetworks(null, requestListener, "") {
            it.create(StreamServer::class.java)
                .pushStreamHeartbeat(PushStreamHeartBeatRequest(msg, streamId))
        }
    }

    //日榜
    fun cmDay(
        anchorId: Long,
        nextKey: String,
        lifecycleOwner: LifecycleOwner?,
        listener: RequestListener<LiveAudienceRankData>,
    ) {
        object : NetWorks<LiveAudienceRankData>(lifecycleOwner, listener) {
            override fun getBaseUrl(): String {
                return UrlConstanst.BASE_URL_LIVE_API_BOBOO_COM
            }

            override fun createCall(retrofit: Retrofit): Call<LiveAudienceRankData> {
                return retrofit.create(StreamServer::class.java)
                    .cmDay(anchorId, nextKey)
            }

            override fun getReqId(): String {
                return ""
            }
        }.start()
    }

    //日榜
    fun cmMonth(
        anchorId: Long,
        nextKey: String,
        lifecycleOwner: LifecycleOwner?,
        listener: RequestListener<LiveAudienceRankData>,
    ) {
        object : NetWorks<LiveAudienceRankData>(lifecycleOwner, listener) {
            override fun getBaseUrl(): String {
                return UrlConstanst.BASE_URL_LIVE_API_BOBOO_COM
            }

            override fun createCall(retrofit: Retrofit): Call<LiveAudienceRankData> {
                return retrofit.create(StreamServer::class.java)
                    .cmMonth(anchorId, nextKey)
            }

            override fun getReqId(): String {
                return ""
            }
        }.start()
    }

    //日榜
    fun cmTotal(
        anchorId: Long,
        nextKey: String,
        lifecycleOwner: LifecycleOwner?,
        listener: RequestListener<LiveAudienceRankData>,
    ) {
        object : NetWorks<LiveAudienceRankData>(lifecycleOwner, listener) {
            override fun getBaseUrl(): String {
                return UrlConstanst.BASE_URL_LIVE_API_BOBOO_COM
            }

            override fun createCall(retrofit: Retrofit): Call<LiveAudienceRankData> {
                return retrofit.create(StreamServer::class.java)
                    .cmTotal(anchorId, nextKey)
            }

            override fun getReqId(): String {
                return ""
            }
        }.start()
    }

    fun mainLiveEnter(
        userId: String,
        requestListener: RequestListener<MainLiveEnterBean>,
    ) {
        newNetworks(null, requestListener, "") {
            it.create(StreamServer::class.java)
                .mainEnterRoom(MainLiveEnterRequest(userId))
        }
    }

    fun getLiveRoomLists(
        userId: String, secenId: String,
        requestListener: RequestListener<LiveRoomListBean>,
    ) {
        newNetworks(null, requestListener, "") {
            it.create(StreamServer::class.java)
                .getLiveRoomLists(LiveRoomListRequest(userId, secenId))
        }
    }

    //关注
    fun liveZan(
        anchor_id_str: String,
        userId: String,
        roomId: String? = null,
        sceneId: String? = null,
        listener: RequestListener<BaseModel>
    ) {
        newNetworks(null, listener, "") {
            it.create(StreamServer::class.java)
                .liveZan(LiveZanReq(anchor_id_str, userId, roomId, sceneId))
        }
    }

}