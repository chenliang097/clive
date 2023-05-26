package com.rongtuoyouxuan.chatlive.crtbiz2.model.im.msg.textmsg;

import com.rongtuoyouxuan.chatlive.crtbiz2.model.im.msg.BaseMsg;
import com.google.gson.annotations.SerializedName;

/**
 * @Description :图片消息
 * @MessageTag( value = "RC:TxtMsg",
 * flag = 3
 * )
 * 1、需要客户端本地存储并计数的统一使用RC:TxtMsg文本消息的content进行传输；
 * @Author : jianbo
 * @Date : 2022/8/3  21:55
 */
public class ImgMsg extends BaseMsg.MsgBody {

    //图片地址
    @SerializedName("image_url")
    public String imageUrl;

    //图片宽度
    @SerializedName("image_width")
    public int imageWidth;

    //图片高度
    @SerializedName("image_height")
    public int imageHeight;

//    @SerializedName("cite_content")
//    public String citeContent;
}
