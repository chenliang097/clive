package com.rongtuoyouxuan.chatlive.biz2.model.user

class FansListRequest {
    var user_id = ""
    var status = 0

    constructor(user_id: String,status: Int){
        this.user_id = user_id
        this.status = status
    }
}