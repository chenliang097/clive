package com.rongtuoyouxuan.chatlive.crtbiz2.im

import androidx.lifecycle.LifecycleOwner
import com.rongtuoyouxuan.chatlive.crtbiz2.ReqId
import com.rongtuoyouxuan.chatlive.crtbiz2.constanst.UrlConstanst
import com.rongtuoyouxuan.chatlive.crtbiz2.model.im.msg.textmsg.RTTxtMsgRequest
import com.rongtuoyouxuan.chatlive.crtbiz2.model.im.request.*
import com.rongtuoyouxuan.chatlive.crtbiz2.model.im.response.*
import newNetworkForChatIM
import retrofit2.Call
import retrofit2.Retrofit

/**
 * Describe: IM
 *
 * @author Ning
 * @date 2022/7/15
 */
object ChatIMBiz {

    /**
     * 用户(游客)获取user token后，获取长连信息
     */
    fun getImToken(
        imTokenRequest: ImTokenRequest,
        listener: com.rongtuoyouxuan.chatlive.crtnet.RequestListener<IMTokenModel>,
    ) = newNetworkForChatIM(
        null,
        listener,
        "",
    ) {
        it.create(ChatIMServer::class.java).getImToken(imTokenRequest)
    }

    /**
     * 直播间踢人  移出聊天室成员
     */
    fun removeChatRoomUser(
        chatRoomRequest: ChatRoomRequest,
        listener: com.rongtuoyouxuan.chatlive.crtnet.RequestListener<OperateResultModel>,
    ) = newNetworkForChatIM(
        null,
        listener,
        "",
    ) {
        it.create(ChatIMServer::class.java).removeChatRoomUser(chatRoomRequest)
    }

    /**
     * 举报
     */
    fun saveReport(
        saveReportRequest: SaveReportRequest,
        listener: com.rongtuoyouxuan.chatlive.crtnet.RequestListener<com.rongtuoyouxuan.chatlive.crtnet.BaseModel>,
    ) = newNetworkForChatIM(
        null,
        listener,
        "",
    ) {
        it.create(ChatIMServer::class.java).saveReport(saveReportRequest)
    }

    /**
     * 禁言
     */
    fun mute(
        muteRequest: MuteRequest,
        listener: com.rongtuoyouxuan.chatlive.crtnet.RequestListener<OperateResultModel>,
    ) = newNetworkForChatIM(
        null,
        listener,
        "",
    ) {
        it.create(ChatIMServer::class.java).mute(muteRequest)
    }

    /**
     * 取消禁言
     */
    fun cancelMute(
        muteRequest: MuteRequest,
        listener: com.rongtuoyouxuan.chatlive.crtnet.RequestListener<OperateResultModel>,
    ) = newNetworkForChatIM(
        null,
        listener,
        "",
    ) {
        it.create(ChatIMServer::class.java).cancelMute(muteRequest)
    }

    /**
     * 拉黑
     */
    fun addBlacklist(
        blacklistRequest: BlacklistRequest,
        listener: com.rongtuoyouxuan.chatlive.crtnet.RequestListener<com.rongtuoyouxuan.chatlive.crtnet.BaseModel>,
    ) = newNetworkForChatIM(
        null,
        listener,
        "",
    ) {
        it.create(ChatIMServer::class.java).addBlacklist(blacklistRequest)
    }

    /**
     * 移除黑名单
     */
    fun removeBlacklist(
        blacklistRequest: BlacklistRequest,
        listener: com.rongtuoyouxuan.chatlive.crtnet.RequestListener<OperateResultModel>,
    ) = newNetworkForChatIM(
        null,
        listener,
        "",
    ) {
        it.create(ChatIMServer::class.java).removeBlacklist(blacklistRequest)
    }

