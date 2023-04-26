package com.rongtuoyouxuan.chatlive.biz2.gift;

import androidx.lifecycle.LifecycleOwner;

import com.rongtuoyouxuan.chatlive.biz2.ReqId;
import com.rongtuoyouxuan.chatlive.biz2.constanst.UrlConstanst;
import com.rongtuoyouxuan.chatlive.biz2.model.stream.gift.GiftModel;
import com.rongtuoyouxuan.chatlive.biz2.model.stream.gift.SendGiftModel;
import com.rongtuoyouxuan.chatlive.net2.CommonBooleanModel;
import com.rongtuoyouxuan.chatlive.net2.NetWorks;
import com.rongtuoyouxuan.chatlive.net2.RequestListener;

import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * Describe: 礼物
 *
 * @author Ning
 * @date 2019/6/5
 */
public class GiftBiz {

    private static GiftBiz instance;

    public static GiftBiz getInstance() {
        if (instance == null) {
            synchronized (GiftBiz.class) {
                if (instance == null) {
                    instance = new GiftBiz();
                }
            }
        }
        return instance;
    }

    /**
     * 获取礼物列表
     *
     * @param lifecycleOwner
     * @param position       在什么位置送（chat、live、home）
     * @param sp             0普通礼物，1特权礼物
     * @param listener
     */
    public void getGiftList(LifecycleOwner lifecycleOwner, final String position, final String hostid, final String sp, RequestListener<GiftModel> listener) {
        new NetWorks<GiftModel>(lifecycleOwner, listener) {

            @Override
            public String getBaseUrl() {
                return UrlConstanst.BASE_URL_LIVE_API_BOBOO_COM;
            }

            @Override
            public Call<GiftModel> createCall(Retrofit retrofit) {
                return retrofit.create(GiftServer.class).getGiftList(position, hostid, sp);
            }

            @Override
            protected String getReqId() {
                return ReqId.GIFT_LIST;
            }
        }.start();
    }

    /**
     * 发送礼物
     *
     * @param giftid    礼物idat、live、home）
     * @param giftnum   礼物数量
     * @param tid       送给谁
     * @param wishes    是否为索要礼物
     * @param channelid 频道ID
     * @param listener
     */
    public void sendGift(String giftid,
                         String gitnum,
                         String tid,
                         boolean isWishes,
                         String channelid,
                         String psrc,
                         RequestListener<SendGiftModel> listener) {
        new NetWorks<SendGiftModel>(null, listener) {

            @Override
            public String getBaseUrl() {
                return UrlConstanst.BASE_URL_LIVE_API_BOBOO_COM;
            }

            @Override
            public Call<SendGiftModel> createCall(Retrofit retrofit) {
                return retrofit.create(GiftServer.class).sendGift(giftid, gitnum, tid, isWishes ? 1 : 0, channelid, psrc);
            }

            @Override
            protected String getReqId() {
                return ReqId.SEND_GIFT;
            }
        }.start();
    }

    /**
     * 获取背包列表
     */
    public void getPackageList(LifecycleOwner lifecycleOwner, RequestListener<GiftModel> listener) {
        new NetWorks<GiftModel>(lifecycleOwner, listener) {

            @Override
            public String getBaseUrl() {
                return UrlConstanst.BASE_URL_LIVE_API_BOBOO_COM;
            }

            @Override
            public Call<GiftModel> createCall(Retrofit retrofit) {
                return retrofit.create(GiftServer.class).getPackageList();
            }

            @Override
            protected String getReqId() {
                return ReqId.GIFT_PACKAGE_LIST;
            }
        }.start();
    }


    /**
     * @param giftid   gift id
     * @param roomId   在哪个房间送的
     * @param giftnum  礼物数量
     * @param tid      给谁送礼
     * @param bid      bid
     * @param listener 监听
     */
    public void sendGiftPackage(final String giftid, final String roomId, final String giftnum, final String tid, final String bid,
                                RequestListener<SendGiftModel> listener) {
        new NetWorks<SendGiftModel>(null, listener) {

            @Override
            public String getBaseUrl() {
                return UrlConstanst.BASE_URL_LIVE_API_BOBOO_COM;
            }

            @Override
            public Call<SendGiftModel> createCall(Retrofit retrofit) {
                return retrofit.create(GiftServer.class).sendGiftPackage(giftid, roomId, giftnum, tid, bid);
            }

            @Override
            protected String getReqId() {
                return ReqId.SEND_GIFT;
            }
        }.start();
    }

    /**
     * 发送礼物
     *
     * @param position  发送位置（chat、live、home）
     * @param roomId    当前房间的id
     * @param giftid    礼物id
     * @param giftnum   数量
     * @param tid       对方id
     * @param channleId 频道ID
     * @param listener
     */
    public void sendGift(final String position, final String roomId, final String giftid, final String giftnum, final String tid,
                         final String channleId, RequestListener<SendGiftModel> listener) {
        new NetWorks<SendGiftModel>(null, listener) {

            @Override
            public String getBaseUrl() {
                return UrlConstanst.BASE_URL_LIVE_API_BOBOO_COM;
            }

            @Override
            public Call<SendGiftModel> createCall(Retrofit retrofit) {
                return retrofit.create(GiftServer.class).sendGift(position, roomId, giftid, giftnum, tid, channleId);
            }

            @Override
            protected String getReqId() {
                return ReqId.SEND_GIFT1;
            }
        }.start();
    }

    public void wishesGift(String tgid, String giftid, String channelid, RequestListener<CommonBooleanModel> listener) {
        new NetWorks<CommonBooleanModel>(null, listener) {

            @Override
            public String getBaseUrl() {
                return UrlConstanst.BASE_URL_LIVE_API_BOBOO_COM;
            }

            @Override
            public Call<CommonBooleanModel> createCall(Retrofit retrofit) {
                return retrofit.create(GiftServer.class).wishesGift(tgid, giftid, channelid);
            }

            @Override
            protected String getReqId() {
                return ReqId.SEND_GIFT1;
            }
        }.start();
    }

}
