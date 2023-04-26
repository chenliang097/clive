package com.rongtuoyouxuan.chatlive.biz2.model.stream.im;

public class RoomRevokeAdmin extends BaseRoomMessage {
    @Override
    public int getItemType() {
        return TYPE_REVOKE_ADMIN;
    }
}
