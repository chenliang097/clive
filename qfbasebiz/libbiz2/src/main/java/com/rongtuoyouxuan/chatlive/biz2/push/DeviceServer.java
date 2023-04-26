package com.rongtuoyouxuan.chatlive.biz2.push;

import com.rongtuoyouxuan.chatlive.biz2.model.push.DeviceModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Describe:
 *
 * @author Ning
 * @date 2019/7/4
 */
public interface DeviceServer {

    @GET("/track/device")
    Call<DeviceModel> sendDeviceInfo(@Query("device_token") String device_token,
                                     @Query("brand") String brand,
                                     @Query("model") String model,
                                     @Query("netconn_type") String netconn_type,
                                     @Query("sys_version") String sys_version,
                                     @Query("push_plat") String push_plat,
                                     @Query("push_token") String push_token
    );

}
