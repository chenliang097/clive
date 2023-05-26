package com.rongtuoyouxuan.chatlive.crtbiz2.model.im.request

import androidx.annotation.Keep

/**
 *	@Description :群相关请求实体
 *	@Author : jianbo
 *	@Date : 2022/8/10  20:03
 */
@Keep
class GroupRequest constructor(
    var id: String,             //群id
    var groupId: String,        //群id
    var name: String?,           //粉丝群名称
    var logo: String?,           //粉丝群头像
    var action: String?,         //操作 禁言mute 取消禁言unmute
    var userIds: List<String>?,  //用户ID
) {

    //退群
    constructor(groupId: String) : this(groupId, groupId, null, null, null, null)

    //1、群全体禁言/取消禁言   { "id": "1", "action": "mute" }
    //2、群消息开启、关闭免打扰 { "group_id": "1", "action": "notice" }
    //3、保存粉丝群信息、群名称 { "id": "1", "name": "group" }
    //4、保存粉丝群信息、群头像 { "id": "1", "logo": "http://xxxxx.jpg" }
    constructor(id: String, action: String?, name: String?, logo: String?) : this(id,
        id,
        name,
        logo,
        action,
        null)

    //移出群成员
    constructor(groupId: String, userIds: List<String>) : this(
        groupId,
        groupId,
        null,
        null,
        null,
        userIds
    )


}