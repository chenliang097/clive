package com.rongtuoyouxuan.chatlive.crtnet;

import com.google.gson.annotations.SerializedName;

public class BaseModel {
    @SerializedName("status")
    public int errCode;
    @SerializedName("msg")
    public String errMsg;
}
