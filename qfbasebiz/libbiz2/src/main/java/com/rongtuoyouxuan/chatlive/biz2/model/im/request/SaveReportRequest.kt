package com.rongtuoyouxuan.chatlive.biz2.model.im.request

import androidx.annotation.Keep

/**
 *	@Description :举报请求实体
 *	@Author : jianbo
 *	@Date : 2022/8/10  20:03
 */
@Keep
class SaveReportRequest constructor(
    //房间chatroom, 粉丝群group, 用户user
    var source: String,
    //来源id，房间 粉丝群 用户, 房间默认为主播id
    var sourceId: String,
    //被举报用户id
    var targetId: String,
    //举报内容
    var content: String
)