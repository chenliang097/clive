package com.rongtuoyouxuan.chatlive.biz2.model.im.msg.cmdMsg;

import com.rongtuoyouxuan.chatlive.biz2.model.im.msg.BaseMsg;
import com.google.gson.annotations.SerializedName;

/**
 * @Description :39. 直播间观众通知消息
 * @MessageTag( value = "RC:CmdMsg",
 * flag = 0
 * )
 * 3、既不需要本地存储也不需要计数的统一使用RC:CmdMsg命令消息的data字段进行传输；
 * 对应融云CommandMessage
 */
public class LiveAudienceNotificationMsg extends BaseMsg.MsgBody {

    //直播间ID
    @SerializedName("room_id")
    public long roomId;

    //描述
    @SerializedName("notification_desc")
    public String lockReasonDesc;

    //类型
    @SerializedName("type")
    public int type;

    //本次直播时长（单位s）
    @SerializedName("live_duration")
    public long liveDuration;

    @SerializedName("pic")
    public String pic;
}
