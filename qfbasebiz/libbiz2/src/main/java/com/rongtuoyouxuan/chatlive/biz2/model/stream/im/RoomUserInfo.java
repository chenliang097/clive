package com.rongtuoyouxuan.chatlive.biz2.model.stream.im;

import com.rongtuoyouxuan.chatlive.net2.BaseModel;

public class RoomUserInfo extends BaseModel {

    public DataBean data = new DataBean();

    public static class DataBean {

        public RoleBean role = new RoleBean();
        public int is_follow = 0;

        public static class RoleBean {

            public String lv = "10";
            public String role = "";
            public String name = "";
            public String power = "";
        }
    }
}
