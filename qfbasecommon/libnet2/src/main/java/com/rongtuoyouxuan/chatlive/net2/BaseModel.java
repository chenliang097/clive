package com.rongtuoyouxuan.chatlive.net2;

import com.google.gson.annotations.SerializedName;

public class BaseModel {
    @SerializedName("code")
    public int errCode;
    @SerializedName("message")
    public String errMsg;
}
