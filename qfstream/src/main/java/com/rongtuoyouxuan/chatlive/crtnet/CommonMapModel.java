package com.rongtuoyouxuan.chatlive.crtnet;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

public class CommonMapModel extends BaseModel {
    @SerializedName("data")
    public Map<String, String> data = new HashMap<>();
}
