package com.rongtuoyouxuan.chatlive.biz2.model.stream;

import com.google.gson.annotations.SerializedName;
import com.rongtuoyouxuan.chatlive.net2.BaseModel;

public class HostManagerModel extends BaseModel {

    /**
     * data : true
     */

    @SerializedName("data")
    public boolean data = false;

    public String tag = "";
}
