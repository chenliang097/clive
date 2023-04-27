package com.rongtuoyouxuan.chatlive.net2;

import com.google.gson.annotations.SerializedName;

public class BaseModel {
    @SerializedName("status")
    public int errCode;
    @SerializedName("msg")
    public String errMsg;
}
