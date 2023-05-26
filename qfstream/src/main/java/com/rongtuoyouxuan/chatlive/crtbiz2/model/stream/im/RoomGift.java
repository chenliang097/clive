package com.rongtuoyouxuan.chatlive.crtbiz2.model.stream.im;

import com.rongtuoyouxuan.chatlive.crtbiz2.model.im.BaseRoomMessage;

public class RoomGift extends BaseRoomMessage {

    public String giftid = "";
    public int count = 0;
    public int combo = 0;
    public String income = "";
    public String group_id = "";
    public double addition = 0;
    public String addition_source = "";
    public int luck_gift_ratio = 0;
    public Reward reward = new Reward();

    @Override
    public int getItemType() {
        return TYPE_GIFT;
    }

    public static class Reward {
        public String giftid = "";
        public int count = 0;
        public String type = "";
    }
}
