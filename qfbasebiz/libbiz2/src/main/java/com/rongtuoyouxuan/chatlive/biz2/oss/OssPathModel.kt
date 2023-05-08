package com.rongtuoyouxuan.chatlive.biz2.oss

import com.rongtuoyouxuan.chatlive.net2.BaseModel

class OssPathModel : BaseModel() {
    var data = DataBean()

    class DataBean{
        var expiration = 0
        var access_key_id = ""
        var access_key_secret = ""
        var token = ""
    }
}