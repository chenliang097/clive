package com.rongtuoyouxuan.chatlive.biz2.push;

import com.rongtuoyouxuan.chatlive.biz2.ReqId;
import com.rongtuoyouxuan.chatlive.biz2.constanst.UrlConstanst;
import com.rongtuoyouxuan.chatlive.biz2.model.push.DeviceModel;
import com.rongtuoyouxuan.chatlive.net2.NetWorks;
import com.rongtuoyouxuan.chatlive.net2.RequestListener;

import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * Describe:
 *
 * @author Ning
 * @date 2019/7/4
 */
public class DeviceBiz {

    private static DeviceBiz instance;

    public static DeviceBiz getInstance() {
        if (instance == null) {
            synchronized (DeviceBiz.class) {
                if (instance == null) {
                    instance = new DeviceBiz();
                }
            }
        }
        return instance;
    }

    /**
     *
     参数	必选	类型	描述
     device_token	是	String	设备 device token length (32, 64)
     bl_time	是	Int	bl_time
     bl_token	是	String	bl_token
     __lg	是	String	语言
     __plat	是	String	平台
     channel	是	String	渠道
     __guid	是	String	guid
     __version	是	String	版本号
     brand	是	String	品牌
     model	是	String	型号
     netconn_type	是	String	网络类型
     sys_version	是	String	系统版本
     push_plat	是	String	推送服务商类型
     push_token	是	String	push token
     */
    public void sendDeviceInfo(final String device_token,
                               final String brand,
                               final String model,
                               final String netconn_type,
                               final String sys_version,
                               final String push_plat,
                               final String push_token,
                               RequestListener listener) {
        new NetWorks<DeviceModel>(null, listener) {
            @Override
            public String getBaseUrl() {
                return UrlConstanst.BASE_URL_LIVE_API_BOBOO_COM;
            }

            @Override
            public Call<DeviceModel> createCall(Retrofit retrofit) {
                return retrofit.create(DeviceServer.class).sendDeviceInfo(device_token,
                        brand,
                        model,
                        netconn_type,
                        sys_version,
                        push_plat,
                        push_token);
            }

            @Override
            protected String getReqId() {
                return ReqId.PUSH_DEVICE;
            }
        }.start();
    }
}
