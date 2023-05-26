package com.rongtuoyouxuan.chatlive.crtnet;

import androidx.lifecycle.LifecycleOwner;

import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * 
 * date:2022/8/3-19:22
 * des:
 */
public class NetWorks2<Model extends BaseModel> extends NetWorkBoundResource<Model> {

    public NetWorks2(LifecycleOwner lifecycleOwner) {
        super(lifecycleOwner);
    }

    @Override
    public void success(BaseNetImpl.NetInfo netInfo, Model result) {

    }

    @Override
    public void fail(BaseNetImpl.NetInfo netInfo, String code, String msg) {

    }

    @Override
    public Call<Model> createCall(Retrofit retrofit) {
        return null;
    }

    public Retrofit getRetrofit(String baseUrl) {
        return BaseNetImpl.getInstance().getRetrofit(baseUrl, takeDefaultParams(), null);
    }

    @Override
    public String getBaseUrl() {
        return "";
    }
}
