package com.rongtuoyouxuan.chatlive.biz2.model.stream.im;

import com.rongtuoyouxuan.chatlive.net2.BaseModel;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ShareHostModel extends BaseModel {

    @SerializedName("data")
    public List<_Message> data = new ArrayList<>();
}