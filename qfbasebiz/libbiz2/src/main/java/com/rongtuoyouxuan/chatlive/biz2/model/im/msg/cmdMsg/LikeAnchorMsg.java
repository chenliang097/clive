package com.rongtuoyouxuan.chatlive.biz2.model.im.msg.cmdMsg;

import com.rongtuoyouxuan.chatlive.biz2.model.im.msg.BaseMsg;
import com.google.gson.annotations.SerializedName;

/**
 * @Description : 对主播表达好感消息（关注主播、给主播点赞、分享主播）
 * @MessageTag( value = "RC:CmdMsg",
 * flag = 0
 * )
 * 3、既不需要本地存储也不需要计数的统一使用RC:CmdMsg命令消息的data字段进行传输；
 * 对应融云CommandMessage
 * @Author : jianbo
 * @Date : 2022/8/3  22:14
 */
public class LikeAnchorMsg extends BaseMsg.MsgBody {

    //直播间ID
    @SerializedName("room_id")
    public long roomId;

    //好感类型，1-follow关注主播 2-点赞主播 3-分享主播
    @SerializedName("like_type")
    public int likeType;

    //直播间最新总点赞数（亦可用于关注数、分享数）
    @SerializedName("likes_num")
    public int likesNum;


}
