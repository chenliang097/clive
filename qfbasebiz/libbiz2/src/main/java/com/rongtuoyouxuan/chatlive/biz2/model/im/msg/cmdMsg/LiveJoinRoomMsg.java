package com.rongtuoyouxuan.chatlive.biz2.model.im.msg.cmdMsg;

import com.rongtuoyouxuan.chatlive.biz2.model.im.msg.BaseMsg;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * @Description : 用户加入直播间（含座驾）
 * @MessageTag( value = "RC:CmdMsg",
 * flag = 0
 * )
 * 3、既不需要本地存储也不需要计数的统一使用RC:CmdMsg命令消息的data字段进行传输；
 * 对应融云CommandMessage
 * @Author : jianbo
 * @Date : 2022/8/3  22:14
 */
public class LiveJoinRoomMsg extends BaseMsg.MsgBody {

    //用户身份类型，0-游客 1-普通用户 10-主播 11-管理员 99-超管
//    @SerializedName("user_type")
//    public int userType;

    //座驾ID，没有时为0
    @SerializedName("car_id")
    public int carId;

    //座驾名称
    @SerializedName("car_name")
    public String carName;

    //座驾缩略图
    @SerializedName("car_thumbnail")
    public String carThumbnail;

    public long score;

    public long total;

    @SerializedName("car_level")
    public int carLevel;

    @SerializedName("car_resources")
    public ArrayList<String> carResources;
}
