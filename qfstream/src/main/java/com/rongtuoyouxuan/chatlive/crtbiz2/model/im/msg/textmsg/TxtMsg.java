package com.rongtuoyouxuan.chatlive.crtbiz2.model.im.msg.textmsg;

import com.rongtuoyouxuan.chatlive.crtbiz2.model.im.msg.BaseMsg;
import com.google.gson.annotations.SerializedName;

/**
 * @Description :文本消息
 * @MessageTag( value = "RC:TxtMsg",
 * flag = 3
 * )
 * 1、需要客户端本地存储并计数的统一使用RC:TxtMsg文本消息的content进行传输；
 * @Author : jianbo
 * @Date : 2022/8/3  20:19
 */
public class TxtMsg extends BaseMsg.MsgBody {

    //文本消息内容
    @SerializedName("content")
    public String content;

    public int type;//1：直播公约

    public boolean followStatus;//是否关注

    public int res;
//    //是否点亮粉丝灯牌（0-默认未点亮 1-已点亮）
//    @SerializedName("fans")
//    public int fans;

    @SerializedName("cite_content")
    public String citeContent;

    /**
     * eg:构建对象实例
     *
     BaseMsg.MsgBody.From fromBean = new BaseMsg.MsgBody.From();
     fromBean.userId = 1;
     fromBean.nickname = "";
     fromBean.avatar = "";
     fromBean.level = 1;
     fromBean.anchorLevel = 1;
     fromBean.gender = 1;

     TxtMsg textContent = new TxtMsg();
     textContent.content = "这是一条文本消息";
     textContent.from = fromBean;

     BaseMsg baseMsg = new BaseMsg();
     baseMsg.schema = "";
     baseMsg.conversationType = "";
     baseMsg.action = "";
     baseMsg.body = textContent;
     */

}
