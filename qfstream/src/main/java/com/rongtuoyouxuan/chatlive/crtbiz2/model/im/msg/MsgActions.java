package com.rongtuoyouxuan.chatlive.crtbiz2.model.im.msg;

/**
 * @Description : 自定义消息的消息类型
 * @Author : jianbo
 * @Date : 2022/8/9  00:00
 */
public class MsgActions {

    //  0、未知消息
    public static final String ACTION_UNKNOWN = "unknown_msg";
    //  1、文本消息
    public static final String ACTION_TEXT = "txt_msg";
    //  2、图片消息
    public static final String ACTION_IMG = "img_msg";
    //  3、图文消息（官方消息）
    public static final String ACTION_RICH = "rich_msg";
    //  4、GIF消息
    public static final String ACTION_GIF = "gif_msg";
    //  5、礼物消息
    public static final String ACTION_GIFT = "gift_msg";
    //  6、关注消息Follow 非直播间用
    public static final String ACTION_FOLLOW = "follow_msg";
    //  7、访客消息
    public static final String ACTION_VISITOR = "visit_msg";
    //  8、支付消息
    public static final String ACTION_PAY = "pay_msg";
    //  9、群成员变化消息
    public static final String ACTION_GROUP_USER = "group_user_msg";
    //  10、群信息变化
    public static final String ACTION_GROUP_INFO = "group_info_msg";
    //  11、公告消息（群公告、直播间聊天室公告）
    public static final String ACTION_ANNOUNCE = "announce_msg";
    //  12、禁言消息（群、聊天室）
    public static final String ACTION_BANNED = "banned_msg";
    //  13、用户加入直播间（含座驾)
    public static final String ACTION_LIVE_JOIN = "live_join_msg";
    //  14、麦位操作消息（邀请上麦、申请上麦、踢下麦、开关嘉宾摄像头或麦克风）
    public static final String ACTION_LIVE_OPTMIC = "live_optmic_msg";
    //  15、麦位应答消息（邀请上麦、申请上麦应答）
    public static final String ACTION_LIVE_RPMIC = "live_rpmic_msg";
    //  16、麦位更新消息（新增麦位、麦位摄像头麦克风状态变化时）
    public static final String ACTION_LIVE_MIC_MSG = "live_mic_msg";
    //  17、对主播表达好感消息（关注主播、给主播点赞、分享主播） 直播间用
    public static final String ACTION_LIKE_ANCHOR = "like_anchor_msg";
    //  18、直播间贴纸消息
    public static final String ACTION_LIVE_PASTER = "live_paster_msg";
    //  19、直播结束消息
    public static final String ACTION_LIVE_END = "live_end_msg";
    //  20、主播开播消息
    public static final String ACTION_LIVE_START = "live_start_msg";
    //  21、直播间热度变化消息(收到钻石数)
    public static final String ACTION_LIVE_HOT = "live_hot_msg";
    //  22、全站礼物广播，即大礼物全站直播间跑马灯
    public static final String ACTION_STATION_GIFT = "station_gift_msg";
    //  23、聊天室保活消息
    public static final String ACTION_LIVE_KEEP = "live_keep_msg";
    //  24、粉丝灯牌点亮消息
    public static final String ACTION_FANS_LIGHT = "fans_light_msg";
    //  25、直播间踢人消息（移出、拉黑）
    public static final String ACTION_LIVE_KICK = "live_kick_msg";
    //  26、直播间点赞数量更新消息
    public static final String ACTION_LIVE_LIKE_NUM = "like_num_msg";
    //  27、用户离开直播间(主动离开、踢人(移除)、拉黑)
    public static final String ACTION_LIVE_LEAVE_ROOM = "live_leave_msg";
    //  28、直播间封禁通知消息
    public static final String ACTION_LIVE_LOCK = "live_lock_msg";
    //  29、自己被踢出群（拉黑并踢出群）
    public static final String ACTION_GROUP_KICK = "group_kick_msg";
    //  30、房间观众接收 开始混流
    public static final String ACTION_LIVE_MIX_STREAM_START = "live_link_audience_msg";
    //  31、用户等级变更
    public static final String ACTION_USER_LEVEL = "user_level_change";
    //  34、封禁账号、封禁设备消息
    public static final String ACTION_SUSPENDED_ACCOUNT = "suspended_account";
    //  35、直播间游戏切换
    public static final String ACTION_CHAT_ROOM_GAME_SWITCH = "chatroom_game_switch";
    //  33、直播间观众通知消息
    public static final String ACTION_LIVE_AUDIENCE_NOTIFICATION = "live_audience_notification";
    //  32、直播间站内分享
    public static final String ACTION_LIVE_SHARE_MSG = "live_share_msg";

    //  44. 游戏播报
    public static final String ACTION_LIVE_GAME_MARQUEE = "live_game_marquee";
    //  46. 申请连麦人数
    public static final String ACTION_LINKMIC_APPLY_NUM = "live_link_apply_total";
    //  47. 福袋发送消息
    public static final String ACTION_LUCKY_MONEY_SEND = "lucky_money_send";
    //  48. 福袋完成消息
    public static final String ACTION_LUCKY_MONEY_FINISH = "lucky_money_finish";
    //  49. 福袋全站广播消息
    public static final String ACTION_LUCKY_MONEY_BROADCAST = "lucky_money_broadcast";
}
