package com.rongtuoyouxuan.chatlive.biz2.model.config;

import com.rongtuoyouxuan.chatlive.net2.BaseModel;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class BaseConfigModel extends BaseModel {

    @SerializedName("emojiBashUrl")
    public String emojiBashUrl = "http://";

    @SerializedName("picUploadMinWidth")
    public int picUploadMinWidth = 750;

    @SerializedName("picUploadMinWidthAvatar")
    public int picUploadMinWidthAvatar = 1;

    @SerializedName("livepagenum")
    public int livepagenum = 50;

    @SerializedName("pullUrls")
    public List<String> pullUrls = new ArrayList<>();
}
