package com.rongtuoyouxuan.chatlive.biz2.user;

import androidx.lifecycle.LifecycleOwner;

import com.rongtuoyouxuan.chatlive.biz2.ReqId;
import com.rongtuoyouxuan.chatlive.biz2.constanst.UrlConstanst;
import com.rongtuoyouxuan.chatlive.biz2.model.user.FollowResponseModel;
import com.rongtuoyouxuan.chatlive.net2.NetWorks;
import com.rongtuoyouxuan.chatlive.net2.RequestListener;

import retrofit2.Call;
import retrofit2.Retrofit;

public class FollowSomeoneBiz {

    private static volatile FollowSomeoneBiz instance;

    public static FollowSomeoneBiz getInstance() {
        if (instance == null) {
            synchronized (FollowSomeoneBiz.class) {
                if (instance == null) {
                    instance = new FollowSomeoneBiz();
                }
            }
        }
        return instance;
    }

    /**
     * add
     */
    public void followAdd(LifecycleOwner lifecycleOwner, final String hostId, final String position, RequestListener<FollowResponseModel> listener) {
        new NetWorks<FollowResponseModel>(lifecycleOwner, listener) {

            @Override
            public Call<FollowResponseModel> createCall(Retrofit retrofit) {
                return retrofit.create(FollowSomeoneServer.class).followAdd(hostId);
            }

            @Override
            protected String getReqId() {
                return ReqId.USER_ADD_FOLLOW;
            }

            @Override
            public String getBaseUrl() {
                return  UrlConstanst.BASE_URL_LIVE_API_BOBOO_COM;
            }
        }.start();
    }

    /**
     * del
     */
    public void followDel(LifecycleOwner lifecycleOwner, final String hostId, RequestListener<FollowResponseModel> listener) {
        new NetWorks<FollowResponseModel>(lifecycleOwner, listener) {

            @Override
            public Call<FollowResponseModel> createCall(Retrofit retrofit) {
                return retrofit.create(FollowSomeoneServer.class).followDel(hostId);
            }

            @Override
            protected String getReqId() {
                return ReqId.USER_DEL_FOLLOW;
            }

            @Override
            public String getBaseUrl() {
                return  UrlConstanst.BASE_URL_LIVE_API_BOBOO_COM;
            }
        }.start();
    }
}
