package com.rongtuoyouxuan.chatlive.crtbiz2.model.im.msg.cmdMsg;

import com.rongtuoyouxuan.chatlive.crtbiz2.model.im.msg.BaseMsg;
import com.google.gson.annotations.SerializedName;

/*
 *Create by {Mr秦} on 2022/9/22
 */
public class UserLevelMsg extends BaseMsg.MsgBody{
    //最新用户等级
    @SerializedName("user_level")
    public long user_level;

    //最新主播等级
    @SerializedName("anchor_level")
    public long anchor_level;
}