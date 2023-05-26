package com.rongtuoyouxuan.chatlive.crtbiz2.config

import androidx.lifecycle.LifecycleOwner
import com.rongtuoyouxuan.chatlive.crtbiz2.ReqId
import com.rongtuoyouxuan.chatlive.crtbiz2.model.country.CountryCodeModel
import newNetworks

object ConfigBizV2 {
    fun getCountryCodeModelEn(
        listener: com.rongtuoyouxuan.chatlive.crtnet.RequestListener<CountryCodeModel>,
        lifecycleOwner: LifecycleOwner? = null
    ) = newNetworks(
        lifecycleOwner,
        listener,
        ReqId.GET_COUNTRY_CODE,
    ) {
        it.create(ConfigServerV2::class.java).getCountryCodeEN()
    }

    fun getCountryCodeModelZH(
        listener: com.rongtuoyouxuan.chatlive.crtnet.RequestListener<CountryCodeModel>,
        lifecycleOwner: LifecycleOwner? = null
    ) = newNetworks(
        lifecycleOwner,
        listener,
        ReqId.GET_COUNTRY_CODE,
    ) {
        it.create(ConfigServerV2::class.java).getCountryCodeZH()
    }
}