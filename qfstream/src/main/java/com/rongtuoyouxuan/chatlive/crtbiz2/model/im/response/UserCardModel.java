package com.rongtuoyouxuan.chatlive.crtbiz2.model.im.response;

import com.rongtuoyouxuan.chatlive.crtnet.BaseModel;
import com.google.gson.annotations.SerializedName;

public class UserCardModel extends BaseModel {

    /**
     * data
     */
    @SerializedName("data")
    public UserCardInfo data;
}
