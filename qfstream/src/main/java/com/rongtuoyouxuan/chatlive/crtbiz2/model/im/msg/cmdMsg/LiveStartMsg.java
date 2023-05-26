package com.rongtuoyouxuan.chatlive.crtbiz2.model.im.msg.cmdMsg;

import com.rongtuoyouxuan.chatlive.crtbiz2.model.im.msg.BaseMsg;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @Description :主播开播消息
 * @MessageTag( value = "RC:CmdMsg",
 * flag = 0
 * )
 * 3、既不需要本地存储也不需要计数的统一使用RC:CmdMsg命令消息的data字段进行传输；
 * 对应融云CommandMessage
 * @Author : jianbo
 * @Date : 2022/8/3  22:14
 */
public class LiveStartMsg extends BaseMsg.MsgBody {

    //直播间ID
    @SerializedName("live_id")
    public long roomId;

    //直播间名称（待定）
    @SerializedName("room_name")
    public String roomName;

    //直播间封面
    @SerializedName("room_pic")
    public String roomPic;    //流ID

    //直播流地址
    @SerializedName("show_scene")
    public List<Integer> sceneList;

}
