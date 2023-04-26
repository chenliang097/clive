package com.rongtuoyouxuan.chatlive.biz2.model.stream

class AudienceExitRoomRequest {
    var live_id = ""
    var link_id = ""
    constructor(live_id: String,link_id: String){
        this.live_id = live_id
        this.link_id = link_id
    }
}