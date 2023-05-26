package com.rongtuoyouxuan.chatlive.crtbiz2.model.stream

class MsgHeaderBean {
    var mid = 0
    var room_id = ""
    var platform = ""
    var accepts = IntArray(10)

    constructor(mid:Int, room_id:String, platform:String, accepts:IntArray){
        this.mid = mid
        this.room_id = room_id
        this.platform = platform
        this.accepts = accepts
    }
}