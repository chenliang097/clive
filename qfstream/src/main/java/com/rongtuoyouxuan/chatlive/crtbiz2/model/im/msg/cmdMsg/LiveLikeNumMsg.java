package com.rongtuoyouxuan.chatlive.crtbiz2.model.im.msg.cmdMsg;

import com.rongtuoyouxuan.chatlive.crtbiz2.model.im.msg.BaseMsg;
import com.google.gson.annotations.SerializedName;

/**
 * 
 * date:2022/8/16-15:08
 * des: 26. 直播间点赞数量更新消息
 */
public class LiveLikeNumMsg extends BaseMsg.MsgBody {

    @SerializedName("room_id")
    public long roomId;

    @SerializedName("likes_num")
    public long likesNum;
}
