package com.rongtuoyouxuan.chatlive.biz2.stream

import com.rongtuoyouxuan.chatlive.biz2.model.im.request.BlacklistRequest
import com.rongtuoyouxuan.chatlive.biz2.model.im.request.MuteRequest
import com.rongtuoyouxuan.chatlive.biz2.model.live.LiveRoomBean
import com.rongtuoyouxuan.chatlive.biz2.model.live.LiveRoomExtraBean
import com.rongtuoyouxuan.chatlive.biz2.model.live.StreamOnlineModel
import com.rongtuoyouxuan.chatlive.biz2.model.stream.*
import com.rongtuoyouxuan.chatlive.biz2.model.stream.im.PushStreamHeartBeatRequest
import com.rongtuoyouxuan.chatlive.net2.BaseModel
import com.rongtuoyouxuan.chatlive.biz2.model.stream.StreamStartInfoRequest
import retrofit2.Call
import retrofit2.http.*

interface StreamServer {
    @POST("/medium/v1/push/getRoomInfoByUserId")
    fun startlive(@Body streamStartRequest: StreamStartInfoRequest): Call<StartStreamInfoBean?>?

    @GET("/chatroom/users")
    fun getOnlineList(
        @Query("chatroom_id") chatroom_id: String?, @Query("anchor_id") anchor_id: String?,
        @Query("page") page: Int?, @Query("size") size: Int?,
    ): Call<StreamOnlineModel?>?

    @GET("/audience/see/live")
    fun getRoomInfo(@Query("live_id") liveId: String?): Call<LiveRoomBean?>?

    @GET("/userProxy/v1/user/getStatistics")
    fun getStreamStatiscData(@Query("user_id") userId: String?, @Query("t") t: Int): Call<StreamEndBean?>?

    //主播点赞
    @POST("/anchor/like/live")
    fun liveLike(@Body request: LikeLiveReq): Call<LikeLiveData>

    //观众点赞
    @POST("/audience/like/live")
    fun liveAudienceLike(@Body request: LikeLiveReq): Call<LikeLiveData>

    //成员列表
    @GET("/chatroom/users")
    fun liveChatroomUsers(
        @Query("chatroom_id") liveId: String,
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("anchor_id") anchorId: String,
    ): Call<LiveChatRoomUserData>

    //关注某人
    @POST("/medium/v1/room/userInteresting")
    fun liveFollows(
        @Body request: LiveFollowsReq): Call<BaseModel>

    //是否关注某个人
    @GET("/follows/has")
    fun getFollowStatus(
        @Query("user_id") userId: Long,
        @Query("follow_id") followId: Long,
    ): Call<FollowStatusModel>

    @GET("/audience/see/live/extra")
    fun getRoomExtraInfo(
        @Query("live_id") liveId: Long?,
        @Query("anchor_id") anchorId: Long?,
    ): Call<LiveRoomExtraBean?>?

    @POST("/account/join_group")
    fun liveJoinGroup(@Body request: LiveJoinGroupReq): Call<LiveJoinGroupResData>

    @POST("/audience/exit/live")
    fun audienceExitRoom(@Body request: AudienceExitRoomRequest): Call<BaseModel>

    @GET("/audience/live/shares")
    fun getShareInfo(): Call<BaseModel>

    @POST("/audience/shared/live")
    fun sharedLive(@Body request: ShareLiveRequest): Call<BaseModel>

    @GET("/live/classify")
    fun getLiveType(@Query("live_id") streamId: String): Call<LiveTypeBean>

    @POST("/anchor/debug/log")
    fun uploadPushStreamInfo(@Body request: PushStreamInfoRequest): Call<BaseModel>

    @POST("/anchor/live/push/heartbeat")
    fun pushStreamHeartbeat(@Body request: PushStreamHeartBeatRequest): Call<StreamHeartBeatBean>

