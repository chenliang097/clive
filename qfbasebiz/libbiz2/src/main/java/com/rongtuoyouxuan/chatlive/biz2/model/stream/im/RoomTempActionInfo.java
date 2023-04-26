package com.rongtuoyouxuan.chatlive.biz2.model.stream.im;

import com.rongtuoyouxuan.chatlive.net2.BaseModel;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class RoomTempActionInfo extends BaseModel {

    @SerializedName("data")
    public DataBean data = new DataBean();

    public static class DataBean {

        @SerializedName("hellomsg")
        public HelloMsg hellomsg = new HelloMsg();

        @SerializedName("roomnotice")
        public RoomNotice roomNotice = new RoomNotice();
    }

    public static class PkSwitch {
        @SerializedName("status")
        public int status = 0;
        public long nowtime = 0;
    }

    public static class Slot {
        public int status = 0;
        public long nowtime = 0;
        public int luck_score = 0;
        public List<SlotImgList> options;//图片资源 暂时没用
    }

    public static class SlotImgList {
        public String name;
        public String icon;
    }

    public static class HelloMsg {
        public Integer status = 0;
        public List<ItemsBean> items = new ArrayList<>();
        public Integer nowtime = 0;

        public static class ItemsBean {
            public String en = "";
        }
    }

    public static class RoomNotice {
       public int status = 0;
       public String content = "";
       public long nowtime = 0l;
    }
}
