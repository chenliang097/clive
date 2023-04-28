package com.rongtuoyouxuan.chatlive.biz2.model.stream.im;

import com.rongtuoyouxuan.chatlive.biz2.model.im.BaseRoomMessage;

public class RoomMessage extends BaseRoomMessage {

    public String msg = "";

    @Override
    public int getItemType() {
        return TYPE_MESSAGE;
    }
}
