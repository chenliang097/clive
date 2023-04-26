package com.rongtuoyouxuan.chatlive.biz2.stream

import com.rongtuoyouxuan.chatlive.biz2.model.gif.GifListBean
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GifListServer {
    @GET("/gif/list")
    fun getGifList(@Query("page") page:Int, @Query("size") size:Int):Call<GifListBean>
}