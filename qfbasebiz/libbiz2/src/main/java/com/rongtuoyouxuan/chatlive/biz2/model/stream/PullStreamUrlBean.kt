package com.rongtuoyouxuan.chatlive.biz2.model.stream

class PullStreamUrlBean {
    var flv = PullStreamUrl()
    var hls = PullStreamUrl()

    data class PullStreamUrl(
        var base_url: String = "",
        var query: String = ""
    )



}