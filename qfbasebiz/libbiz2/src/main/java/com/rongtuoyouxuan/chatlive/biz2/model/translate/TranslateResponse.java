package com.rongtuoyouxuan.chatlive.biz2.model.translate;

import com.rongtuoyouxuan.chatlive.net2.BaseModel;
import com.google.gson.annotations.SerializedName;


public class TranslateResponse extends BaseModel {
    /**
     * data : {"trans":"Hello there"}
     */

    @SerializedName("data")
    public DataBean data = new DataBean();

    public static class DataBean {
        /**
         * trans : Hello there
         */

        @SerializedName("trans")
        public String trans = "";

        public String reqId = "";
    }
}
