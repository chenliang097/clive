package com.rongtuoyouxuan.chatlive.biz2.model.im.request

import androidx.annotation.Keep

/**
 *	@Description : 会话置顶request
 *	@Author : jianbo
 *	@Date : 2022/8/10  20:03
 */
@Keep
class ConversationToTopRequest(

    var target: String,
    var action: String,
    var type: Int,

    )