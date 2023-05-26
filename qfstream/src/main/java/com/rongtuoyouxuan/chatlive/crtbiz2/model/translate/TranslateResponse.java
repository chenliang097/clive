package com.rongtuoyouxuan.chatlive.crtbiz2.model.translate;

import com.rongtuoyouxuan.chatlive.crtnet.BaseModel;
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
