package com.rongtuoyouxuan.chatlive.crtbiz2.model.config;

import android.text.TextUtils;

import com.rongtuoyouxuan.chatlive.crtbiz2.model.im.BaseRoomMessage;
import com.rongtuoyouxuan.chatlive.crtutil.gson.GsonSafetyUtils;
import com.rongtuoyouxuan.chatlive.crtnet.BaseModel;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MsgConfigModel extends BaseModel {

    @SerializedName("data")
    public HashMap<String, Value> data = new HashMap<>();

    public static class Value {

        @SerializedName("zh")
        public String zh = "";
        @SerializedName("zhtw")
        public String zhtw = "";
        @SerializedName("en")
        public String en = "";
        @SerializedName("ja")
        public String ja = "";
        @SerializedName("ko")
        public String ko = "";
        @SerializedName("ru")
        public String ru = "";
        @SerializedName("th")
        public String th = "";
        @SerializedName("vi")
        public String vi = "";
        @SerializedName("ar")
        public String ar = "";

        public Item getItem(String country) {
            String value = en;
            switch (country) {
                case "zh":
                    value = zh;
                    break;
                case "zhtw":
                    value = zhtw;
                    break;
                case "en":
                    value = en;
                    break;
                case "ja":
                    value = ja;
                    break;
                case "ko":
                    value = ko;
                    break;
                case "ru":
                    value = ru;
                    break;
                case "th":
                    value = th;
                    break;
                case "vi":
                    value = vi;
                    break;
                case "ar":
                    value = ar;
                    break;
            }
            if (TextUtils.isEmpty(value)) {
                value = en;
            }
            return GsonSafetyUtils.getInstance().fromJson(value, Item.class);
        }
    }

    public static class Item {
        @SerializedName("type")
        public String type = "";
        @SerializedName("action")
        public String action = "";
        @SerializedName("body")
        public BodyBean body = new BodyBean();

        public static class BodyBean extends BaseRoomMessage {

            @SerializedName("act")
            public String act = "";
            @SerializedName("act_val")
            public String actVal = "";
            @SerializedName("data")
            public List<DataBean> dataX = new ArrayList<>();

            @Override
            public int getItemType() {
                return TYPE_COMMON_TEMPLATEMSG;
            }

            public static class DataBean {

                @SerializedName("type")
                public String type = "";
                @SerializedName("static")
                public int staticX;
                @SerializedName("value")
                public String value = "";
                @SerializedName("color")
                public String color = "";

                public boolean isTextType() {
                    return "text".equals(type);
                }

                public boolean isUserRoleType() {
                    return "userrole".equals(type);
                }

                public boolean isUserLvType() {
                    return "userlv".equals(type);
                }

                public boolean isAnchorLvType() {
                    return "anchorlv".equals(type);
                }

                public boolean isGiftIconType() {
                    return "gifticon".equals(type);
                }

                public boolean isGiftNameType() {
                    return "giftname".equals(type);
                }

                public boolean isImgType() {
                    return "img".equals(type);
                }

            }
        }
    }
}
