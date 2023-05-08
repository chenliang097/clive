package com.rongtuoyouxuan.chatlive.biz2.model.user;

import com.google.gson.annotations.SerializedName;
import com.rongtuoyouxuan.chatlive.net2.BaseModel;

public class ReportBean extends BaseModel {

    @SerializedName("data")
    public DataBean data = new DataBean();

    public static class DataBean {
        public int report_type = 0;
        public String report_content = "";
    }
}
