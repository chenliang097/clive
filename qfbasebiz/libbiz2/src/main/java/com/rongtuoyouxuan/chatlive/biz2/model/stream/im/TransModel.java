package com.rongtuoyouxuan.chatlive.biz2.model.stream.im;

import com.rongtuoyouxuan.chatlive.net2.BaseModel;
import com.google.gson.annotations.SerializedName;

public class TransModel  extends BaseModel {
    @SerializedName("data")
    public Data data = new Data();

    public static class Data {
        @SerializedName("id")
        public String id;

        @SerializedName("trans")
        public String trans;
    }
}
