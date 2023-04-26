package com.rongtuoyouxuan.chatlive.biz2.model.stream.im;

public class BlastBroadcastGiftModel extends BaseBroadcastGiftModel {

    public String uid;
    public String nick;
    public String gift_id;
    public int prcie;
    public int num;
    public String icon;

    @Override
    public BroadcastType getType() {
        return BroadcastType.BLASTGIFT;
    }
}
