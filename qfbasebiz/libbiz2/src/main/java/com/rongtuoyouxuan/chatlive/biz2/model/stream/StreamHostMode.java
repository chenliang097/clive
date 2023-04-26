package com.rongtuoyouxuan.chatlive.biz2.model.stream;

import com.google.gson.annotations.SerializedName;
import com.rongtuoyouxuan.chatlive.net2.BaseModel;

public class StreamHostMode extends BaseModel {

    @SerializedName("data")
    public AnchorInfo data = new AnchorInfo();

}
