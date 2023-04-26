package com.rongtuoyouxuan.chatlive.biz2.model.stream.gift;


import com.rongtuoyouxuan.chatlive.net2.BaseModel;

public class SendGiftModel extends BaseModel {


    /**
     * data : {"giftRet":{"ce_id":"eb1a3444620b99fa8ee95f94c7d8a4f6","balance":66366}}
     */

    // 短视频送礼添加tag
    public String tag = "";

    private DataBean data = new DataBean();

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        public int intimacy = 0;
        /**
         * giftRet : {"ce_id":"eb1a3444620b99fa8ee95f94c7d8a4f6","balance":66366}
         */

        private GiftRetBean giftRet = new GiftRetBean();

        public GiftRetBean getGiftRet() {
            return giftRet;
        }

        public void setGiftRet(GiftRetBean giftRet) {
            this.giftRet = giftRet;
        }

        public static class GiftRetBean {
            /**
             * ce_id : eb1a3444620b99fa8ee95f94c7d8a4f6
             * balance : 66366
             */

            private String ce_id = "";
            private String balance = "";
            private int num = 0;         // 背包添加
            private String giftId = "";
            private String bid = "";     //礼物背包添加

            public String getCe_id() {
                return ce_id;
            }

            public void setCe_id(String ce_id) {
                this.ce_id = ce_id;
            }

            public String getBalance() {
                return balance;
            }

            public void setBalance(String balance) {
                this.balance = balance;
            }

            public int getNum() {
                return num;
            }

            public void setNum(int num) {
                this.num = num;
            }

            public String getGiftId() {
                return giftId;
            }

            public void setGiftId(String giftId) {
                this.giftId = giftId;
            }

            public String getBid() {
                return bid;
            }

            public void setBid(String bid) {
                this.bid = bid;
            }
        }
    }
}
