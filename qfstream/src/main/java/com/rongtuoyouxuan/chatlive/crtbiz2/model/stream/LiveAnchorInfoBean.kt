package com.rongtuoyouxuan.chatlive.crtbiz2.model.main

import com.rongtuoyouxuan.chatlive.crtbiz2.model.stream.PullStreamUrlBean
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
    @SerializedName("fire")
    val fire: Long? = 0,
    @SerializedName("anchor_id")
    val anchorId: Long? = 0,
    @SerializedName("anchor_name")
    val anchorName: String? = "",
    @SerializedName("anchor_avatar")
    val avatar: String? = "",
    @SerializedName("medium_cover_image")
    val medium_cover_image: String? = "",
    @SerializedName("room_id")
    val roomId: Long? = 0L,
    @SerializedName("room_id_str")
    val roomIdStr: String? = "",
    @SerializedName("scene_id")
    val sceneId: Long? = 0,
    @SerializedName("scene_id_str")
    val sceneIdStr: String? = "",
    @SerializedName("room_number")
    val roomNumber: String? = "",
    @SerializedName("stream_id")
    val streamId: String? = "",
    @SerializedName("room_user_total")
    val roomUserTotal: Int? = 0,
    @SerializedName("room_title")
    val roomTitle: String? = "",
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
    val rooms_info: ArrayList<LiveItemEntity>?,
    val ads: ArrayList<LiveAdsEntity>?,
    val page: Int? = 1,
    val total: Int? = 1,
    val type: String? = "",
    val base_64_scene_ids: String? = "",
    val count: Int? = 0,
)

data class LiveResponse(val data: LiveData) : com.rongtuoyouxuan.chatlive.crtnet.BaseModel() {
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

data class LiveAdsData(val data: LiveAdsList) : com.rongtuoyouxuan.chatlive.crtnet.BaseModel()

data class SearchResultEntity(
    val avatar: String? = "",
    val nickname: String? = "",
    @SerializedName("show_id")
    val showId: Long? = 0L,
    @SerializedName("user_id")
    val userId: Long? = 0L,
)

data class SearchResultList(val list: ArrayList<SearchResultEntity>)
data class SearchResultData(val data: SearchResultList) : com.rongtuoyouxuan.chatlive.crtnet.BaseModel()