package com.rongtuoyouxuan.chatlive.crtbiz2.model.config;

import com.rongtuoyouxuan.chatlive.crtnet.BaseModel;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class UpdateModel extends BaseModel {

    @SerializedName("data")
    public DataBean data = new DataBean();

    public static class DataBean {

        @SerializedName("ios")
        public IosBean ios;
        @SerializedName("android")
        public AndroidBean android = new AndroidBean();

        public static class IosBean {
            /**
             * version : 1.0.0.1
             * uplog : ["aaaa","bbbbb","ccccc"]
             * url :
             * sign : asdfadfafd
             */

            @SerializedName("version")
            public String version;
            @SerializedName("url")
            public String url;
            @SerializedName("sign")
            public String sign;
            @SerializedName("downloadurl")
            public String downloadurl;
            @SerializedName("uplog")
            public List<String> uplog;
        }

        public static class AndroidBean {
            /**
             * version : 1.0.0.1
             * uplog : ["aaaa","bbbbb","ccccc"]
             * url :
             * sign : asdfasd
             */

            @SerializedName("version")
            public String version = "";
            @SerializedName("url")
            public String url = "";
            @SerializedName("sign")
            public String sign = "";
            @SerializedName("downloadurl")
            public String downloadurl = "";
            @SerializedName("uplog")
            public List<String> uplog = new ArrayList<>();
            @SerializedName("pkg")
            public String pkg = "";
            @SerializedName("jump")
            public int jump = 0;
        }
    }
}
