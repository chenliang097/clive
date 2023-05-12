package com.rongtuoyouxuan.qfcommon.util

import com.rongtuoyouxuan.chatlive.biz2.model.stream.ProfileUserData
import com.jeremyliao.liveeventbus.LiveEventBus
import com.jeremyliao.liveeventbus.core.Observable
import com.rongtuoyouxuan.chatlive.biz2.model.user.UserCardInfoBean

/**
 * 
 * date:2022/8/20-14:11
 * des: 资料卡
 */
object UserCardHelper {
    //资料卡-@
    val atUserVM: Observable<ProfileUserData> = LiveEventBus.get("LIVE_AT_USER")

    //资料卡-管理
    val managerVM: Observable<UserCardInfoBean.ProfileUserData> = LiveEventBus.get("LIVE_USER_CARD_MANAGER")

    //资料卡-举报
    val reportVM: Observable<ProfileUserData> = LiveEventBus.get("USER_CARD_REPORT")

    //资料卡-灯牌
    val lightBoardVM: Observable<ProfileUserData> = LiveEventBus.get("LIVE_LIGHT_BOARD")

    //关注主播
    val followAnchorVM: Observable<Boolean> = LiveEventBus.get("LIVE_FOLLOW_ANCHOR")

    //关注
    val clickfollowAnchorVM: Observable<Boolean> = LiveEventBus.get("LIVE_FOLLOW_ANCHOR_CLICK")
}