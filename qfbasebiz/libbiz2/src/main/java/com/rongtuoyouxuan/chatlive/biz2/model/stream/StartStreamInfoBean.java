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
        public boolean is_access = false;
        public String cdn_url = "";
        public String anchor_avatar = "";
        public String last_cover_image = "";
        public String scene_title = "";
        public boolean back_video = false;
        public boolean position_switch = false;
    }
}
