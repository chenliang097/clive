package com.rongtuoyouxuan.chatlive.biz2.model.stream

import com.rongtuoyouxuan.chatlive.net2.BaseModel
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.google.gson.annotations.SerializedName

/**
 *
 * date:2022/8/11-10:22
 * des: 贡献榜及成员列表
 */
class CmMultiEntity(override val itemType: Int) : MultiItemEntity {
    companion object {
        const val item_top = 0//banner
        const val item_content = 1//item
    }

    var topList: ArrayList<LiveAudienceRankEntity> = arrayListOf()
    var liveItemEntity: LiveAudienceRankEntity? = null
}

data class LiveAudienceRankEntity(
    @SerializedName("diamond_amount")
    val diamondAmount: Float? = 0f,
    @SerializedName("is_follow")
    var isFollow: Boolean? = false,
    val nickname: String = "",
    @SerializedName("user_avatar")
    val userAvatar: String? = "",
    @SerializedName("user_id")
    val userId: Long? = 0L,
)

data class LiveAudienceRankRes(
    var isRefresh: Boolean = true,
    val list: ArrayList<LiveAudienceRankEntity>?,
    @SerializedName("live_id")
    val liveId: Long? = 0,
    @SerializedName("next_key")
    val nextKey: String? = "",
    @SerializedName("total_diamond")
    val totalDiamond: Float? = 0f,
)

data class LiveAudienceRankData(val data: LiveAudienceRankRes) : BaseModel()

data class LiveFollowsReq(
    @SerializedName("follow_id_str")
    val followId: String,
    @SerializedName("user_id_str")
    val userId: String,
    @SerializedName("room_id_str")
    val roomId: String? = null,
    @SerializedName("scene_id_str")
    val sceneId: String? = null,
    @SerializedName("status")
    val status: Int? = null,//关注状态,传入1是关注，传入0是取消关注
)

data class LiveZanReq(
    @SerializedName("anchor_id_str")
    val anchorId: String,
    @SerializedName("user_id_str")
    val userId: String,
    @SerializedName("room_id_str")
    val roomId: String? = null,
    @SerializedName("scene_id_str")
    val sceneId: String? = null
)

data class LiveChatRoomUserEntity(
    val nickname: String? = "",
    @SerializedName("user_id")
    val userId: Long? = 0L,
    val avatar: String? = "",
    @SerializedName("is_follow")
    var isFollow: Int = 0,
    val score: Long? = 0,
    val role: String? = "",
) {
    override fun equals(other: Any?): Boolean {
        if (other is LiveChatRoomUserEntity) {
            return other.userId == userId
        }
        return super.equals(other)
    }

    override fun hashCode(): Int {
        var result = nickname?.hashCode() ?: 0
        result = 31 * result + (userId?.hashCode() ?: 0)
        result = 31 * result + (avatar?.hashCode() ?: 0)
        result = 31 * result + isFollow
        result = 31 * result + score.hashCode()
        return result
    }
}

data class LiveChatRoomUserRes(
    val list: ArrayList<LiveChatRoomUserEntity>?,
    val page: Int,
    val size: Int,
    @SerializedName("page_total")
    val pageTotal: Int,
    val total: Int,
)

data class LiveChatRoomUserData(
    val data: LiveChatRoomUserRes,
) : BaseModel()

data class RankCountryRes(
    val country: String,
    val country_name: String,
)

data class RankCountryData(
    val data: ArrayList<RankCountryRes>,
) : BaseModel()