package com.rongtuoyouxuan.chatlive.crtbiz2.model.im.msg;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.google.gson.annotations.SerializedName;

/**
 * @Description : 通用自定义消息结构
 * @Author : jianbo
 * @Date : 2022/8/3  19:52
 */
public class BaseMsg implements MultiItemEntity {

    //Schema
    @SerializedName("schema")
    public String schema;

    /**
     * 会话类型
     * <p>
     * conversation_type 会话类型包括：
     * 1、private 私聊
     * 2、group 群聊
     * 3、chatroom 聊天室（直播间公聊）
     * 4、up上行消息
     */
    @SerializedName("conversation_type")
    public String conversationType;

    //APP应用本地语言，传语言码：zh/en/th/vi/zh-Hant等
    @SerializedName("language")
    public String language;

    //消息类型
    @SerializedName("action")
    public String action;

    //消息体
    @SerializedName("body")
    public MsgBody body;

    //消息类型 int
    public int messageType;

    @Override
    public int getItemType() {
        return messageType;
    }

    /**
     * 消息体
     */
    public static class MsgBody {

        //发送者用户信息
        @SerializedName("from")
        public From from;

        //用户身份类型，0-游客 1-普通用户 10-主播 11-管理员 99-超管
        @SerializedName("user_type")
        public int userType;

        //是否点亮粉丝灯牌（0-默认未点亮 1-已点亮）
        @SerializedName("fans")
        public int fans;

        //粉丝群名称
        @SerializedName("fans_name")
        public String fansName = "";

        /**
         * 发送者用户信息
         */
        public static class From {

            @SerializedName("user_id")
            public String userId;

            //昵称
            @SerializedName("nickname")
            public String nickname;

            //头像
            @SerializedName("avatar")
            public String avatar;

            //用户等级
            @SerializedName("level")
            public int level;

            //主播等级
            @SerializedName("anchor_level")
            public int anchorLevel;

            //性别 0-未知 1-男 2-女
            @SerializedName("gender")
            public int gender;

            //主播认证状态 0-默认未认证 1-已认证
            @SerializedName("realcert_status")
            public int realcertStatus;

            //是否已关注  给对方发消息时不知道对方有没有关注我，故删除，通过基本用户信息接口拉取
            @Deprecated
            @SerializedName("followed")
            public String followed;
        }

    }

}
