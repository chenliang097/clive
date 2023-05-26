package com.rongtuoyouxuan.chatlive.crtbiz2.oss;


import retrofit2.Call;
import retrofit2.http.POST;

public interface OssServer {
    @POST("/userProxy/v1/sts/generateToken")
    Call<OssPathModel> getPath();

    @POST("/userProxy/v1/sts/generateToken")
    Call<OssPathModel> getNewPath();
}
