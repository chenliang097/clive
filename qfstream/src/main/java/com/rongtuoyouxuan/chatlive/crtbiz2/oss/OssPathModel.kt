package com.rongtuoyouxuan.chatlive.crtbiz2.oss

class OssPathModel : com.rongtuoyouxuan.chatlive.crtnet.BaseModel() {
    var data = DataBean()

    class DataBean{
        var expiration = 0
        var access_key_id = ""
        var access_key_secret = ""
        var token = ""
    }
}