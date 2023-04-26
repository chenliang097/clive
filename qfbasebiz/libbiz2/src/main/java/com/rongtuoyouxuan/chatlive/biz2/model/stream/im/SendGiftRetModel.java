package com.rongtuoyouxuan.chatlive.biz2.model.stream.im;

import com.rongtuoyouxuan.chatlive.net2.BaseModel;
import com.google.gson.annotations.SerializedName;

public class SendGiftRetModel extends BaseModel {

    @SerializedName("data")
    public Data data = new Data();

    public static class Data {
        @SerializedName("msgRet")
        public _Message msg = new _Message();

        @SerializedName("giftRet")
        public GiftRet giftRet = new GiftRet();
    }

    public static class GiftRet {
        @SerializedName("balance")
        public int balance = 0;
    }
}