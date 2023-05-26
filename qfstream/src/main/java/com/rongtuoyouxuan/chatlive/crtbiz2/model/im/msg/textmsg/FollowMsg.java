package com.rongtuoyouxuan.chatlive.crtbiz2.model.im.msg.textmsg;

import com.rongtuoyouxuan.chatlive.crtbiz2.model.im.msg.BaseMsg;
import com.google.gson.annotations.SerializedName;

/**
 * @Description :关注消息Follow
 * @MessageTag( value = "RC:TxtMsg",
 * flag = 3
 * )
 * 1、需要客户端本地存储并计数的统一使用RC:TxtMsg文本消息的content进行传输；
 * @Author : jianbo
 * @Date : 2022/8/3  21:55
 */
public class FollowMsg extends BaseMsg.MsgBody {

    //关注类型,1-关注了你
    @SerializedName("follow_type")
    public int followType;

}
