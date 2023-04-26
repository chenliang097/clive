package com.rongtuoyouxuan.libgift.viewmodel

import androidx.lifecycle.MutableLiveData
import com.rongtuoyouxuan.chatlive.biz2.model.gift.GiftEntity
import com.jeremyliao.liveeventbus.LiveEventBus
import com.jeremyliao.liveeventbus.core.Observable

/**
 *
 * date:2022/8/1-17:10
 * des: 礼物面板共享数据
 */
object GiftHelper {

    //数量
    val giftNumList = arrayListOf(999, 599, 199, 99, 9, 1)

    //选择的数量
    val giftNum = MutableLiveData<Int>()

    //列表中选中的礼物
    val giftSelected = MutableLiveData<GiftEntity?>()

    //余额
    val giftBalance = MutableLiveData<Long>()

    //余额
    val giftJindouAmount = MutableLiveData<Long>()

    //数量弹框-监听箭头上下
    val isShowDialog = MutableLiveData<Boolean>()

    //送礼后
    val sendGiftVM: Observable<GiftEntity> = LiveEventBus.get("GIFT_SEND")

    //礼物特效开关
    val giftConfigAnim: Observable<Int> = LiveEventBus.get("GIFT_CONFIG_ANIM")

    //座驾特效开关
    val carConfigAnim: Observable<Int> = LiveEventBus.get("CAR_CONFIG_ANIM")

    var isChatPlayGift = true

    //点击礼物面板充值
    val clickGiftRecharge: Observable<Int> = LiveEventBus.get("KEY_GIFT_RECHARGE")
    var isRechargeOpen = true

    const val luckyNum = 10
    val giftLuckyVM: Observable<GiftEntity> = LiveEventBus.get("GIFT_LUCKY_IM")
    const val luckyMarqueeNum = 5000

    var title = ""
    var content = ""
    var helpTitle = ""
    var helpContent = ""
}