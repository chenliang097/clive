package com.rongtuoyouxuan.chatlive.biz2.model.login.request

import androidx.annotation.Keep
import com.rongtuoyouxuan.chatlive.biz2.constanst.Config

/*
*Create by {Mr秦} on 2022/7/23
*/
@Keep
class LoginRequest constructor(
    var key: String,
    var mobile: String,
    var password: String,
    var region_code: String,
    var type: Int,
    var verify_code: String,
    var country_code: String,
) {
    //验证码注册or登录
    constructor(
        mobile: String,
        region_code: String,
        type: Int,
        verify_code: String,
        country_code: String,
    ) : this("", mobile, "", region_code, type, verify_code, country_code)

    //登录密码登录
    constructor(
        key: String,
        mobile: String,
        password: String,
        region_code: String,
        type: Int,
        country_code: String,
    ) : this(key, mobile, password, region_code, type, "", country_code)


    //游客登录
    constructor(
    ) : this("", "", "", "", Config.login_visitor, "", "")

}