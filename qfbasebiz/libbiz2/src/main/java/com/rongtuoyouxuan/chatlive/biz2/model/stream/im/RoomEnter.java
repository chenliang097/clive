package com.rongtuoyouxuan.chatlive.biz2.model.stream.im;

public class RoomEnter extends BaseRoomMessage {

    public long score;

    @Override
    public int getItemType() {
        return TYPE_ENTER;
    }
}
