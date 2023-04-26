package com.rongtuoyouxuan.chatlive.biz2.model.im.request

import androidx.annotation.Keep

/**
 *	@Description :拉黑请求实体
 *	@Author : jianbo
 *	@Date : 2022/8/10  20:03
 */
@Keep
class BlacklistRequest constructor(
    //房间chatroom, 粉丝群group, 用户user
    var source: String,
    //来源id，房间 粉丝群 用户, 房间默认为主播id
    var sourceId: String,
    //主播ID, 房间拉黑时需要主播id
    var anchorId: String?,
    //屏蔽ID
    var blockIds: List<String>
) {

    constructor(
        source: String,
        sourceId: String,
        blockIds: List<String>
    ) : this(source, sourceId, null, blockIds)

}