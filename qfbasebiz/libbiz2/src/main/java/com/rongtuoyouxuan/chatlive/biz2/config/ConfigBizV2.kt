package com.rongtuoyouxuan.chatlive.biz2.config

import androidx.lifecycle.LifecycleOwner
import com.rongtuoyouxuan.chatlive.biz2.ReqId
import com.rongtuoyouxuan.chatlive.biz2.model.country.CountryCodeModel
import com.rongtuoyouxuan.chatlive.net2.RequestListener
import newNetworks

object ConfigBizV2 {
    fun getCountryCodeModelEn(
        listener: RequestListener<CountryCodeModel>,
        lifecycleOwner: LifecycleOwner? = null
    ) = newNetworks(
        lifecycleOwner,
        listener,
        ReqId.GET_COUNTRY_CODE,
    ) {
        it.create(ConfigServerV2::class.java).getCountryCodeEN()
    }

    fun getCountryCodeModelZH(
        listener: RequestListener<CountryCodeModel>,
        lifecycleOwner: LifecycleOwner? = null
    ) = newNetworks(
        lifecycleOwner,
        listener,
        ReqId.GET_COUNTRY_CODE,
    ) {
        it.create(ConfigServerV2::class.java).getCountryCodeZH()
    }
}