package com.rongtuoyouxuan.chatlive.crtbiz2.model.stream

import com.google.gson.annotations.SerializedName

/**
 * 
 * date:2022/8/11-15:38
 * des: 直播间-资料卡
 */
data class LiveUserRes(
    @SerializedName("anchor_level")
    val anchorLevel: Int? = 0,
    val avatar: String? = "",
    val birthday: String? = "",
    @SerializedName("country_code")
    val countryCode: String? = "",
    @SerializedName("fans_total")
    val fansTotal: Int? = 0,
    @SerializedName("follow_total")
    val followTotal: Int? = 0,
    val gender: Int? = 0,
    val nickname: String? = "",
    @SerializedName("send_coin")
    val sendCoin: Int? = 0,
    val signature: String? = "",
    @SerializedName("user_id")
    val userId: Long? = 0,
    @SerializedName("user_level")
    val userLevel: Int? = 0,
    @SerializedName("is_follows")
    var isFollows: Boolean? = false,
    @SerializedName("is_fans_group")
    val isFansGroup: Boolean? = false,
    @SerializedName("realcert_status")
    val realcertStatus: Int? = 0,
    @SerializedName("show_id")
    val showId: String? = "",
    @SerializedName("region_code")
    val regionCode: String? = "",
    @SerializedName("role")
    val role: String? = "",
    @SerializedName("write_off_status")
    val writeOffStatus: Int? = 0,
    @SerializedName("live_id")
    val liveId: Long? = 0L,
    @SerializedName("is_on_live")
    val isOnLive: Boolean? = false,
    @SerializedName("is_on_line")
    val isOnLine: Boolean? = false,
    @SerializedName("is_be_block")
    val isBeBlock: Boolean? = false,//当前登录用户被对方拉黑
    @SerializedName("is_block")
    var isBlock: Boolean? = false,//当前登录用户是否拉黑对方

)

data class LiveUserData(val data: LiveUserRes) : com.rongtuoyouxuan.chatlive.crtnet.BaseModel()

data class ProfileUserData(val userId: Long, val userName: String, val avatar: String)
data class LiveJoinGroupReq(
    @SerializedName("anchor_id")
    val anchorInt: Long,
    @SerializedName("live_id")
    val liveId: Long,
)

data class LiveJoinGroupRes(
    @SerializedName("group_id")
    val groupId: Long? = 0L,
    @SerializedName("group_name")
    val groupName: String? = "",
)

data class LiveJoinGroupResData(val data: LiveJoinGroupRes) : com.rongtuoyouxuan.chatlive.crtnet.BaseModel()

data class LiveMarqueeEntity(
    val roomId: Long? = 0,
    val gameId: Int? = 0,
    val gameVersion: String? = "",
    val gameFileZip: String? = "",
    val supportPop: Boolean? = false
)