package com.rongtuoyouxuan.chatlive.biz2.config

import com.rongtuoyouxuan.chatlive.biz2.model.country.CountryCodeModel
import retrofit2.Call
import retrofit2.http.GET

interface ConfigServerV2 {
    @GET("/static/countrycode_en.json")
    fun getCountryCodeEN(): Call<CountryCodeModel>

    @GET("/static/countrycode_zh.json")
    fun getCountryCodeZH(): Call<CountryCodeModel>
}