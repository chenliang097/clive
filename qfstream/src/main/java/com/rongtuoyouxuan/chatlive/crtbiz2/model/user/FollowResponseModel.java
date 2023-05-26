package com.rongtuoyouxuan.chatlive.crtbiz2.model.user;

import com.rongtuoyouxuan.chatlive.crtnet.BaseModel;
import com.google.gson.annotations.SerializedName;

public class FollowResponseModel extends BaseModel {

    public String tag = "";
    /**
     * data : {"intimacy":10}
     */

    @SerializedName("data")
    public DataBean data = new DataBean();

    public static class DataBean {
        /**
         * intimacy : 10
         */

        public int intimacy = 0;
    }
}
