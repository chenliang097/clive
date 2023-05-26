package com.rongtuoyouxuan.chatlive.crtbiz2.config;


import com.rongtuoyouxuan.chatlive.crtbiz2.model.config.ClientConfModel;
import com.rongtuoyouxuan.chatlive.crtbiz2.model.config.UpdateModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ConfigServer {

    @GET("")
    Call<ClientConfModel> getClientConf();

    @GET("")
    Call<UpdateModel> getLastVersion(@Path("lastversion") String lastversion);

}
