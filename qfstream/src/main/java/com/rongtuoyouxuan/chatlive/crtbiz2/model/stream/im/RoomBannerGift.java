package com.rongtuoyouxuan.chatlive.crtbiz2.model.stream.im;

public class RoomBannerGift extends BaseBroadcastGiftModel {


    public FromBean from = new FromBean();
    public ToBean to = new ToBean();
    public String giftid = "";
    public String giftUrl = "";
    public int count = 0;
    public int combo = 0;
    public long roomId = 0;
    public int type = 0;//类型0:世界礼物 1:幸运礼物
    @Override
    public BroadcastType getType() {
        return BroadcastType.COMMONGIFT;
    }

    public static class FromBean {
        /**
         * uid : 32423
         * nick : adfa
         * avatar : xxxx
         * role :
         * level : 2
         * vip : 0
         * svip : 0
         */

        public String uid = "";
        public String nick = "";
        public String avatar = "";
        public String role = "";
        public int level = 0;
        public int vip = 0;
        public int svip = 0;
    }

    public static class ToBean {
        /**
         * uid : 1232
         * nick : asdfd
         */

        public int uid = 0;
        public String nick = "";
    }
}
