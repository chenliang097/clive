package com.rongtuoyouxuan.chatlive.biz2.translate;

import com.rongtuoyouxuan.chatlive.biz2.model.translate.RoomTranslateResponse;
import com.rongtuoyouxuan.chatlive.biz2.model.translate.TranslateResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public interface TranslateServer {
//    POST /common/trans

    @FormUrlEncoded
    @POST("/common/trans")
    Call<TranslateResponse> translateContent(@Field("text") String text, @Field("translang") String translang, @Field("position") String position);

    @FormUrlEncoded
    @POST("/common/trans")
    Call<RoomTranslateResponse> RoomtranslateContent(@Field("text") String text, @Field("translang") String translang, @Field("position") String position);
}
