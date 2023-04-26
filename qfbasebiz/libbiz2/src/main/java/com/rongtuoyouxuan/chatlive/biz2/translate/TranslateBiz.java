package com.rongtuoyouxuan.chatlive.biz2.translate;


import androidx.lifecycle.LifecycleOwner;

import com.rongtuoyouxuan.chatlive.biz2.ReqId;
import com.rongtuoyouxuan.chatlive.biz2.constanst.UrlConstanst;
import com.rongtuoyouxuan.chatlive.biz2.model.translate.RoomTranslateResponse;
import com.rongtuoyouxuan.chatlive.biz2.model.translate.TranslateResponse;
import com.rongtuoyouxuan.chatlive.net2.NetWorks;
import com.rongtuoyouxuan.chatlive.net2.RequestListener;

import retrofit2.Call;
import retrofit2.Retrofit;

public class TranslateBiz {

    private static volatile TranslateBiz instance;

    public static TranslateBiz getInstance() {
        if (instance == null) {
            synchronized (TranslateBiz.class) {
                if (instance == null) {
                    instance = new TranslateBiz();
                }
            }
        }
        return instance;
    }

    public void translateContent(LifecycleOwner lifecycleOwner, final String content, final String translang, final RequestListener<TranslateResponse> listener) {
        new NetWorks<TranslateResponse>(lifecycleOwner,listener){

            @Override
            public Call<TranslateResponse> createCall(Retrofit retrofit) {
                return retrofit.create(TranslateServer.class).translateContent(content,translang,"dynamic");
            }

            @Override
            protected String getReqId() {
                return ReqId.TRANSLATE;
            }

            @Override
            public String getBaseUrl() {
                return UrlConstanst.BASE_URL_LIVE_API_BOBOO_COM;
            }
        }.start();
    }


    public void translateRoomContent(LifecycleOwner lifecycleOwner, final String content, final String translang, final String position, RequestListener<RoomTranslateResponse> listener) {
        new NetWorks<RoomTranslateResponse>(lifecycleOwner,listener){

            @Override
            public Call<RoomTranslateResponse> createCall(Retrofit retrofit) {
                return retrofit.create(TranslateServer.class).RoomtranslateContent(content,translang,position);
            }

            @Override
            protected String getReqId() {
                return ReqId.TRANSLATE;
            }

            @Override
            public String getBaseUrl() {
                return UrlConstanst.BASE_URL_LIVE_API_BOBOO_COM;
            }
        }.start();
    }

}
