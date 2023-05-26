package com.rongtuoyouxuan.chatlive.crtbiz2.model.im.msg.ntfmsg;

import com.rongtuoyouxuan.chatlive.crtbiz2.model.im.msg.BaseMsg;
import com.rongtuoyouxuan.chatlive.crtbiz2.model.im.msg.cmdMsg.UsersBean;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @Description :群成员变化消息
 * @MessageTag( value = "RC:GrpNtf",
 * flag = 1
 * )
 * 2、需要本地存储但不计数的统一使用RC:GrpNtf群组通知消息的data字段进行传输；
 * 对应融云GroupNotificationMessage
 * @Author : jianbo
 * @Date : 2022/8/3  22:12
 */
public class GroupUserMsg extends BaseMsg.MsgBody {

    //群成员人数
    @SerializedName("user_total")
    public int userTotal;

    //成员变化类型(1-加入群 2-被邀请进群 3-被踢出群 4-主动退群 5-被拉黑)
    @SerializedName("change_type")
    public int changeType;

    //被操作者用户信息数组（主动加入群和主动退群时为空）
    @SerializedName("users")
    public List<UsersBean> users;
}
