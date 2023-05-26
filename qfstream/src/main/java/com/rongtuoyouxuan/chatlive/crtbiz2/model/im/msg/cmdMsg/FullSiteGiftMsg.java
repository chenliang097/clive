package com.rongtuoyouxuan.chatlive.crtbiz2.model.im.msg.cmdMsg;

import com.rongtuoyouxuan.chatlive.crtbiz2.model.im.msg.BaseMsg;
import com.google.gson.annotations.SerializedName;

/**
 * @Description :全站礼物广播，即大礼物全站直播间跑马灯（待定）
 * @MessageTag( value = "RC:CmdMsg",
 * flag = 0
 * )
 * 3、既不需要本地存储也不需要计数的统一使用RC:CmdMsg命令消息的data字段进行传输；
 * 对应融云CommandMessage
 * @Author : jianbo
 * @Date : 2022/8/3  22:14
 */
public class FullSiteGiftMsg extends BaseMsg.MsgBody {

    //礼物接收者用户信息
    @SerializedName("to")
    public ToBean to;

    @SerializedName("room_id")
    public long roomId;

    //礼物ID
    @SerializedName("gift_id")
    public int giftId;

    //礼物名称
    @SerializedName("gift_name")
    public String giftName;

    //礼物图片URL
    @SerializedName("gift_img_url")
    public String giftImgUrl;

    //礼物价格
    @SerializedName("gift_price")
    public int giftPrice;

    //礼物大动画URL
    @SerializedName("gift_big_mp4")
    public String giftBigMp4;

    @SerializedName("gift_thumbnail")
    public String giftThumbnail = "";

    @SerializedName("gift_level")
    public int giftLevel = 1;

    //收到的礼物数量
    @SerializedName("gift_num")
    public int num;

    public static class ToBean {

        //用户ID
        @SerializedName("user_id")
        public long userId;

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
