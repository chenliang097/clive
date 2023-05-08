package com.rongtuoyouxuan.chatlive.biz2.oss;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface OssServer {
    @POST("/userProxy/v1/sts/generateToken")
    Call<OssPathModel> getPath();

    @POST("/userProxy/v1/sts/generateToken")
    Call<OssPathModel> getNewPath();
}
