package com.rongtuoyouxuan.chatlive.biz2.model.user

class FansListUserRequest {
    var user_id = ""
    var follow_id = ""

    constructor(user_id: String,follow_id: String){
        this.user_id = user_id
        this.follow_id = follow_id
    }
}