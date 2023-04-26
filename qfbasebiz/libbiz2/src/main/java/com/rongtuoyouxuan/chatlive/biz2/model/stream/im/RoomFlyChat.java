package com.rongtuoyouxuan.chatlive.biz2.model.stream.im;

public class RoomFlyChat {

    public FromBean from = new FromBean();
    public String msg = "";
    public String style = "";

    public static class FromBean {

        public String uid = "";
        public String nick = "";
        public String avatar = "";
        public String role = "";
        public int level = 0;
        public int vip = 0;
        public int svip = 0;

        public String gdtype = "";

        public boolean isZhiZunGuard(){
            return "year".equalsIgnoreCase(gdtype);
        }

        public boolean isNormalGuard(){
            return "normal".equalsIgnoreCase(gdtype);
        }
    }
}
