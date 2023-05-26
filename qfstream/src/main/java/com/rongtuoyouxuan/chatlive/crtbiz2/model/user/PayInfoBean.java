package com.rongtuoyouxuan.chatlive.crtbiz2.model.user;

import com.google.gson.annotations.SerializedName;
import com.rongtuoyouxuan.chatlive.crtnet.BaseModel;

public class PayInfoBean extends BaseModel {

    @SerializedName("data")
    public DataBean data = new DataBean();

    public static class DataBean {
        public int user_id = 0;
        public String out_trade_no = "";
        public int amount = 0;
        public PayInfoItemBean pay_info = new PayInfoItemBean();
    }

    public static class PayInfoItemBean {
        public int machid = 0;
        public String nonce = "";
        public String prepay_id = "";
        public String sign = "";
        public String timestamp = "";
        public String order_str = "";
    }
}
