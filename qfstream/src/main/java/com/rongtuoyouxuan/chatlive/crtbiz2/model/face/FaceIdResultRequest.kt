package com.rongtuoyouxuan.chatlive.crtbiz2.model.face

class FaceIdResultRequest {
    var sim = ""
    var order_no = ""
    var user_id = ""

    constructor(sim: String, order_no:String, user_id:String){
        this.sim = sim
        this.order_no = order_no
        this.user_id = user_id
    }
}