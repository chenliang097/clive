package com.rongtuoyouxuan.chatlive.crtbiz2.model.stream.im;

import com.rongtuoyouxuan.chatlive.crtbiz2.model.im.BaseRoomMessage;

public class RoomMessage extends BaseRoomMessage {

    public String msg = "";

    @Override
    public int getItemType() {
        return TYPE_MESSAGE;
    }
}