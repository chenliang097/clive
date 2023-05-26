package com.rongtuoyouxuan.chatlive.crtbiz2.model.im.request

import androidx.annotation.Keep

/**
 *	@Description :聊天室相关请求实体
 *	@Author : jianbo
 *	@Date : 2022/8/10  20:03
 */
@Keep
class ChatRoomRequest constructor(
    var chatroomId: String,       //直播间id
    var anchorId: String,        //主播id
    var userIds: List<String>?,  //用户ID
) {


}