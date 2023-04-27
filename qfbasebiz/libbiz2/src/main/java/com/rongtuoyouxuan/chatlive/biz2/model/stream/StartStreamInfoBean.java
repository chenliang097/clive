package com.rongtuoyouxuan.chatlive.biz2.model.stream;

import com.google.gson.annotations.SerializedName;
import com.rongtuoyouxuan.chatlive.net2.BaseModel;

public class StartStreamInfoBean extends BaseModel {


    @SerializedName("data")
    public DataBean data;
    public static class DataBean {
        public String room_number = "";
        public String stream_id = "";
        public String token = "";
        public long anchor_id = 0;
        public String anchor_name = "";
        public long room_id = 0;
        public String room_id_str = "";
        public long scene_id = 0;
        public String scene_id_str = "";
    }
}
