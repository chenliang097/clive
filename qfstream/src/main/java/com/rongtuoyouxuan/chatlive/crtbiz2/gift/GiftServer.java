package com.rongtuoyouxuan.chatlive.crtbiz2.gift;

import com.rongtuoyouxuan.chatlive.crtbiz2.model.stream.gift.GiftModel;
import com.rongtuoyouxuan.chatlive.crtbiz2.model.stream.gift.SendGiftModel;
import com.rongtuoyouxuan.chatlive.crtnet.CommonBooleanModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Describe:
 *
 * @author Ning
 * @date 2019/6/5
 */
public interface GiftServer {

    @GET("/gift/list")
    Call<GiftModel> getGiftList(@Query("position") String position, @Query("hostid") String hostid, @Query("sp") String sp);

    @FormUrlEncoded
    @POST("/gift/send?")
    Call<SendGiftModel> sendGift(@Field("giftid") String giftid,
                                 @Field("giftnum") String gitnum,
                                 @Field("tid") String tid,
                                 @Field("wishes") int isWishes,
                                 @Field("channelid") String channelid,
                                 @Field("psrc") String psrc);

    @GET("/package/list")
    Call<GiftModel> getPackageList();

    @FormUrlEncoded
    @POST("/package/sendgift?")
    Call<SendGiftModel> sendGiftPackage(@Field("giftid") String giftid,
                                        @Field("roomid") String roomid,
                                        @Field("giftnum") String gitnum,
                                        @Field("tid") String tid,
                                        @Field("bid") String bid);

    @FormUrlEncoded
    @POST("/gift/send?")
    Call<SendGiftModel> sendGift(@Field("position") String position
            , @Field("roomid") String roomid
            , @Field("giftid") String giftid
            , @Field("giftnum") String gitnum
            , @Field("tid") String tid
            , @Field("channelid") String channelid);

    @FormUrlEncoded
    @POST("/gift/wishes")
    Call<CommonBooleanModel> wishesGift(@Field("tgid") String tgid,
                                        @Field("giftid") String giftid,
                                        @Field("channelid") String channelid);
}