    //日榜
    @GET("/live_diamond/rank_day")
    fun cmDay(
        @Query("anchor_id") anchorId: Long,
        @Query("next_key") nextKey: String,
    ): Call<LiveAudienceRankData>

    //月榜
    @GET("/live_diamond/rank_month")
    fun cmMonth(
        @Query("anchor_id") anchorId: Long,
        @Query("next_key") nextKey: String,
    ): Call<LiveAudienceRankData>

    //总榜
    @GET("/live_diamond/rank_total")
    fun cmTotal(
        @Query("anchor_id") anchorId: Long,
        @Query("next_key") nextKey: String,
    ): Call<LiveAudienceRankData>

    @POST("/medium/v1/pull/userAction")
    fun enterRoom(@Body request: EnterRoomRequestBean): Call<EnterRoomBean>

    @POST("/medium/v1/room/roomBasicInfo")
    fun getRoomInfo(@Body request: EnterRoomRequestBean): Call<EnterRoomBean>

    @POST("/medium/v1/room/roomCompleteInfo")
    fun getRoomInfoExtra(@Body request: EnterRoomRequestBean): Call<RoomInfoExtraBean>

    @POST("/medium/v1/pull/getFireEstMedium")
    fun mainEnterRoom(@Body request: MainLiveEnterRequest): Call<MainLiveEnterBean>

    @POST("/medium/v1/pull/getRoomListByUser")
    fun getLiveRoomLists(@Body request: LiveRoomListRequest): Call<LiveRoomListBean>

    @POST("/medium/v1/room/userLiking")
    fun liveZan(@Body request: LiveZanReq): Call<BaseModel>

    @POST("/userProxy/v1/user/faceIdentification")
    fun getTencentFaceData(@Body request: LiveZanReq): Call<BaseModel>

    @POST("/medium/v1/push/updateSceneBaseInfo")
    fun uploadAnchorInfo(@Body request: StartPushStreamRequest): Call<BaseModel>

    @POST("/userProxy/v1/user/setAllowRule")
    fun setUserAllowRange(@Body request: LiveRoomVisibleRangeRequest): Call<BaseModel>

    @GET("/userProxy/v1/user/roomAdminList")
    fun getRoomManagerList(@Query("room_id") roomId: String, @Query("page") page: Int, @Query("size") size: Int): Call<RoomManagerListBean>

    @POST("/userProxy/v1/user/delRoomAdmin")
    fun delRoomManager(@Body request: AnchorRoomSettingRequest): Call<BaseModel>

    @GET("/userProxy/v1/user/blackList")
    fun getManagerBlackList(@Query("room_id") roomId: String, @Query("page") page: Int, @Query("size") size: Int): Call<RoomManagerBlackListBean>

    @POST("/userProxy/v1/user/cancelBlack")
    fun relieveRoomBlack(@Body request: BlacklistRequest): Call<BaseModel>

    @POST
    fun getManagerMuteList(@Url url: String?,
                           @Body request: AnchorRoomMuteListRequest?): Call<RoomManagerMuteListBean>

    @POST("/userProxy/v1/user/cancelForbidSpeak")
    fun relieveRoomMute(@Body request: MuteRequest): Call<BaseModel>

    @GET("/userProxy/v1/user/getShieldWord")
    fun getRoomMaskWords(@Query("room_id") roomId:String): Call<RoomMaskWordsBean>

    @POST("/userProxy/v1/user/setShieldWord")
    fun setRoomMaskWord(@Body request:SetRoomMaskWordsRequest): Call<BaseModel>

    @POST("/userProxy/v1/user/delShieldWord")
    fun deleteRoomMaskWord(@Body request:SetRoomMaskWordsRequest): Call<BaseModel>

    @GET("/userProxy/v1/user/rankList")
    fun getPopularityRank(@Query("page") page:Int, @Query("size") size:Int, @Query("user_id") userId:String): Call<PopolarityRankBean>


}