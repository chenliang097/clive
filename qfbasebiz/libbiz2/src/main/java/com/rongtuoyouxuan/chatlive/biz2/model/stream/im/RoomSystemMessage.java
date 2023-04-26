package com.rongtuoyouxuan.chatlive.biz2.model.stream.im;

import com.rongtuoyouxuan.chatlive.biz2.model.im.msg.BaseMsg;
import com.rongtuoyouxuan.chatlive.biz2.model.im.msg.MessageContent;

public class RoomSystemMessage extends BaseMsg {


    public String title = "";
    public String title_color = "";
    public String msg = "";
    public String msg_color = "";
    public String act = "";
    public String act_val = "";
    public RoomSystemMessage() {
    }
    public RoomSystemMessage(String title, String title_color, String msg, String msg_color, String act, String act_val) {
        this.title = title;
        this.title_color = title_color;
        this.msg = msg;
        this.msg_color = msg_color;
        this.act = act;
        this.act_val = act_val;
    }

    @Override
    public int getItemType() {
        return MessageContent.MSG_TEXT.type();
    }
}
