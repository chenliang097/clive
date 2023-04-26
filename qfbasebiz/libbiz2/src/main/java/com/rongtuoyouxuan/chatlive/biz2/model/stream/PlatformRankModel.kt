package com.rongtuoyouxuan.chatlive.biz2.model.stream

import com.rongtuoyouxuan.chatlive.net2.BaseModel
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.google.gson.annotations.SerializedName

/**
 * author:jianbo
 * date:2022/8/11-10:22
 * des: 平台榜单  直播榜、用户榜
 */
class PlatformRankMultiEntity(override val itemType: Int) : MultiItemEntity {
    companion object {
        const val item_top = 0//banner
        const val item_content = 1//item
    }

    var topList: ArrayList<PlatformRankEntity> = arrayListOf()
    var liveItemEntity: PlatformRankEntity? = null
}

data class PlatformRankEntity(
    @SerializedName("avatar")
    var userAvatar: String? = "",
    @SerializedName("is_followed")
    var isFollow: Boolean? = false,
    @SerializedName("live_id")
    var liveId: Long? = 0,
    var nickname: String = "",
    var score: Int = 0,
    var sort: Int = 0,
    @SerializedName("user_id")
    var userId: Long? = 0L,
    var isComplement: Boolean? = false,
)

data class PlatformRankRes(
    var isRefresh: Boolean = true,
    @SerializedName("users")
    val list: ArrayList<PlatformRankEntity>?,
    @SerializedName("live_id")
    val liveId: Long? = 0,
    @SerializedName("next_key")
    val nextKey: Int? = 0,
    @SerializedName("type")
    val type: String? = "",
)

data class PlatformRankData(val data: PlatformRankRes) : BaseModel()