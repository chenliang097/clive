package com.rongtuoyouxuan.chatlive.crtbiz2.config

import com.rongtuoyouxuan.chatlive.crtbiz2.model.config.ClientConfModel
import retrofit2.Call
import retrofit2.http.GET

interface ClientConfigServer {
    @GET("/static/client_conf.json")
    fun getClientConfig(): Call<ClientConfModel>
}