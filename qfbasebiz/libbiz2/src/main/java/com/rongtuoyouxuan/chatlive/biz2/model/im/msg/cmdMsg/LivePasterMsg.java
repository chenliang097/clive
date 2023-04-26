package com.rongtuoyouxuan.chatlive.biz2.model.im.msg.cmdMsg;

import com.rongtuoyouxuan.chatlive.biz2.model.im.msg.BaseMsg;
import com.google.gson.annotations.SerializedName;

/**
 * @Description :直播间贴纸消息
 * @MessageTag( value = "RC:CmdMsg",
 * flag = 0
 * )
 * 3、既不需要本地存储也不需要计数的统一使用RC:CmdMsg命令消息的data字段进行传输；
 * 对应融云CommandMessage
 * @Author : jianbo
 * @Date : 2022/8/3  22:14
 */
public class LivePasterMsg extends BaseMsg.MsgBody {

    //直播间ID
    @SerializedName("room_id")
    public long roomId;

    //操作类型，1-添加贴纸 2-移除贴纸 3-移动贴纸
    @SerializedName("operate_type")
    public int operateType;

    //贴纸ID
    @SerializedName("paster_id")
    public int pasterId;

    //贴纸URL
    @SerializedName("paster_url")
    public String pasterUrl;

    //贴纸宽
    @SerializedName("paster_width")
    public float pasterWidth;

    //贴纸高
    @SerializedName("paster_height")
    public float pasterHeight;

    //贴纸X坐标（百分比）
    @SerializedName("coor_x")
    public float coorX;

    //贴纸Y坐标（百分比）
    @SerializedName("coor_y")
    public float coorY;

    //type = 1 图片贴纸，type=2 文字贴纸
    public int type = 1;

    @SerializedName("paster_text")
    public String pasterText = "";
}
