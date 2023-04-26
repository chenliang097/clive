package com.rongtuoyouxuan.chatlive.biz2.model.stream.im;

import com.google.gson.annotations.SerializedName;

public class UserLevelUp {
    @SerializedName("old")
    public int oldLevel = 0;
    @SerializedName("new")
    public int newLevel = 0;
}
