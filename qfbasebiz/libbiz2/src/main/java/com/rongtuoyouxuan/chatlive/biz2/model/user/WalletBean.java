package com.rongtuoyouxuan.chatlive.biz2.model.user;

import com.google.gson.annotations.SerializedName;
import com.rongtuoyouxuan.chatlive.net2.BaseModel;

public class WalletBean extends BaseModel {

    @SerializedName("data")
    public DataBean data = new DataBean();

    public static class DataBean {
        public String id = "";
        public String user_id = "";
        public int balance = 0;
        public int income = 0;
        public int income_cny = 0;
    }
}
