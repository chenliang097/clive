package com.rongtuoyouxuan.chatlive.crtbiz2.model.im.msg.ntfmsg;

import com.rongtuoyouxuan.chatlive.crtbiz2.model.im.msg.BaseMsg;
import com.google.gson.annotations.SerializedName;

/**
 * @Description :群信息变化
 * @MessageTag( value = "RC:GrpNtf",
 * flag = 1
 * )
 * 2、需要本地存储但不计数的统一使用RC:GrpNtf群组通知消息的data字段进行传输；
 * 对应融云GroupNotificationMessage
 * @Author : jianbo
 * @Date : 2022/8/3  22:12
 */
public class GroupInfoMsg extends BaseMsg.MsgBody {

    //群名称（目前只有群名称变化通知）
    @SerializedName("group_name")
    public String groupName;

}
