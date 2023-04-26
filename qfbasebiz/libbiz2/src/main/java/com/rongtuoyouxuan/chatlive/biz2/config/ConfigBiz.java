package com.rongtuoyouxuan.chatlive.biz2.config;


import androidx.lifecycle.LifecycleOwner;

import com.rongtuoyouxuan.chatlive.biz2.ReqId;
import com.rongtuoyouxuan.chatlive.biz2.constanst.UrlConstanst;
import com.rongtuoyouxuan.chatlive.biz2.model.config.ClientConfModel;
import com.rongtuoyouxuan.chatlive.net2.NetWorks;
import com.rongtuoyouxuan.chatlive.net2.RequestListener;

import retrofit2.Call;
import retrofit2.Retrofit;

public class ConfigBiz {

    private static volatile ConfigBiz instance;

    public static ConfigBiz getInstance(){
        if(instance == null){
            synchronized (ConfigBiz.class){
                if(instance == null){
                    instance = new ConfigBiz();
                }
            }
        }
        return instance;
    }

    public void getClientConf(LifecycleOwner lifecycleOwner, RequestListener<ClientConfModel> listener) {
        new NetWorks<ClientConfModel>(lifecycleOwner, listener) {
            @Override
            public String getBaseUrl() {
                return UrlConstanst.BASE_URL_LIVE_API_BOBOO_COM;
            }

            @Override
            public Call<ClientConfModel> createCall(Retrofit retrofit) {
                return retrofit.create(ConfigServer.class).getClientConf();
            }

            @Override
            protected String getReqId() {
                return ReqId.getClientConf;
            }
        }.start();
    }

}
