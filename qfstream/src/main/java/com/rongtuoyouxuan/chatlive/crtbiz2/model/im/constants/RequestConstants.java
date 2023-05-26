package com.rongtuoyouxuan.chatlive.crtbiz2.model.im.constants;

/**
 * @Description : 请求参数常量
 * @Author : jianbo
 * @Date : 2022/8/11  09:45
 */
public class RequestConstants {

    /**
     * source 来源: 房间chatroom, 粉丝群group, 用户user
     */
    public static final String CHATROOM = "chatroom";
    public static final String GROUP = "group";
    public static final String USER = "user";

    //全员禁言mute
    public static final String GROUP_MUTE = "mute";
    //取消禁言unmute
    public static final String GROUP_UNMUTE = "unmute";

    //允许通知notice
    public static final String GROUP_NOTICE = "notice";
    //免打扰close_notice
    public static final String GROUP_CLOSE_NOTICE = "close_notice";

    //系统消息UserId
    public static final String SYSTEM_ID = "system";
    //官方消息UserId
    public static final String OFFICIAL_ID = "official";


    public static final String ACTION_VISITOR = "visit_msg";
    public static final String ACTION_PAY = "pay_msg";
    public static final String ACTION_GROUP_USER = "group_user_msg";
    public static final String ACTION_GROUP_INFO = "group_info_msg";
    public static final String PUSH_HOST = "host";
    public static final String TARGET_ID = "targetId";
    public static final String SOURCE = "source";


}