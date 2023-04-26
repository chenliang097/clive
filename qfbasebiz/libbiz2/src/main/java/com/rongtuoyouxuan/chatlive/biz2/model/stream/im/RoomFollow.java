package com.rongtuoyouxuan.chatlive.biz2.model.stream.im;

import com.rongtuoyouxuan.chatlive.biz2.model.im.msg.BaseMsg;
import com.rongtuoyouxuan.chatlive.biz2.model.im.msg.MessageContent;

public class RoomFollow extends BaseMsg {

    @Override
    public int getItemType() {
        return MessageContent.MSG_FOLLOW.type;
    }
}
