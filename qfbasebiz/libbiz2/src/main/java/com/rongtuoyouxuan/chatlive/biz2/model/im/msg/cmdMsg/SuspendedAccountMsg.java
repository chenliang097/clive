package com.rongtuoyouxuan.chatlive.biz2.model.im.msg.cmdMsg;

import com.rongtuoyouxuan.chatlive.biz2.model.im.msg.BaseMsg;
import com.google.gson.annotations.SerializedName;

/**
 * @Description : 34. 封禁账号、封禁设备消息
 * @Author : jianbo
 * @Date : 2022/11/14  19:27
 */
public class SuspendedAccountMsg extends BaseMsg.MsgBody {

    //封禁类型 1-封账号 2-封播
    @SerializedName("suspended_type")
    public int suspendedType;

    //封禁原因（预留）
    @SerializedName("suspended_reason")
    public String suspendedReason;

    //封禁时长，单位秒s
    @SerializedName("suspended_duration")
    public int suspendedDuration;    //流ID

}