    /**
     * 获取黑名单列表
     */
    fun getBlacklist(
        map: Map<String, @JvmSuppressWildcards Any>,
        listener: com.rongtuoyouxuan.chatlive.crtnet.RequestListener<BlackListModel>,
    ) = newNetworkForChatIM(
        null,
        listener,
        "",
    ) {
        it.create(ChatIMServer::class.java).getBlacklist(map)
    }

    /**
     * 粉丝群信息
     */
    fun getGroupInfo(
        map: Map<String, @JvmSuppressWildcards Any>,
        listener: com.rongtuoyouxuan.chatlive.crtnet.RequestListener<GroupInfoModel>,
    ) = newNetworkForChatIM(
        null,
        listener,
        "",
    ) {
        it.create(ChatIMServer::class.java).getGroupInfo(map)
    }

    fun removeGroupUser(
        groupRequest: GroupRequest,
        listener: com.rongtuoyouxuan.chatlive.crtnet.RequestListener<OperateResultModel>,
    ) = newNetworkForChatIM(
        null,
        listener,
        "",
    ) {
        it.create(ChatIMServer::class.java).removeGroupUser(groupRequest)
    }

    /**
     * 用户粉丝群列表
     */
    fun getUserGroups(
        userGroupsRequest: UserGroupsRequest,
        listener: com.rongtuoyouxuan.chatlive.crtnet.RequestListener<GroupInfoListModel>,
    ) = newNetworkForChatIM(
        null,
        listener,
        "",
    ) {
        it.create(ChatIMServer::class.java).getUserGroups(userGroupsRequest)
    }

    /**
     * 获取用户访问权限 是否拉黑、是否禁言, 游客默认直播间禁言状态
     */
    fun getUserAccessPermission(
        map: Map<String, @JvmSuppressWildcards Any>,
        listener: com.rongtuoyouxuan.chatlive.crtnet.RequestListener<OperateResultModel>,
    ) = newNetworkForChatIM(
        null,
        listener,
        "",
    ) {
        it.create(ChatIMServer::class.java).getUserAccessPermission(map)
    }

    /**
     * 聊天室禁言
     */
    fun bannedChatRoomAllUser(
        bannedRequest: BannedRequest,
        listener: com.rongtuoyouxuan.chatlive.crtnet.RequestListener<com.rongtuoyouxuan.chatlive.crtnet.BaseModel>,
    ) = newNetworkForChatIM(
        null,
        listener,
        "",
    ) {
        it.create(ChatIMServer::class.java).bannedChatRoomAllUser(bannedRequest)
    }


    /**
     * @param uid
     * @param listener
     * 获取资料卡
     */
    fun getUserCardInfo(
        uid: Long,
        lifecycleOwner: LifecycleOwner? = null,
        listener: com.rongtuoyouxuan.chatlive.crtnet.RequestListener<UserCardModel>,
    ) {
        object : com.rongtuoyouxuan.chatlive.crtnet.NetWorks<UserCardModel>(lifecycleOwner, listener) {
            override fun createCall(retrofit: Retrofit): Call<UserCardModel> {
                return retrofit.create(ChatIMServer::class.java).getUserCardInfo(uid)
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
     * 聊天室发言
     */
    fun sendTextMsg(
        rtTxtMsgRequest: RTTxtMsgRequest,
        listener: com.rongtuoyouxuan.chatlive.crtnet.RequestListener<com.rongtuoyouxuan.chatlive.crtnet.BaseModel>,
    ) = newNetworkForChatIM(
        null,
        listener,
        "",
    ) {
        it.create(ChatIMServer::class.java).sendTxtMsg(rtTxtMsgRequest)
    }

    fun setRoomManager(
        request: SetRoomManagerRequest,
        listener: com.rongtuoyouxuan.chatlive.crtnet.RequestListener<com.rongtuoyouxuan.chatlive.crtnet.BaseModel>,
    ) = newNetworkForChatIM(
        null,
        listener,
        "",
    ) {
        it.create(ChatIMServer::class.java).setRoomManager(request)
    }


}