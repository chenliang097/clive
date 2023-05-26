package com.rongtuoyouxuan.chatlive.crtbiz2.model.im.msg.textmsg;

import com.rongtuoyouxuan.chatlive.crtbiz2.model.im.msg.BaseMsg;
import com.google.gson.annotations.SerializedName;

/**
 * @Description :支付消息
 * @MessageTag( value = "RC:TxtMsg",
 * flag = 3
 * )
 * 1、需要客户端本地存储并计数的统一使用RC:TxtMsg文本消息的content进行传输；
 * @Author : jianbo
 * @Date : 2022/8/3  20:19
 */
public class PayMsg extends BaseMsg.MsgBody {

    //购买的bo币数量（待定）
    @SerializedName("coin")
    public int coin;

    //状态 0-成功 其他失败（待定）
    @SerializedName("pay_status")
    public int payStatus;
}
