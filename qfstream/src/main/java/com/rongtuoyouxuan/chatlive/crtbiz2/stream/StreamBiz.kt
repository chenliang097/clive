package com.rongtuoyouxuan.chatlive.crtbiz2.stream

import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.LifecycleOwner
import com.rongtuoyouxuan.chatlive.crtbiz2.ReqId
import com.rongtuoyouxuan.chatlive.crtbiz2.constanst.UrlConstanst
import com.rongtuoyouxuan.chatlive.crtbiz2.model.im.request.BlacklistRequest
import com.rongtuoyouxuan.chatlive.crtbiz2.model.im.request.MuteRequest
import com.rongtuoyouxuan.chatlive.crtbiz2.model.live.LiveRoomBean
import com.rongtuoyouxuan.chatlive.crtbiz2.model.live.LiveRoomExtraBean
import com.rongtuoyouxuan.chatlive.crtbiz2.model.live.StreamOnlineModel
import com.rongtuoyouxuan.chatlive.crtbiz2.model.main.LiveResponse
import com.rongtuoyouxuan.chatlive.crtbiz2.model.stream.*
import com.rongtuoyouxuan.chatlive.crtbiz2.model.stream.im.PushStreamHeartBeatRequest
import com.rongtuoyouxuan.chatlive.crtbiz2.model.stream.StreamStartInfoRequest
import newNetworks
import retrofit2.Call
import retrofit2.Retrofit

object StreamBiz {

    fun startlive(
        lifecycleOwner: LifecycleOwner?,
        classify_id: Int?, title: String?, pic: String?, longitude: Double?, latitude: Double?,
        listener: com.rongtuoyouxuan.chatlive.crtnet.RequestListener<StartStreamInfoBean?>?,
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
        listener: com.rongtuoyouxuan.chatlive.crtnet.RequestListener<StartStreamInfoBean?>?,
    ) {
        object : com.rongtuoyouxuan.chatlive.crtnet.NetWorks<StartStreamInfoBean?>(lifecycleOwner, listener) {
            override fun getBaseUrl(): String {
                return UrlConstanst.BASE_URL_LIVE_API_BOBOO_COM
            }

            override fun createCall(retrofit: Retrofit): Call<StartStreamInfoBean?>? {
                return retrofit.create(StreamServer::class.java)
                    .startlive(StreamStartInfoRequest(userId, userName))
            }

            override fun getReqId(): String {
                return ReqId.STREAM_CONMEN
            }
        }.start()
    }

