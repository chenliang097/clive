package com.rongtuoyouxuan.chatlive.crtbiz2.model.stream;

import com.google.gson.annotations.SerializedName;
import com.rongtuoyouxuan.chatlive.crtnet.BaseModel;

public class StreamHostMode extends BaseModel {

    @SerializedName("data")
    public AnchorInfo data = new AnchorInfo();

}
