package com.rongtuoyouxuan.chatlive.qfcommon.webview.model

import androidx.annotation.Keep

/*
*Create by {Mr秦} on 2022/8/19
*/
@Keep
class ComInterfaceData(
    val type: String,
    val receive: String,
    val jsCallback: String,
    val access_url: String,
    val scene: Int = 0,
    val status: Int = 1
)