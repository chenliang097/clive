package com.rongtuoyouxuan.chatlive.databus.util

import android.content.Context
import com.rongtuoyouxuan.chatlive.databus.DataBus
import com.rongtuoyouxuan.chatlive.databus.language.LocaleManager
import com.hbb20.Country
import com.hbb20.CountryCodePicker

/*
*Create by {Mr秦} on 2022/7/23
*/
class AreaCodeUtil {
    companion object {
        fun getLibraryLanguageCountryList(context: Context): List<Country> {
            val result = when (DataBus.instance().localeManager.language.value) {
                LocaleManager.LANGUAGE_ENGLISH -> CountryCodePicker.Language.ENGLISH
                LocaleManager.LANGUAGE_SIMPLIFIED_CHINESE -> CountryCodePicker.Language.CHINESE_SIMPLIFIED
                LocaleManager.LANGUAGE_ZH_TW -> CountryCodePicker.Language.CHINESE_TRADITIONAL
                LocaleManager.LANGUAGE_TH -> CountryCodePicker.Language.ENGLISH
                LocaleManager.LANGUAGE_IN -> CountryCodePicker.Language.INDONESIA
                LocaleManager.LANGUAGE_MS -> CountryCodePicker.Language.ENGLISH
                LocaleManager.LANGUAGE_VIETNAM -> CountryCodePicker.Language.VIETNAMESE
                else -> CountryCodePicker.Language.ENGLISH
            }
            return Country.getLibraryMasterCountryList(context,
                result)
        }
    }
}