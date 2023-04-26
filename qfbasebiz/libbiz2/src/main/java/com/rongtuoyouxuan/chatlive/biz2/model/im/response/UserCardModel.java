package com.rongtuoyouxuan.chatlive.biz2.model.im.response;

import com.rongtuoyouxuan.chatlive.net2.BaseModel;
import com.google.gson.annotations.SerializedName;

public class UserCardModel extends BaseModel {

    /**
     * data
     */
    @SerializedName("data")
    public UserCardInfo data;
}
