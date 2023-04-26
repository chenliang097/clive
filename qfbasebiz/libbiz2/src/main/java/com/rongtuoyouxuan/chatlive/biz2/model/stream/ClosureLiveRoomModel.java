package com.rongtuoyouxuan.chatlive.biz2.model.stream;

import com.google.gson.annotations.SerializedName;
import com.rongtuoyouxuan.chatlive.net2.BaseModel;

/**
 * Describe:
 *
 * @author Ning
 * @date 2019/6/25
 */
public class ClosureLiveRoomModel extends BaseModel {

    /**
     * data : true
     */

    @SerializedName("data")
    public boolean data = false;

    public String tag = "";
}
