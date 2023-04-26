package com.rongtuoyouxuan.chatlive.databus;

import com.rongtuoyouxuan.chatlive.net2.BaseModel;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class CountryCodeModel extends BaseModel {

    /**
     * name : 安哥拉
     * code : AO
     * lg : en-US
     */
    @SerializedName("data")
    public List<CountryCodeItem> data = new ArrayList<>();

    public static class CountryCodeItem {
        @SerializedName("name")
        private String name;
        @SerializedName("code")
        private String code;
        @SerializedName("lg")
        private String lg;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getLg() {
            return lg;
        }

        public void setLg(String lg) {
            this.lg = lg;
        }
    }
}
