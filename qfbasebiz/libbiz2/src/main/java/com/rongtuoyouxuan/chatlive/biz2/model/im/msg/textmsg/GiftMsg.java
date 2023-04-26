package com.rongtuoyouxuan.chatlive.biz2.model.im.msg.textmsg;

import com.rongtuoyouxuan.chatlive.biz2.model.im.msg.BaseMsg;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * @Description :礼物消息
 * @MessageTag( value = "RC:TxtMsg",
 * flag = 3
 * )
 * 1、需要客户端本地存储并计数的统一使用RC:TxtMsg文本消息的content进行传输；
 * @Author : jianbo
 * @Date : 2022/8/3  21:55
 */
public class GiftMsg extends BaseMsg.MsgBody {

    //群ID或直播房间ID
//    @SerializedName("chat_id")
//    public int chatId;

    //礼物ID
    @SerializedName("gift_id")
    public long giftId;

    //礼物名称
    @SerializedName("gift_name")
    public String giftName;

    //礼物图片URL
    @SerializedName("gift_thumbnail")
    public String giftImgUrl;

    //礼物大动画URL
    @SerializedName("gift_big_mp4")
    public String giftBigMp4;

    // 1:普通 2：高级
    @SerializedName("gift_level")
    public String giftLevel;

    //收到的礼物数量
    @SerializedName("gift_num")
    public int num;

    @SerializedName("gift_resources")
    public ArrayList<String> giftResources;


    @SerializedName("gift_mark")
    public int giftMark = 0;

    //礼物接收者用户信息
    @SerializedName("to")
    public ToBean to;

    public static class ToBean {

        //用户ID
        @SerializedName("user_id")
        public String userId;

        //用户昵称
        @SerializedName("nickname")
        public String nickname;

        //用户头像地址
        @SerializedName("avatar")
        public String avatar;

        //用户等级
        @SerializedName("level")
        public int level;

        //主播等级，非主播为0
        @SerializedName("anchor_level")
        public int anchorLevel;

        //性别 0-未知 1-男 2-女
        @SerializedName("gender")
        public int gender;
    }
}
