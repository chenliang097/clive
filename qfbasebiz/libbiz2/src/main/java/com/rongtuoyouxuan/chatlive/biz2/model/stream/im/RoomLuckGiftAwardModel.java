package com.rongtuoyouxuan.chatlive.biz2.model.stream.im;

public class RoomLuckGiftAwardModel extends BaseBroadcastGiftModel {

    public FromBean from = new FromBean();
    public ToBean to = new ToBean();
    public String giftid = "";
    public int ratio = 0;

    @Override
    public BroadcastType getType() {
        return BroadcastType.LUCKGIFTAWARD;
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
