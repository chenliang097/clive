package com.rongtuoyouxuan.chatlive.biz2.model.stream

import com.rongtuoyouxuan.chatlive.net2.BaseModel
import com.google.gson.annotations.SerializedName

/**
 * 
 * date:2022/8/9-16:31
 * des: 直播间点赞--贴纸
 */
data class LikeLiveReq(
    @SerializedName("room_id_str")
    val roomId: String? = "",
    @SerializedName("scene_id_str")
    val sceneIdStr: String? = "",
    @SerializedName("user_id_str")
    val userIdStr: String? = "",
    @SerializedName("anchor_id_str")
    val anchorIdStr: String? = ""

)

data class LikeLiveRes(
    @SerializedName("like_total")
    val likeTotal: Long? = 0
)

data class LikeLiveData(
    val data: LikeLiveRes
) : BaseModel()

data class LiveStickEntity(
    @SerializedName("paster_icon")
    val pasterIcon: String? = "",
    @SerializedName("paster_id")
    val pasterId: Int? = 0,
    @SerializedName("paster_url")
    val pasterUrl: String? = "",
    var isSelected: Boolean = false
)

data class LiveStickListRes(
    val list: ArrayList<LiveStickEntity>?,
    val page: Int? = 1,
    @SerializedName("page_total")
    val pageTotal: Int? = 1,
    val size: Int? = 0
)

data class LiveStickResData(val data: LiveStickListRes) : BaseModel()

data class LiveSetStickReq(
    val info: String,
    @SerializedName("live_id")
    val liveId: Long,
    @SerializedName("paster_id")
    val pasterId: Int
)

data class LiveGetStickRes(
    val info: String? = "",
    @SerializedName("live_id")
    val liveId: Long,
    @SerializedName("paster_id")
    val pasterId: Int
)

data class LiveGetStickResData(val data: LiveGetStickRes) : BaseModel()

//贴纸坐标百分比
data class LiveStickPoint(
    //operateType=1 添加，=2移除，=3移动 =4/5添加View不上传数据
    @SerializedName("operate_type")
    var operateType: Int,
    @SerializedName("paster_id")
    val stickId: Int,
    @SerializedName("paster_url")
    val imageUrl: String,
    @SerializedName("paster_width")
    val width: Float,
    @SerializedName("paster_height")
    val height: Float,
    @SerializedName("coor_x")
    val xPercent: Float,
    @SerializedName("coor_y")
    val yPercent: Float,
    //type = 1 图片贴纸，type=2 文字贴纸
    val type: Int = 1,
    @SerializedName("paster_text")
    val pasterText: String? = ""
)