    fun getStreamStatiscData(
        lifecycleOwner: LifecycleOwner?,
        userId: String,
        t:Int,
        listener: com.rongtuoyouxuan.chatlive.crtnet.RequestListener<StreamEndBean>,
    ) {
        object : com.rongtuoyouxuan.chatlive.crtnet.NetWorks<StreamEndBean>(lifecycleOwner, listener) {
            override fun getBaseUrl(): String {
                return UrlConstanst.BASE_URL_LIVE_API_BOBOO_COM
            }

            override fun createCall(retrofit: Retrofit): Call<StreamEndBean?>? {
                return retrofit.create(StreamServer::class.java)
                    .getStreamStatiscData(userId, t)
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
        listener: com.rongtuoyouxuan.chatlive.crtnet.RequestListener<StreamOnlineModel>,
    ) {
        object : com.rongtuoyouxuan.chatlive.crtnet.NetWorks<StreamOnlineModel>(lifecycleOwner, listener) {
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
        listener: com.rongtuoyouxuan.chatlive.crtnet.RequestListener<LiveRoomBean?>?,
    ) {
        object : com.rongtuoyouxuan.chatlive.crtnet.NetWorks<LiveRoomBean?>(lifecycleOwner, listener) {
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
        roomId: String?,
        sceneId: String?,
        userId: String?,
        anchorId: String?,
        count: Int?,
        lifecycleOwner: LifecycleOwner?,
        listener: com.rongtuoyouxuan.chatlive.crtnet.RequestListener<LikeLiveData>,
    ) {
        object : com.rongtuoyouxuan.chatlive.crtnet.NetWorks<LikeLiveData>(lifecycleOwner, listener) {
            override fun getBaseUrl(): String {
                return UrlConstanst.BASE_URL_LIVE_API_BOBOO_COM
            }

            override fun createCall(retrofit: Retrofit): Call<LikeLiveData> {
                return retrofit.create(StreamServer::class.java)
                    .liveLike(LikeLiveReq(roomId, sceneId, userId, anchorId))
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
        listener: com.rongtuoyouxuan.chatlive.crtnet.RequestListener<LiveChatRoomUserData>,
    ) {
        object : com.rongtuoyouxuan.chatlive.crtnet.NetWorks<LiveChatRoomUserData>(lifecycleOwner, listener) {
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
        listener: com.rongtuoyouxuan.chatlive.crtnet.RequestListener<com.rongtuoyouxuan.chatlive.crtnet.BaseModel>,
    ) {
        object : com.rongtuoyouxuan.chatlive.crtnet.NetWorks<com.rongtuoyouxuan.chatlive.crtnet.BaseModel>(lifecycleOwner, listener) {
            override fun getBaseUrl(): String {
                return UrlConstanst.BASE_URL_LIVE_API_BOBOO_COM
            }

            override fun createCall(retrofit: Retrofit): Call<com.rongtuoyouxuan.chatlive.crtnet.BaseModel> {
                return retrofit.create(StreamServer::class.java)
                    .liveFollows(LiveFollowsReq(followId, userId, roomId, sceneId, status))
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
        listener: com.rongtuoyouxuan.chatlive.crtnet.RequestListener<FollowStatusModel>,
    ) {
        object : com.rongtuoyouxuan.chatlive.crtnet.NetWorks<FollowStatusModel>(lifecycleOwner, listener) {
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
        listener: com.rongtuoyouxuan.chatlive.crtnet.RequestListener<LiveRoomExtraBean?>?,
    ) {
        object : com.rongtuoyouxuan.chatlive.crtnet.NetWorks<LiveRoomExtraBean?>(lifecycleOwner, listener) {
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
        listener: com.rongtuoyouxuan.chatlive.crtnet.RequestListener<LiveJoinGroupResData>,
    ) {
        object : com.rongtuoyouxuan.chatlive.crtnet.NetWorks<LiveJoinGroupResData>(lifecycleOwner, listener) {
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
        listener: com.rongtuoyouxuan.chatlive.crtnet.RequestListener<com.rongtuoyouxuan.chatlive.crtnet.BaseModel>,
    ) {
        object : com.rongtuoyouxuan.chatlive.crtnet.NetWorks<com.rongtuoyouxuan.chatlive.crtnet.BaseModel>(lifecycleOwner, listener) {
            override fun getBaseUrl(): String {
                return UrlConstanst.BASE_URL_LIVE_API_BOBOO_COM
            }

            override fun createCall(retrofit: Retrofit): Call<com.rongtuoyouxuan.chatlive.crtnet.BaseModel> {
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
        requestListener: com.rongtuoyouxuan.chatlive.crtnet.RequestListener<EnterRoomBean>,
    ) {
        newNetworks(null, requestListener, "") {
            it.create(StreamServer::class.java)
                .getRoomInfo(EnterRoomRequestBean(roomId, sceneId, userId, true))
        }
    }

    fun getRoomInfoExtra(
        roomId: String, sceneId: String, userId: String,
        requestListener: com.rongtuoyouxuan.chatlive.crtnet.RequestListener<RoomInfoExtraBean>,
    ) {
        newNetworks(null, requestListener, "") {
            it.create(StreamServer::class.java)
                .getRoomInfoExtra(EnterRoomRequestBean(roomId, sceneId, userId, true))
        }
    }

    fun audienceExitRoom(
        live_id: String,
        link_id: String,
        requestListener: com.rongtuoyouxuan.chatlive.crtnet.RequestListener<com.rongtuoyouxuan.chatlive.crtnet.BaseModel>,
    ) {
        newNetworks(null, requestListener, ReqId.LINK_MIC_AUDIENCE_AGREE_INVITE) {
            it.create(StreamServer::class.java)
                .audienceExitRoom(AudienceExitRoomRequest(live_id, link_id))
        }
    }

    fun getShareInfo(requestListener: com.rongtuoyouxuan.chatlive.crtnet.RequestListener<com.rongtuoyouxuan.chatlive.crtnet.BaseModel>) {
        newNetworks(null, requestListener, ReqId.LINK_MIC_AUDIENCE_AGREE_INVITE) {
            it.create(StreamServer::class.java).getShareInfo()
        }
    }

    //获取直播分类
    fun getLiveType(liveId: String, requestListener: com.rongtuoyouxuan.chatlive.crtnet.RequestListener<LiveTypeBean>) {
        newNetworks(null, requestListener, "") {
            it.create(StreamServer::class.java).getLiveType(liveId)
        }
    }

    fun uploadPushStreamInfo(info: String, requestListener: com.rongtuoyouxuan.chatlive.crtnet.RequestListener<com.rongtuoyouxuan.chatlive.crtnet.BaseModel>) {
        newNetworks(null, requestListener, "") {
            it.create(StreamServer::class.java).uploadPushStreamInfo(PushStreamInfoRequest(info))
        }
    }

    fun pushStreamHeartbeat(
        roomId: String,
        sceneId: String,
        userId: String,
        requestListener: com.rongtuoyouxuan.chatlive.crtnet.RequestListener<StreamHeartBeatBean>,
    ) {
        newNetworks(null, requestListener, "") {
            it.create(StreamServer::class.java)
                .pushStreamHeartbeat(PushStreamHeartBeatRequest(userId, roomId, sceneId))
        }
    }

    //日榜
    fun cmDay(
        anchorId: Long,
        nextKey: String,
        lifecycleOwner: LifecycleOwner?,
        listener: com.rongtuoyouxuan.chatlive.crtnet.RequestListener<LiveAudienceRankData>,
    ) {
        object : com.rongtuoyouxuan.chatlive.crtnet.NetWorks<LiveAudienceRankData>(lifecycleOwner, listener) {
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
        listener: com.rongtuoyouxuan.chatlive.crtnet.RequestListener<LiveAudienceRankData>,
    ) {
        object : com.rongtuoyouxuan.chatlive.crtnet.NetWorks<LiveAudienceRankData>(lifecycleOwner, listener) {
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
        listener: com.rongtuoyouxuan.chatlive.crtnet.RequestListener<LiveAudienceRankData>,
    ) {
        object : com.rongtuoyouxuan.chatlive.crtnet.NetWorks<LiveAudienceRankData>(lifecycleOwner, listener) {
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
        requestListener: com.rongtuoyouxuan.chatlive.crtnet.RequestListener<MainLiveEnterBean>,
    ) {
        newNetworks(null, requestListener, "") {
            it.create(StreamServer::class.java)
                .mainEnterRoom(MainLiveEnterRequest(userId))
        }
    }

    fun getLiveRoomLists(
        userId: String, secenId: String,
        requestListener: com.rongtuoyouxuan.chatlive.crtnet.RequestListener<LiveRoomListBean>,
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
        listener: com.rongtuoyouxuan.chatlive.crtnet.RequestListener<com.rongtuoyouxuan.chatlive.crtnet.BaseModel>
    ) {
        newNetworks(null, listener, "") {
            it.create(StreamServer::class.java)
                .liveZan(LiveZanReq(anchor_id_str, userId, roomId, sceneId))
        }
    }

    //上传主播信息
    fun uploadAnchorInfo(
        request:StartPushStreamRequest,
        listener: com.rongtuoyouxuan.chatlive.crtnet.RequestListener<com.rongtuoyouxuan.chatlive.crtnet.BaseModel>
    ) {
        newNetworks(null, listener, "") {
            it.create(StreamServer::class.java)
                .uploadAnchorInfo(request)
        }
    }

    fun setUserAllowRange(
        request:LiveRoomVisibleRangeRequest,
        listener: com.rongtuoyouxuan.chatlive.crtnet.RequestListener<com.rongtuoyouxuan.chatlive.crtnet.BaseModel>
    ) {
        newNetworks(null, listener, "") {
            it.create(StreamServer::class.java)
                .setUserAllowRange(request)
        }
    }

    fun getRoomManagerList(
        roomId: String,
        page: Int,
        size:Int,
        listener: com.rongtuoyouxuan.chatlive.crtnet.RequestListener<RoomManagerListBean>
    ) {
        newNetworks(null, listener, "") {
            it.create(StreamServer::class.java)
                .getRoomManagerList(roomId, page, size)
        }
    }

    fun deleteRoomManagerList(
        request: AnchorRoomSettingRequest,
        listener: com.rongtuoyouxuan.chatlive.crtnet.RequestListener<com.rongtuoyouxuan.chatlive.crtnet.BaseModel>
    ) {
        newNetworks(null, listener, "") {
            it.create(StreamServer::class.java)
                .delRoomManager(request)
        }
    }

    fun getManagerBlackList(
        roomId: String,
        page: Int,
        size:Int,
        listener: com.rongtuoyouxuan.chatlive.crtnet.RequestListener<RoomManagerBlackListBean>
    ) {
        newNetworks(null, listener, "") {
            it.create(StreamServer::class.java)
                .getManagerBlackList(roomId, page, size)
        }
    }

    fun relieveRoomBlack(
        request: BlacklistRequest,
        listener: com.rongtuoyouxuan.chatlive.crtnet.RequestListener<com.rongtuoyouxuan.chatlive.crtnet.BaseModel>
    ) {
        newNetworks(null, listener, "") {
            it.create(StreamServer::class.java)
                .relieveRoomBlack(request)
        }
    }

    fun getRoomMuteList(
        lifecycleOwner: LifecycleOwner?,
        sceneId: String,
        roomId: String,
        page: Int,
        size: Int,
        listener: com.rongtuoyouxuan.chatlive.crtnet.RequestListener<RoomManagerMuteListBean>
    ) {
        object : com.rongtuoyouxuan.chatlive.crtnet.NetWorks<RoomManagerMuteListBean>(lifecycleOwner, listener) {
            override fun createCall(retrofit: Retrofit): Call<RoomManagerMuteListBean> {
                return retrofit.create(StreamServer::class.java).getManagerMuteList(
                    UrlConstanst.BASE_URL_MUTE_LIST_API_BOBOO_COM + "?page=$page&size=$size",
                        AnchorRoomMuteListRequest(roomId, sceneId)
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

    fun relieveRoomMute(
        request: MuteRequest,
        listener: com.rongtuoyouxuan.chatlive.crtnet.RequestListener<com.rongtuoyouxuan.chatlive.crtnet.BaseModel>
    ) {
        newNetworks(null, listener, "") {
            it.create(StreamServer::class.java)
                .relieveRoomMute(request)
        }
    }

    fun getRoomMaskWords(
        roomId: String,
        listener: com.rongtuoyouxuan.chatlive.crtnet.RequestListener<RoomMaskWordsBean>
    ) {
        newNetworks(null, listener, "") {
            it.create(StreamServer::class.java)
                .getRoomMaskWords(roomId)
        }
    }

    fun setRoomMaskWord(
        request: SetRoomMaskWordsRequest,
        listener: com.rongtuoyouxuan.chatlive.crtnet.RequestListener<com.rongtuoyouxuan.chatlive.crtnet.BaseModel>
    ) {
        newNetworks(null, listener, "") {
            it.create(StreamServer::class.java)
                .setRoomMaskWord(request)
        }
    }

    fun deleteRoomMaskWord(
        request: SetRoomMaskWordsRequest,
        listener: com.rongtuoyouxuan.chatlive.crtnet.RequestListener<com.rongtuoyouxuan.chatlive.crtnet.BaseModel>
    ) {
        newNetworks(null, listener, "") {
            it.create(StreamServer::class.java)
                .deleteRoomMaskWord(request)
        }
    }

    fun getPopularityRank(
        userId: String,
        page: Int,
        size: Int,
        listener: com.rongtuoyouxuan.chatlive.crtnet.RequestListener<PopolarityRankBean>
    ) {
        newNetworks(null, listener, "") {
            it.create(StreamServer::class.java)
                .getPopularityRank(page, size, userId)
        }
    }

    fun getStreamEnd(
        streamEndRequest: StreamEndRequest,
        listener: com.rongtuoyouxuan.chatlive.crtnet.RequestListener<StreamEndBean>
    ) {
        newNetworks(null, listener, "") {
            it.create(StreamServer::class.java)
                .getStreamEnd(streamEndRequest)
        }
    }

    fun getStreamEndInfo(
        streamEndRequest: StreamEndRequest,
        listener: com.rongtuoyouxuan.chatlive.crtnet.RequestListener<StreamEndBean>
    ) {
        newNetworks(null, listener, "") {
            it.create(StreamServer::class.java)
                .getStreamEndInfo(streamEndRequest)
        }
    }

    fun getRecommendList(
        lifecycleCoroutineScope: LifecycleCoroutineScope,
        request: RecommenListRequestBean,
        listener: com.rongtuoyouxuan.chatlive.crtnet.RequestListener<LiveResponse>
    ) {
        newNetworks(null, listener, "") {
            it.create(StreamServer::class.java)
                .getRecommendList(request)
        }
    }

}