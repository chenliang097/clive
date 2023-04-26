package com.rongtuoyouxuan.chatlive.biz2.model.im.msg.textmsg;

import com.rongtuoyouxuan.chatlive.biz2.model.im.msg.BaseMsg;
import com.google.gson.annotations.SerializedName;

/**
 * @Description :图文消息
 * @MessageTag( value = "RC:TxtMsg",
 * flag = 3
 * )
 * 1、需要客户端本地存储并计数的统一使用RC:TxtMsg文本消息的content进行传输；
 * @Author : jianbo
 * @Date : 2022/8/3  21:55
 */
public class RichTextMsg extends BaseMsg.MsgBody {

    //图片地址，为空时不显示
    @SerializedName("image_url")
    public String imageUrl;

    //图文消息标题
    @SerializedName("title")
    public String title;

    //文本消息内容
    @SerializedName("content")
    public String content;

    //URL链接地址
    @SerializedName("link_url")
    public String linkUrl;

}
