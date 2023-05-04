package com.rongtuoyouxuan.chatlive.biz2.model.im.request

import androidx.annotation.Keep

/**
 *	@Description :拉黑请求实体
 *	@Author : jianbo
 *	@Date : 2022/8/10  20:03
 */
@Keep
class BlacklistRequest constructor(
    var user_id: String,
    var bid: String,
    var room_id: String?,
    var u_nick_name: String?,
    var b_nick_name: String?
)