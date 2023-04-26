package com.rongtuoyouxuan.chatlive.biz2.model.im.msg.ntfmsg;

import com.rongtuoyouxuan.chatlive.biz2.model.im.msg.BaseMsg;
import com.rongtuoyouxuan.chatlive.biz2.model.im.msg.cmdMsg.UsersBean;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @Description :禁言消息（群、聊天室）
 * @MessageTag( value = "RC:GrpNtf",
 * flag = 1
 * )
 * 2、需要本地存储但不计数的统一使用RC:GrpNtf群组通知消息的data字段进行传输；
 * 对应融云GroupNotificationMessage
 * @Author : jianbo
 * @Date : 2022/8/3  22:12
 */
public class BannedMsg extends BaseMsg.MsgBody {

    //类型，1-全员禁言 2-解除全员禁言 3-单用户禁言 4-单用户解除禁言
    @SerializedName("banned_type")
    public int bannedType;

    //被禁言or被解除禁言的用户信息，banned_type为3和4时有效
    @SerializedName("banned_users")
    public List<UsersBean> bannedUsers;

}
