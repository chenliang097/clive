package com.rongtuoyouxuan.chatlive.biz2.user;

import com.rongtuoyouxuan.chatlive.biz2.model.user.FollowResponseModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface FollowSomeoneServer {
    @FormUrlEncoded
    @POST("/follow/add")
    Call<FollowResponseModel> followAdd(@Field("uid") String hostid);

    @FormUrlEncoded
    @POST("/follow/del")
    Call<FollowResponseModel> followDel(@Field("uid") String hostid);
}
