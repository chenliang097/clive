package com.rongtuoyouxuan.chatlive.crtbiz2.model.im.msg.textmsg;

import com.rongtuoyouxuan.chatlive.crtbiz2.model.im.msg.BaseMsg;
import com.google.gson.annotations.SerializedName;

/**
 * @Description :GIF消息
 * @MessageTag( value = "RC:TxtMsg",
 * flag = 3
 * )
 * 1、需要客户端本地存储并计数的统一使用RC:TxtMsg文本消息的content进行传输；
 * @Author : jianbo
 * @Date : 2022/8/3  21:55
 */
public class GifMsg extends BaseMsg.MsgBody {

    //GIF图片ID
    @SerializedName("gif_id")
    public int gifId;

    //GIF图片地址
    @SerializedName("gif_url")
    public String gifUrl;

    //是否点亮粉丝灯牌（0-默认未点亮 1-已点亮）
//    @SerializedName("fans")
//    public int fans;

//    @SerializedName("cite_content")
//    public String citeContent;
}
