package com.rongtuoyouxuan.chatlive.crtbiz2.model.stream;

import com.google.gson.annotations.SerializedName;
import com.rongtuoyouxuan.chatlive.crtnet.BaseModel;

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
