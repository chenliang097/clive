package com.rongtuoyouxuan.chatlive.biz2.model.stream.im;

public class RoomMessage extends BaseRoomMessage {

    public String msg = "";

    @Override
    public int getItemType() {
        return TYPE_MESSAGE;
    }
}
