package com.rongtuoyouxuan.chatlive.biz2.model.im.request

import androidx.annotation.Keep

/**
 *	@Description : push注册设备
 *	@Author : jianbo
 *	@Date : 2022/8/10  20:03
 */
@Keep
class RegisterDeviceRequest(

    var push_platform: String = "rc",
    var push_token: String,
    var brand: String,
    var model: String,
    var system_version: String,
    var network_type: String,
    var device_token: String,

    )