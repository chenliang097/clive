package com.rongtuoyouxuan.chatlive.crtbiz2.model.im.request

import androidx.annotation.Keep

/**
 *	@Description :用户粉丝群列表
 *	@Author : jianbo
 *	@Date : 2022/8/10  20:03
 */
@Keep
class UserGroupsRequest(

    // 页
    var page: Int,
    // 每页数量
    var size: Int,
    // 群类型
    var type: Int,
    var groupIds: List<String>?,
)