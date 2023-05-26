package com.rongtuoyouxuan.chatlive.crtbiz2.im

import com.rongtuoyouxuan.chatlive.crtbiz2.model.im.msg.textmsg.RTTxtMsgRequest
import com.rongtuoyouxuan.chatlive.crtbiz2.model.im.request.*
import com.rongtuoyouxuan.chatlive.crtbiz2.model.im.response.*
import com.rongtuoyouxuan.chatlive.crtbiz2.model.mine.response.VersionInfoModel
import retrofit2.Call
import retrofit2.http.*

/**
 * Describe:
 *
 * @author Ning
 * @date 2022/7/15
 */
interface ChatIMServer {

    /**
     * 用户(游客)获取user token后，获取长连信息
     */
    @POST("/user/token")
    fun getImToken(@Body imTokenRequest: ImTokenRequest): Call<IMTokenModel>

    /******************************** 举报、禁言、黑名单 start ********************************************/

    /**
     * 直播间踢人  移出聊天室成员
     */
    @POST("/chatroom/users/remove")
    fun removeChatRoomUser(@Body chatRoomRequest: ChatRoomRequest): Call<OperateResultModel>

    /******************************** 举报、禁言、黑名单 end ********************************************/


    /******************************** 举报、禁言、黑名单 start ********************************************/

    /**
     * 举报
     */
    @POST("/report/save")
    fun saveReport(@Body saveReportRequest: SaveReportRequest): Call<com.rongtuoyouxuan.chatlive.crtnet.BaseModel>

    /**
     * 禁言
     */
    @POST("/userProxy/v1/user/forbidSpeak")
    fun mute(@Body muteRequest: MuteRequest): Call<OperateResultModel>

    /**
     * 取消禁言
     */
    @POST("/userProxy/v1/user/cancelForbidSpeak")
    fun cancelMute(@Body muteRequest: MuteRequest): Call<OperateResultModel>

    /**
     * 拉黑
     */
    @POST("/userProxy/v1/user/black")
    fun addBlacklist(@Body blacklistRequest: BlacklistRequest): Call<com.rongtuoyouxuan.chatlive.crtnet.BaseModel>

    /**
     * 移除黑名单
     */
    @POST("/userProxy/v1/user/cancelBlack")
    fun removeBlacklist(@Body blacklistRequest: BlacklistRequest): Call<OperateResultModel>

    /**
     * 获取黑名单列表
     */
    @GET("/blacklist")
    fun getBlacklist(@QueryMap map: Map<String, @JvmSuppressWildcards Any>): Call<BlackListModel>


    /******************************** 举报、禁言、黑名单 end ********************************************/


    /******************************** 公告 start ********************************************/



    /******************************** 群相关 start ********************************************/

    /**
     * 粉丝群信息
     */
    @GET("/group")
    fun getGroupInfo(@QueryMap map: Map<String, @JvmSuppressWildcards Any>): Call<GroupInfoModel>

    /**
     * 退群
     */
    @POST("/group/leave")
    fun quitGroup(@Body groupRequest: GroupRequest): Call<com.rongtuoyouxuan.chatlive.crtnet.BaseModel>

    /**
     * 群全体禁言/取消禁言
     */
    @POST("/group/mute")
    fun groupMute(@Body groupRequest: GroupRequest): Call<GroupInfoModel>

    /**
     * 保存粉丝群信息、群名称
     */
    @POST("/group/save")
    fun saveGroupInfo(@Body groupRequest: GroupRequest): Call<GroupInfoModel>

    /**
     * 群消息开启、关闭免打扰
     */
    @POST("/group/user/notice")
    fun switchGroupNotice(@Body groupRequest: GroupRequest): Call<GroupUserModel>

    /**
     * 粉丝群用户列表
     */
    @GET("/group/users")
    fun getGroupUsers(@QueryMap map: Map<String, @JvmSuppressWildcards Any>): Call<GroupUserListModel>

    /**
     * 移出群成员
     */
    @POST("/group/users/remove")
    fun removeGroupUser(@Body groupRequest: GroupRequest): Call<OperateResultModel>

    /**
     * 用户粉丝群列表
     */
    @POST("/user/groups")
    fun getUserGroups(@Body userGroupsRequest: UserGroupsRequest): Call<GroupInfoListModel>

    /******************************** 群相关 end ********************************************/


    /******************************** 群用户 start ********************************************/

    /**
     * 获取用户访问权限 是否拉黑、是否禁言, 游客默认直播间禁言状态
     */
    @GET("/user/access/permissions")
    fun getUserAccessPermission(@QueryMap map: Map<String, @JvmSuppressWildcards Any>): Call<OperateResultModel>

    /******************************** 群用户 end ********************************************/

    /**
     * 检查更新
     * */
    @GET("/version")
    fun getUpdateVersion(): Call<VersionInfoModel>

    /**
     * push设备注册
     */
    @POST("/device/register")
    fun registerDevice(@Body registerDeviceRequest: RegisterDeviceRequest): Call<com.rongtuoyouxuan.chatlive.crtnet.BaseModel>


    //直播间-资料卡
    @GET("/user/data")
    fun getUserCardInfo(@Query("user_id") uid: Long): Call<UserCardModel>

    /**
     * 直播间全员禁言
     */
    @POST("/chatroom/mute")
    fun bannedChatRoomAllUser(@Body bannedRequest: BannedRequest): Call<com.rongtuoyouxuan.chatlive.crtnet.BaseModel>

    /**
     *
     */
    @POST("/medium/v1/barrage/pushBarrage")
    fun sendTxtMsg(@Body rtTxtMsgRequest: RTTxtMsgRequest): Call<com.rongtuoyouxuan.chatlive.crtnet.BaseModel>

    /**
     * 设置房管
     */
    @POST("/userProxy/v1/user/addRoomAdmin")
    fun setRoomManager(@Body request: SetRoomManagerRequest): Call<com.rongtuoyouxuan.chatlive.crtnet.BaseModel>
}