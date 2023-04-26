package com.rongtuoyouxuan.chatlive.biz2.model.stream.im;

import com.rongtuoyouxuan.chatlive.net2.BaseModel;
import com.google.gson.annotations.SerializedName;

public class UserMessageModel extends BaseModel {
    @SerializedName("data")
    public _Message data = new _Message();
}