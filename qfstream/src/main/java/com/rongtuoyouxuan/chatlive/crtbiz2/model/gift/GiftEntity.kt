package com.rongtuoyouxuan.chatlive.crtbiz2.model.gift

import com.google.gson.annotations.SerializedName

/**
 *
 * date:2022/8/1-11:48
 * des: 礼物
 */

//包含礼物通道字段：用户头像/用户名/礼物数
data class GiftEntity(

    val level: Int? = 0,
    val price: Float? = 0.0f,
    val thumbnail: String? = "",
    val type: Int? = 0,
    var isSelected: Boolean = false,
    var userHead: String? = "",
    var userName: String? = "",
    var giftNum: Int? = 1,
    val mark: ArrayList<Int>? = null,
    var userId: String? = "",

    @SerializedName("url_1x")
    var giftUrl1X: String? = "",
    @SerializedName("url_2x")
    var giftUrl2X: String? = "",
    @SerializedName("url_3x")
    var giftUrl3X: String? = "",
    @SerializedName("is_top")
    var isTop: Int? = 0,
    var value: Int? = 0,
    var status: Int? = 0,
    @SerializedName("resource_url")
    val resourceUrl: String? = "",
    @SerializedName("created_at")
    val createdAt: Long? = 0L,
    @SerializedName("updated_at")
    val updatedAt: Long? = 0L,
    @SerializedName("name")
    val giftName: String? = "",
    @SerializedName("id")
    val giftId: Int? = 0
)

data class GiftPanelRes(
    @SerializedName("total")
    val total: Int? = 0,
    @SerializedName("list")
    val list: MutableList<GiftEntity>? = null
)

data class GiftPanelResData(val data: GiftPanelRes) : com.rongtuoyouxuan.chatlive.crtnet.BaseModel()

data class GiftResourceListRes(val timestamp: Long? = 0, val gift: ArrayList<String>?)

data class GiftResponseData(val data: GiftResourceListRes?) : com.rongtuoyouxuan.chatlive.crtnet.BaseModel()

data class CarResourceListRes(val timestamp: Long? = 0, val car: ArrayList<String>?)

data class CarResponseData(val data: CarResourceListRes?) : com.rongtuoyouxuan.chatlive.crtnet.BaseModel()

data class GiftSendReq(
    @SerializedName("gift_id")
    val giftId: Int,
    @SerializedName("room_id")
    val roomId: String = "",
    @SerializedName("scene_id")
    val sceneId: String = "",
    @SerializedName("user_id")
    val userId: String = "",
    @SerializedName("anchor_id")
    val anchorId: String = "",
    val num: Int,
    @SerializedName("user_name")
    val userName: String = "",
    @SerializedName("avatar")
    val avatar: String = ""
)

data class GiftSendRes(
    @SerializedName("balance_amount")
    val balanceAmount: Float? = 0.0f,
    val count: Int? = 0,
    val timestamp: Long? = 0L,
    val type: Int? = 0,
    val balance: Int? = 0
)

data class GiftSendResData(val data: GiftSendRes) : com.rongtuoyouxuan.chatlive.crtnet.BaseModel()

data class GiftLargerAnimEntity(
    //礼物大动画1或者是座驾2
    val type: Int = 1,
    val userHead: String = "",
    val userName: String = "",
    val filePath: String = "",
    val carLevel: Int = 1
)

data class GiftLuckyInfoHelpEntity(
    val content: String? = "",
    val title: String? = ""
)

data class GiftLuckyInfoRes(
    val content: String? = "",
    val title: String? = "",
    val help: GiftLuckyInfoHelpEntity? = null
)

data class GiftLuckyInfoData(val data: GiftLuckyInfoRes) : com.rongtuoyouxuan.chatlive.crtnet.BaseModel()


data class GiftBagEntity(
    @SerializedName("gift_list")
    val giftList: ArrayList<GiftEntity>? = null
)

data class GiftBagData(val data: GiftBagEntity) : com.rongtuoyouxuan.chatlive.crtnet.BaseModel()