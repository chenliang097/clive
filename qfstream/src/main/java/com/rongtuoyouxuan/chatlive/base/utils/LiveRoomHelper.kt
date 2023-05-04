package com.rongtuoyouxuan.chatlive.base.utils

import com.rongtuoyouxuan.chatlive.biz2.model.stream.LiveMarqueeEntity
import com.rongtuoyouxuan.chatlive.biz2.model.stream.LiveStickPoint
import com.jeremyliao.liveeventbus.LiveEventBus
import com.jeremyliao.liveeventbus.core.Observable

/**
 *
 * date:2022/8/11-16:12
 * des: 直播间辅助类
 */
object LiveRoomHelper {

    //贡献榜/成员列表dialog监听
    val cmDismissVM: Observable<Int> = LiveEventBus.get("LIVE_DISMISS_CM")

    //成员列表更新tab数据
    val cmMemberTabVM: Observable<Int> = LiveEventBus.get("LIVE_MEMBER_TAB_CM")

    //用户VM打开资料卡
    val openUserCardVM: Observable<String> = LiveEventBus.get("LIVE_USER_CARD_DIALOG")

    //更新点赞数量
    val starVM: Observable<Long> = LiveEventBus.get("LIVE_STAR")

    //更新点赞数量--从直播扩展接口更新
    val starVM2: Observable<Long> = LiveEventBus.get("LIVE_EXTRA_STAR")

    //贴纸移动后更新位置坐标
    val stickPointVM: Observable<LiveStickPoint> = LiveEventBus.get("LIVE_STICK_POINT")

    //打开贴纸dialog
    val openStickDialogVM: Observable<Int> = LiveEventBus.get("LIVE_OPEN_STICK_DIALOG")

    //最小化
    val liveFloatingWindow: Observable<Long> = LiveEventBus.get("LIVE_OPEN_FloatingWindow")


    //跑马灯跳转
    val liveMarqueeClick: Observable<LiveMarqueeEntity> = LiveEventBus.get("LIVE_MARQUEE_CLICK")

}