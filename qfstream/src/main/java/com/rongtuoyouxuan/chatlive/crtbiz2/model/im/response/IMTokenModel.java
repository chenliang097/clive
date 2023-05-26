package com.rongtuoyouxuan.chatlive.crtbiz2.model.im.response;

import com.rongtuoyouxuan.chatlive.crtnet.BaseModel;
import com.google.gson.annotations.SerializedName;

public class IMTokenModel extends BaseModel {
    /**
     * data : {"admin":"zhiji001","token":"OoObimh7YGZkvN6RQPCw4itEIbmandILOm0q+aTngHPzgSfleZKCt/kB1V1ZhmSH3gpF3DBBoedRL+Yv4GCtJJGj2AB/3lSL","type":"RC"}
     */

    @SerializedName("data")
    public DataBean data = new DataBean();

    public static class DataBean {
        /**
         * admin : zhiji001
         * token : OoObimh7YGZkvN6RQPCw4itEIbmandILOm0q+aTngHPzgSfleZKCt/kB1V1ZhmSH3gpF3DBBoedRL+Yv4GCtJJGj2AB/3lSL
         * type : RC
         * appid : x18ywvqfxcvyc
         * ip : 192.168.1.241
         * port : 10200
         * sign : c66f4ddb37b6fc8d7b7b730ead2d976a
         * ts : 1572251339
         * period : 10
         */

        @SerializedName("token")
        public String token = "";

        @SerializedName("appid")
        public String appid;
        @SerializedName("ip")
        public String ip;
        @SerializedName("port")
        public int port;
        @SerializedName("period")
        public int period = 10;
        @SerializedName("sign")
        public String sign;
        @SerializedName("ts")
        public long ts;
        @SerializedName("admin")
        public String admin = "";
        @SerializedName("type")
        public String type = "";

        @Override
        public String toString() {
            return "DataBean{" +
                    "token='" + token + '\'' +
                    ", appid='" + appid + '\'' +
                    ", ip='" + ip + '\'' +
                    ", port=" + port +
                    ", period=" + period +
                    ", sign='" + sign + '\'' +
                    ", ts=" + ts +
                    ", admin='" + admin + '\'' +
                    ", type='" + type + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "IMTokenModel{" +
                "data=" + data +
                ", errCode=" + errCode +
                ", errMsg='" + errMsg + '\'' +
                '}';
    }
}
