package com.rongtuoyouxuan.chatlive.crtbiz2.model.im.msg.cmdMsg;

import com.google.gson.annotations.SerializedName;

/**
 * 
 * date:2022/8/20-16:28
 * des:
 */
public class UsersBean {
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
    public String level;

    //主播等级，非主播为0
    @SerializedName("anchor_level")
    public int anchorLevel;

    //性别 0-未知 1-男 2-女
    @SerializedName("gender")
    public int gender;
}
