package com.rongtuoyouxuan.chatlive.biz2.model.im.msg.cmdMsg;

import com.rongtuoyouxuan.chatlive.biz2.model.im.msg.BaseMsg;
import com.google.gson.annotations.SerializedName;

/**
 * @Description :聊天室保活消息
 * @MessageTag( value = "RC:CmdMsg",
 * flag = 0
 * )
 * 3、既不需要本地存储也不需要计数的统一使用RC:CmdMsg命令消息的data字段进行传输；
 * 对应融云CommandMessage
 * @Author : jianbo
 * @Date : 2022/8/3  22:14
 */
public class LiveKeepAliveMsg extends BaseMsg.MsgBody {

    //直播间ID
    @SerializedName("room_id")
    public long roomId;

}
