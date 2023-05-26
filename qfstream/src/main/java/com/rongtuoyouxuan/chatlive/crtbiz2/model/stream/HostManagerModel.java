package com.rongtuoyouxuan.chatlive.crtbiz2.model.stream;

import com.google.gson.annotations.SerializedName;
import com.rongtuoyouxuan.chatlive.crtnet.BaseModel;

public class HostManagerModel extends BaseModel {

    /**
     * data : true
     */

    @SerializedName("data")
    public boolean data = false;

    public String tag = "";
}
