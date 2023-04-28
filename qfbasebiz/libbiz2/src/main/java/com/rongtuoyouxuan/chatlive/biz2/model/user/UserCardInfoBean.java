package com.rongtuoyouxuan.chatlive.biz2.model.user;

import com.google.gson.annotations.SerializedName;
import com.rongtuoyouxuan.chatlive.net2.BaseModel;

public class UserCardInfoBean extends BaseModel {

    @SerializedName("data")
    public DataBean data = new DataBean();

    public static class DataBean {
        public int fans_count = 0;
        public int follow_count = 0;
        public boolean is_follow = false;
        public String nick_name = "";
        public String avatar = "";
        public String sex = "";
        public String location = "";
        public boolean is_room_admin = false;
        public boolean is_verify = false;
        public boolean is_super_admin = false;
        public String follow_id = "";
        public boolean is_forbid_speak = false;
    }
}
