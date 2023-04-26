package com.rongtuoyouxuan.chatlive.databus.config

import com.blankj.utilcode.util.SPUtils
import com.rongtuoyouxuan.chatlive.biz2.config.ConfigBizV2
import com.rongtuoyouxuan.chatlive.biz2.model.country.CountryCodeModel
import com.rongtuoyouxuan.chatlive.databus.DataBus
import com.rongtuoyouxuan.chatlive.gson.GsonSafetyUtils
import com.rongtuoyouxuan.chatlive.log.PLog
import com.rongtuoyouxuan.chatlive.net2.listener
import com.rongtuoyouxuan.chatlive.sp.SPConstants

open class ConfigManagerExt {
    companion object {
        private const val TAG = "ConfigManagerLoader"
    }

    private var countryCodeModelEN: CountryCodeModel =
        CountryCodeModel()
    private var countryCodeModelZH: CountryCodeModel =
        CountryCodeModel()

    fun getCountryName(code: String): String = when (DataBus.instance().localeManager.language.value) {
        "zh" -> countryCodeModelZH
        "zhtw" -> countryCodeModelZH
        else -> countryCodeModelEN
    }.data?.firstOrNull { it.code == code }?.name ?: code

    fun loadCountryCode() {
        countryCodeModelEN = getConfigFromDisk(
            SPConstants.StringConstants.COUNTRY_CODE_EN, CountryCodeModel::class.java,
            CountryCodeModel()
        )
        countryCodeModelZH = getConfigFromDisk(
            SPConstants.StringConstants.COUNTRY_CODE_CN, CountryCodeModel::class.java,
            CountryCodeModel()
        )

        ConfigBizV2.getCountryCodeModelEn(listener({ _, result ->
            countryCodeModelEN = result
            saveConfigToDisk(SPConstants.StringConstants.COUNTRY_CODE_EN, result)
        }, { reqId, errCode, msg ->
            PLog.d(TAG, "$reqId $errCode $msg")
        }))

        ConfigBizV2.getCountryCodeModelZH(listener({ _, result ->
            countryCodeModelZH = result
            saveConfigToDisk(SPConstants.StringConstants.COUNTRY_CODE_CN, result)
        }, { reqId, errCode, msg ->
            PLog.d(TAG, " $reqId $errCode $msg")
        }))
    }

    private fun saveConfigToDisk(key: String, value: Any) {
        try {
            val str = GsonSafetyUtils.getInstance().mGson.toJson(value)
            if (!str.isNullOrEmpty()) {
                SPUtils.getInstance().put(key, str)
            }
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }

    private fun <T> getConfigFromDisk(key: String, type: Class<T>, defaultValue: T): T {
        val str = SPUtils.getInstance().getString(key)
        if (!str.isNullOrEmpty()) {
            try {
                val obj = GsonSafetyUtils.getInstance().mGson.fromJson(str, type)
                if (obj != null) {
                    return obj
                }
            } catch (e: Throwable) {
                e.printStackTrace()
            }
        }
        return defaultValue
    }
}