package com.rongtuoyouxuan.chatlive.crtbiz2.model.stream

class FansListRequest {
    var user_id = ""
    var status = 0   //  0：自己关注列表  1：他人的关注列表
    constructor(user_id: String, status: Int){
        this.user_id = user_id
        this.status = status
    }
}