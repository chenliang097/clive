package com.rongtuoyouxuan.chatlive.biz2.model.stream.im

class PushStreamHeartBeatRequest {
    var msg = ""
    var live_id = ""
    constructor(msg: String, live_id:String){
        this.msg = msg
        this.live_id = live_id
    }
}