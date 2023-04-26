package com.rongtuoyouxuan.chatlive.biz2.model.main

import com.rongtuoyouxuan.chatlive.biz2.model.stream.PullStreamUrlBean
import com.rongtuoyouxuan.chatlive.net2.BaseModel
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.google.gson.annotations.SerializedName

class MainMultiEntity(override val itemType: Int) : MultiItemEntity {
    companion object {
        const val item_banner = 0//banner
        const val item_area = 1//热门-快捷入口
        const val item_content = 2//item
        const val item_end = 3 //没有更多数据
        const val item_focus = 4//关注头部
    }

    var bannerEntityList: LiveAdsList? = null
    var liveItemEntity: LiveMainEntity? = null
//    override fun getItemType(): Int = type

}

data class LiveItemEntity(
    val id: String,
    @SerializedName("anchor_id")
    val anchorId: Long? = 0,
    val title: String? = "",
    val pic: String? = "",
    @SerializedName("live_tag")
    val liveTag: Int? = 0,
    @SerializedName("classify_id")
    val streamType: Int? = 1, // 1：视频， 2：游戏
    @SerializedName("live_type")
    val liveType: String? = "",//normal:普通 ,pay:付费，private:私密直播
    @SerializedName("live_mark")
    val liveMark: String? = "", // 角标
    @SerializedName("live_redbag")
    val liveRedBag: Int? = 0, //红包Icon 1显示 0不显示
    var isSelected: Boolean = false,//本地使用
)

data class CdnSdkItemEntity(
    @SerializedName("stream_id")
    val streamId: String,
    var play_urls: PullStreamUrlBean = PullStreamUrlBean(),
)

data class LiveMainEntity(
    val live: LiveItemEntity?,
    @SerializedName("cdn_sdk")
    val cdnSdkEntity: CdnSdkItemEntity?,
)

data class LiveData(
    val lives: ArrayList<LiveMainEntity>?,
    val ads: ArrayList<LiveAdsEntity>?,
    val page: Int? = 1,
    val total: Int? = 1,
    val type: String? = "",
)

data class LiveResponse(val data: LiveData) : BaseModel() {
    var isRefresh = false
}

data class LiveAdsEntity(
    val pic: String,
    val title: String,
    val url: String,
    val target: Int = 0,
)

data class LiveAdsList(
    val ads: ArrayList<LiveAdsEntity>?,
)

data class LiveAdsData(val data: LiveAdsList) : BaseModel()

data class SearchResultEntity(
    val avatar: String? = "",
    val nickname: String? = "",
    @SerializedName("show_id")
    val showId: Long? = 0L,
    @SerializedName("user_id")
    val userId: Long? = 0L,
)

data class SearchResultList(val list: ArrayList<SearchResultEntity>)
data class SearchResultData(val data: SearchResultList) : BaseModel()