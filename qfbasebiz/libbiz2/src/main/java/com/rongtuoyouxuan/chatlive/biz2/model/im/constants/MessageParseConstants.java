package com.rongtuoyouxuan.chatlive.biz2.model.im.constants;

/**
 * @Description : 自定义消息中常量值
 * @Author : jianbo
 * @Date : 2022/8/11  16:28
 */
public class MessageParseConstants {

    /**
     * 关注类型,1-关注了你
     */
    public static final int FOLLOW_TYPE1 = 1;
    public static final int FOLLOW_TYPE2 = 2;

    /**
     * 状态 0-成功 其他失败（待定）
     */
    public static final int PAY_STATUS_SUCCESS = 0;
    public static final int PAY_STATUS_FAIL = 1;

    /**
     * 成员变化类型(1-加入群 2-被邀请进群 3-被踢出群 4-主动退群 5-被拉黑)
     */
    public static final int CHANGE_TYPE1 = 1;
    public static final int CHANGE_TYPE2 = 2;
    public static final int CHANGE_TYPE3 = 3;
    public static final int CHANGE_TYPE4 = 4;
    public static final int CHANGE_TYPE5 = 5;


    /**
     * 禁言类型，1-全员禁言 2-解除全员禁言 3-单用户禁言 4-单用户解除禁言
     */
    public static final int BANNED_TYPE1 = 1;
    public static final int BANNED_TYPE2 = 2;
    public static final int BANNED_TYPE3 = 3;
    public static final int BANNED_TYPE4 = 4;


}
