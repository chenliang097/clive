package com.rongtuoyouxuan.chatlive.crtbiz2.model.im.msg.cmdMsg;

import com.rongtuoyouxuan.chatlive.crtbiz2.model.im.msg.BaseMsg;
import com.google.gson.annotations.SerializedName;

/**
 * @Description :25.直播间踢人消息（移出、拉黑）
 * @MessageTag( value = "RC:CmdMsg",
 * flag = 0
 * )
 * 3、既不需要本地存储也不需要计数的统一使用RC:CmdMsg命令消息的data字段进行传输；
 * 对应融云CommandMessage
 * @Author : jianbo
 * @Date : 2022/8/3  22:14
 */
public class LiveKickPeopleMsg extends BaseMsg.MsgBody {

    //直播间ID
    @SerializedName("room_id")
    public long roomId;

    //踢人类型 1-移出直播间 2-拉黑并踢出直播间
    @SerializedName("operate_type")
    public int operateType;

    //操作者用户信息（被邀请进群为发起邀请者、被踢出群时为操作踢人的用户）
    @SerializedName("operate_user")
    public UsersBean operateUser;
}
