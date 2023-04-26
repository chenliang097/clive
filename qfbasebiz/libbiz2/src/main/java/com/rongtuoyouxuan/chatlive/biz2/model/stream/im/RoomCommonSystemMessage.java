package com.rongtuoyouxuan.chatlive.biz2.model.stream.im;

public class RoomCommonSystemMessage extends BaseRoomMessage {

    public String title = "";
    public String title_color = "";
    public String msg = "";
    public String msg_color = "";
    public String act = "";
    public String act_val = "";
    public String bg_color = "";
    public RoomCommonSystemMessage() {
    }
    public RoomCommonSystemMessage(String title, String title_color, String msg, String msg_color, String act, String act_val, String bg_color) {
        this.title = title;
        this.title_color = title_color;
        this.msg = msg;
        this.msg_color = msg_color;
        this.act = act;
        this.act_val = act_val;
        this.bg_color = bg_color;
    }

    @Override
    public int getItemType() {
        return TYPE_COMMON_SYSTEM_NOTICE;
    }
}
