package com.rongtuoyouxuan.chatlive.crtuikit

import android.content.Context

object UiKitConfig {
    var getLanguageContext: ((context: Context) -> Context)? = null
    var isArLanguage: (() -> Boolean) = { false }
    @JvmStatic
    var baseUrl: String = ""
}