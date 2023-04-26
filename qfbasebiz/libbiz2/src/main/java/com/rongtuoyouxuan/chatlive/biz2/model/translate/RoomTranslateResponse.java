package com.rongtuoyouxuan.chatlive.biz2.model.translate;

import androidx.annotation.IntDef;

import com.rongtuoyouxuan.chatlive.net2.BaseModel;
import com.google.gson.annotations.SerializedName;

public class RoomTranslateResponse extends BaseModel {


    public static final int ROOM_TYPE_START = 1;
    public static final int ROOM_TYPE_END = 2;
    /**
     * data : {"trans":"Hello there"}
     */

    @SerializedName("data")
    public DataBean data = new DataBean();

    @IntDef({ROOM_TYPE_START, ROOM_TYPE_END})
    public @interface RoomTranslateType {
    }

    public static class DataBean {
        @RoomTranslateType
        public int event = 0;
        /**
         * trans : Hello there
         */

        @SerializedName("trans")
        public String trans = "";

        public String reqId = "";
        public String original = "";
    }
}
