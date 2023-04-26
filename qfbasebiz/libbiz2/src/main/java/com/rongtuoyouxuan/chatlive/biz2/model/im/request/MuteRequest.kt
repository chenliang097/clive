package com.rongtuoyouxuan.chatlive.biz2.model.im.request

import androidx.annotation.Keep

/**
 *	@Description :禁言请求实体
 *	@Author : jianbo
 *	@Date : 2022/8/10  20:03
 */
@Keep
class MuteRequest constructor(
    //房间chatroom, 粉丝群group, 用户user
    var source: String,
    //来源id，房间 粉丝群 用户, 房间默认为主播id
    var sourceId: String,
    //主播ID 房间禁言时需要
    var anchorId: String,
    //屏蔽ID
    var muteIds: List<String>,
    var muteTime: Int,
) {

    //取消禁言
    constructor(
        source: String,
        sourceId: String,
        anchorId: String,
        muteIds: List<String>,
    ) : this(source, sourceId, anchorId, muteIds, 0)
}