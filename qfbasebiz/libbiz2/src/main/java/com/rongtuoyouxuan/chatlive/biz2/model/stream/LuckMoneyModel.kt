package com.rongtuoyouxuan.chatlive.biz2.model.stream

import com.rongtuoyouxuan.chatlive.biz2.model.im.msg.BaseMsg
import com.rongtuoyouxuan.chatlive.net2.BaseModel
import com.contrarywind.interfaces.IPickerViewData
import com.google.gson.annotations.SerializedName

/**
 * author:jianbo
 * date:2022/8/11-10:22
 * des: 福袋接口请求
 */

//福袋配置
data class LuckyMoneyConfigModel(
    @SerializedName("avg_amount")
    val avgAmountList: ArrayList<Int>?,
    @SerializedName("countdown")
    val countdownList: ArrayList<Int>?,
    @SerializedName("number")
    val numberList: ArrayList<Int>?,
)

//福袋配置 Response
data class LuckyMoneyConfigData(val data: LuckyMoneyConfigModel) : BaseModel()

data class SendLuckyMoneyModel(
    @SerializedName("balance")
    val balance: Int,
)

//发福袋 Response
data class SendLuckyMoneyResponse(val data: SendLuckyMoneyModel) : BaseModel()

class LiveLuckyMoneyInfoMsg : BaseMsg.MsgBody() {
    @SerializedName("avg_amount")
    var avgAmount: Int = 0

    @SerializedName("countdown")
    var countdown: Int = 0

    @SerializedName("current_countdown")
    var currentCountdown: Int = 0

    @SerializedName("grab_limit")
    var grabLimit: Int = 0

    @SerializedName("id")
    var id: Long = 0

    @SerializedName("number")
    var number: Int = 0

    @SerializedName("send_at")
    var sendAt: Long = 0

    @SerializedName("sender_id")
    var senderId: Long = 0

    @SerializedName("sender_nickname")
    var senderNickname: String = ""

    @SerializedName("sender_avatar")
    var senderPortraitUrl: String = ""

    @SerializedName("sort_number")
    var sortNumber: Int = 0

    @SerializedName("total_amount")
    var totalAmount: Int = 0

    @SerializedName("type")
    var type: Int = 0
}
//{
//    constructor() : this(0, 0, 0, 0, 0, 0, 0,
//    0, "", "", 0, 0, 0)
//}

data class LiveLuckyMoneyModel(
    @SerializedName("list")
    val liveLuckyMoneyInfoMsgList: ArrayList<LiveLuckyMoneyInfoMsg>?,
    @SerializedName("timestamp")
    val timestamp: Long,
)

//直播间内红包信息Response
data class LiveLuckyMoneyResponse(val data: LiveLuckyMoneyModel) : BaseModel()

data class GrabLuckyMoneyModel(
    @SerializedName("amount")
    val amount: Long,
    //福袋id
    var id: Long,
    //福袋信息
    var luckyMoneyInfo: LiveLuckyMoneyInfoMsg,
)

//抢福袋 Response
data class GrabLuckyMoneyResponse(val data: GrabLuckyMoneyModel) : BaseModel()


data class CheckLuckyLimitModel(
    @SerializedName("unmatched")
    val liveLuckyMoneyInfoList: ArrayList<Int>?,
    var luckyMoneyInfo: LiveLuckyMoneyInfoMsg,
)

//验证抢福袋约束条件 Response
data class CheckLuckyLimitResponse(val data: CheckLuckyLimitModel) : BaseModel()

data class LuckyMoneyWinnerInfo(
    @SerializedName("amount")
    var amount: Int,
    @SerializedName("winner_nickname")
    var winnerNickname: String,
)

data class LuckyMoneyWinnerModel(
    @SerializedName("list")
    val luckyMoneyWinnerInfoList: ArrayList<LuckyMoneyWinnerInfo>?,
)

//获奖名单 Response
data class LuckyMoneyWinnerResponse(val data: LuckyMoneyWinnerModel) : BaseModel()

//发福袋Request
data class SendLuckyMoneyRequest(
    @SerializedName("avg_amount")
    var avgAmount: Int,
    @SerializedName("countdown")
    var countdown: Int,
    @SerializedName("grab_limit")
    var grabLimit: Int,
    @SerializedName("live_id")
    var liveId: Long,
    @SerializedName("number")
    var number: Int,
    @SerializedName("total_amount")
    var totalAmount: Int,
    @SerializedName("type")
    var type: Int,
) {
    constructor() : this(0, 0, 0, 0, 0, 0, 0)

    constructor(
        avgAmount: Int,
        countdown: Int,
        number: Int,
        totalAmount: Int,
        type: Int,
    ) : this(avgAmount, countdown, 0, 0, number, totalAmount, type)
}

//抢福袋Request
data class GrabLuckyMoneyRequest(
    @SerializedName("id")
    val id: Long,
)

data class GrabLimitModel(
    val grabLimitStr: String,
    val grabLimitPara: Int,
    var isSelected: Boolean,
) : IPickerViewData {
    override fun getPickerViewText(): String = grabLimitStr
}

data class SuperLuckyMoneyMarqueeEntity(
    val roomId: Long? = 0,
)
