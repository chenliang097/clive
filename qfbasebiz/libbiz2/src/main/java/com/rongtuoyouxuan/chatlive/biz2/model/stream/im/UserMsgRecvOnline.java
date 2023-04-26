package com.rongtuoyouxuan.chatlive.biz2.model.stream.im;

public class UserMsgRecvOnline {
    public String online = "";
    public String uid = "";
    public String nick = "";
    public String avatar = "";

    public boolean isOnline(){
        return "1".equals(online);
    }
}
