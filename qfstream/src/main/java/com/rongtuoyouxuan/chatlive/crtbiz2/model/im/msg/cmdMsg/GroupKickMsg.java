package com.rongtuoyouxuan.chatlive.crtbiz2.model.im.msg.cmdMsg;

import com.rongtuoyouxuan.chatlive.crtbiz2.model.im.msg.BaseMsg;
import com.google.gson.annotations.SerializedName;

/**
 * @Description :29. 自己被踢出群（拉黑并踢出群）
 * 配合群成员变化消息（9：group_user_msg）使用，本消息采用一对一方式发送，由system发送给被踢出群的用户。
 * @MessageTag( value = "RC:CmdMsg",
 * flag = 0
 * )
 * 3、既不需要本地存储也不需要计数的统一使用RC:CmdMsg命令消息的data字段进行传输；
 * 对应融云CommandMessage
 * @Author : jianbo
 * @Date : 2022/8/3  22:14
 */
public class GroupKickMsg extends BaseMsg.MsgBody {

    //成员变化类型(3-被踢出群 5-被拉黑)
    @SerializedName("change_type")
    public int changeType;

    //群ID
    @SerializedName("group_id")
    public int groupId;
}
