package com.rongtuoyouxuan.chatlive.net2;

import com.google.gson.annotations.SerializedName;

public class CommonBooleanModel extends BaseModel {
    @SerializedName("data")
    public boolean data = false;

    public String tag = "";

    public boolean isSuccess() {
        return errCode == 0 && data;
    }
}
