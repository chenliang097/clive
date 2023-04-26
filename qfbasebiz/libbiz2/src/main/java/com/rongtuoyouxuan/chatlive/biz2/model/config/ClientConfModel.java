package com.rongtuoyouxuan.chatlive.biz2.model.config;

import com.rongtuoyouxuan.chatlive.net2.BaseModel;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ClientConfModel extends BaseModel {

    private DataBean data = new DataBean();
    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {

        @SerializedName("mlswitch")
        public MlswitchBean mlswtich = new MlswitchBean();
        @SerializedName("ozf")
        public Refusepmsgswitch refusepmsgswitch = new Refusepmsgswitch();
        private PayBean pay = new PayBean();
        private VersionBean version = new VersionBean();
        private BaseConfigModel baseconf = new BaseConfigModel();
        private RCConf rcconf = new RCConf();
        @SerializedName("voicetag")
        private VoiceTagBean voiceTag = new VoiceTagBean();

        public PayBean getPay() {
            return pay;
        }

        public void setPay(PayBean pay) {
            this.pay = pay;
        }

        public VersionBean getVersion() {
            return version;
        }

        public void setVersion(VersionBean version) {
            this.version = version;
        }

        public BaseConfigModel getBaseconf() {
            return baseconf;
        }

        public void setBaseconf(BaseConfigModel baseconf) {
            this.baseconf = baseconf;
        }

        public RCConf getRcconf() {
            return rcconf;
        }

        public void setRcconf(RCConf rcconf) {
            this.rcconf = rcconf;
        }

        public VoiceTagBean getVoiceTag() {
            return voiceTag;
        }

        public void setVoiceTag(VoiceTagBean voiceTag) {
            this.voiceTag = voiceTag;
        }

        public static class TsCheckBean {
            public int status = 0;
        }

        public static class PayBean {
            /**
             * switch : 1
             */

            @SerializedName("switch")
            private int switchX = 0;

            public int getSwitchX() {
                return switchX;
            }

            public void setSwitchX(int switchX) {
                this.switchX = switchX;
            }
        }

        public static class MlswitchBean {
            @SerializedName("status")
            public int status = 0;
            @SerializedName("hometimes")
            public int homeTimes = 3;
        }

        public static class OtherpayswitchBean {
            @SerializedName("status")
            public int status = 0;
        }

        public static class Refusepmsgswitch {
            @SerializedName("status")
            public int status = 0;
        }

        public static class RCConf {
            public String file = "";
            public String navi = "";
        }

        public static class VersionBean {

            private String version = "";
            private String url = "";
            private String sign = "";
            private String downloadurl = "";
            private List<String> uplog = new ArrayList<>();

            public String getVersion() {
                return version;
            }

            public void setVersion(String version) {
                this.version = version;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getSign() {
                return sign;
            }

            public void setSign(String sign) {
                this.sign = sign;
            }

            public String getDownloadurl() {
                return downloadurl;
            }

            public void setDownloadurl(String downloadurl) {
                this.downloadurl = downloadurl;
            }

            public List<String> getUplog() {
                return uplog;
            }

            public void setUplog(List<String> uplog) {
                this.uplog = uplog;
            }
        }

        public static class VipPrice {
            public int status = 0;
        }


        public static class VideoCtCode {

            /**
             * items : ["VN","RU","IN","UA","PH","SG","CN","TH"]
             * def : VN
             */

            public String def = "";
            public List<String> items = new ArrayList<>();
        }
    }


    public static class FriendTabBean {
        private int status;
        /**
         * type : tagml
         * val : tmn
         * name : {"zh":"美女","en":"Beauty"}
         */

        private List<ItemsBean> items;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public List<ItemsBean> getItems() {
            return items;
        }

        public void setItems(List<ItemsBean> items) {
            this.items = items;
        }

        public static class ItemsBean implements Serializable {
            public static String TYPE_ALL = "mi_all";
            public static String TYPE_TAGML = "tagml";
            public static String TYPE_NEWML = "newml";

            private String type;
            private String val;
            /**
             * zh : 美女
             * en : Beauty
             */

            private MultiLanguageModel name;

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getVal() {
                return val;
            }

            public void setVal(String val) {
                this.val = val;
            }

            public MultiLanguageModel getName() {
                return name;
            }

            public void setName(MultiLanguageModel name) {
                this.name = name;
            }
        }
    }


    public static class VoiceTagBean {
        private int status;
        /**
         * type : tagvoice
         * val : 1
         * name : {"zh":"美女","en":"Beauty","ru":"красавица","vi":"Xinh đẹp"}
         */

        private List<ItemsBean> items;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public List<ItemsBean> getItems() {
            return items;
        }

        public void setItems(List<ItemsBean> items) {
            this.items = items;
        }

        public static class ItemsBean {
            private String type;
            @SerializedName("val")
            private String value;
            /**
             * zh : 美女
             * en : Beauty
             * ru : красавица
             * vi : Xinh đẹp
             */

            private MultiLanguageModel name;

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }

            public MultiLanguageModel getName() {
                return name;
            }

            public void setName(MultiLanguageModel name) {
                this.name = name;
            }
        }
    }
}
