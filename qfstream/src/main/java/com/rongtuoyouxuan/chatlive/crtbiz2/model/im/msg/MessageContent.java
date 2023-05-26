package com.rongtuoyouxuan.chatlive.crtbiz2.model.im.msg;

import android.os.Build;

import com.blankj.utilcode.util.StringUtils;

import java.util.Arrays;

/**
 * @Description :消息内容常量
 * @Author : jianbo
 * @Date : 2022/8/3  22:51
 */
public enum MessageContent {

    MSG_UNKNOWN(0, MsgActions.ACTION_UNKNOWN),
    MSG_TEXT(1001, MsgActions.ACTION_TEXT),
    MSG_IMG(2, MsgActions.ACTION_IMG),
    MSG_RICH(3, MsgActions.ACTION_RICH),
    MSG_GIF(4, MsgActions.ACTION_GIF),
    MSG_GIFT(5, MsgActions.ACTION_GIFT),
    MSG_FOLLOW(6, MsgActions.ACTION_FOLLOW),
    MSG_VISITOR(7, MsgActions.ACTION_VISITOR),
    MSG_PAY(8, MsgActions.ACTION_PAY),
    MSG_GROUP_USER(9, MsgActions.ACTION_GROUP_USER),
    MSG_GROUP_INFO(10, MsgActions.ACTION_GROUP_INFO),
    MSG_ANNOUNCE(11, MsgActions.ACTION_ANNOUNCE),
    MSG_BANNED(12, MsgActions.ACTION_BANNED),
    MSG_LIVE_JOIN(13, MsgActions.ACTION_LIVE_JOIN),
    MSG_LIVE_OPTMIC(14, MsgActions.ACTION_LIVE_OPTMIC),
    MSG_LIVE_RPMIC(15, MsgActions.ACTION_LIVE_RPMIC),
    MSG_LIVE_MIC_MSG(16, MsgActions.ACTION_LIVE_MIC_MSG),
    MSG_LIKE_ANCHOR(17, MsgActions.ACTION_LIKE_ANCHOR),
    MSG_LIVE_PASTER(18, MsgActions.ACTION_LIVE_PASTER),
    MSG_LIVE_END(19, MsgActions.ACTION_LIVE_END),
    MSG_LIVE_START(20, MsgActions.ACTION_LIVE_START),
    MSG_LIVE_HOT(21, MsgActions.ACTION_LIVE_HOT),
    MSG_STATION_GIFT(22, MsgActions.ACTION_STATION_GIFT),
    MSG_LIVE_KEEP(23, MsgActions.ACTION_LIVE_KEEP),
    MSG_FANS_LIGHT(24, MsgActions.ACTION_FANS_LIGHT),
    MSG_LIVE_KICK(25, MsgActions.ACTION_LIVE_KICK),
    MSG_LIVE_LIKE_NUM(26, MsgActions.ACTION_LIVE_LIKE_NUM),
    MSG_LIVE_LEAVE_ROOM(27, MsgActions.ACTION_LIVE_LEAVE_ROOM),
    MSG_LIVE_LOCK(28, MsgActions.ACTION_LIVE_LOCK),
    MSG_GROUP_KICK(29, MsgActions.ACTION_GROUP_KICK),
    MSG_LIVE_MIX_STREAM_START(30, MsgActions.ACTION_LIVE_MIX_STREAM_START),
    MSG_USER_LEVEL(31, MsgActions.ACTION_USER_LEVEL),
    MSG_SUSPENDED_ACCOUNT(34, MsgActions.ACTION_SUSPENDED_ACCOUNT),
    ACTION_CHAT_ROOM_GAME_SWITCH(31, MsgActions.ACTION_CHAT_ROOM_GAME_SWITCH),
    ACTION_LIVE_AUDIENCE_NOTIFICATION(32, MsgActions.ACTION_LIVE_AUDIENCE_NOTIFICATION),
    ACTION_LINKMIC_APPLY_NUM(33, MsgActions.ACTION_LINKMIC_APPLY_NUM);


    public int type;

    private String action;

    MessageContent(int type, String action) {
        this.type = type;
        this.action = action;
    }

    public int type() {
        return this.type;
    }

    public String action() {
        return this.action;
    }

    // 实现字符串转枚举的静态方法
    public static MessageContent fromString(String action) {
        if (StringUtils.isEmpty(action)) {
            return MSG_UNKNOWN;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Arrays.stream(values())
                    .filter(bl -> bl.action.equalsIgnoreCase(action))
                    .findFirst()
                    .orElse(null);
        } else {
            return MessageContent.valueOf(action);
        }
    }

    /**
     * 提前判断，用于解决
     * Case中出现的Constant expression required
     *
     * @param value
     * @return
     */
    public static MessageContent getByValue(int value) {
        for (MessageContent x : values()) {
            if (x.type() == value) {
                return x;
            }
        }
        return null;
    }
}
