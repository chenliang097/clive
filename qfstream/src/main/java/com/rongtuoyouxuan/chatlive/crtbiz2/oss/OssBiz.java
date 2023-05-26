package com.rongtuoyouxuan.chatlive.crtbiz2.oss;

import androidx.lifecycle.LifecycleOwner;

import com.rongtuoyouxuan.chatlive.crtbiz2.constanst.UrlConstanst;
import com.rongtuoyouxuan.chatlive.crtnet.NetWorks;
import com.rongtuoyouxuan.chatlive.crtnet.RequestListener;

import retrofit2.Call;
import retrofit2.Retrofit;

public class OssBiz {
    private static volatile OssBiz instance;

    private OssBiz() {
    }

    public static OssBiz getInstance() {
        if (instance == null) {
            synchronized (OssBiz.class) {
                if (instance == null) {
                    instance = new OssBiz();
                }
            }
        }
        return instance;
    }

    public void getOssConfig(LifecycleOwner lifecycleOwner, RequestListener<OssPathModel> listener) {
        new NetWorks<OssPathModel>(lifecycleOwner, listener) {

            @Override
            public String getBaseUrl() {
                return UrlConstanst.BASE_URL_LIVE_API_BOBOO_COM;
            }

            @Override
            public Call<OssPathModel> createCall(Retrofit retrofit) {
                return retrofit.create(OssServer.class).getPath();
            }

            @Override
            protected String getReqId() {
                return "";
            }
        }.start();
    }


    public void getOssNewConfig(LifecycleOwner lifecycleOwner, final String position, RequestListener<OssPathModel> listener) {
        new NetWorks<OssPathModel>(lifecycleOwner, listener) {

            @Override
            public String getBaseUrl() {
                return UrlConstanst.BASE_URL_LIVE_API_BOBOO_COM;
            }

            @Override
            public Call<OssPathModel> createCall(Retrofit retrofit) {
                return retrofit.create(OssServer.class).getNewPath();
            }

            @Override
            protected String getReqId() {
                return "";
            }
        }.start();
    }

}
