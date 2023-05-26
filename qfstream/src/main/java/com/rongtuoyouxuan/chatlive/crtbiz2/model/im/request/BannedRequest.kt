package com.rongtuoyouxuan.chatlive.crtbiz2.model.im.request


class BannedRequest {
    var chatroom_id = ""
    var anchor_id = ""
    var action = ""
    constructor(chatroom_id: String, anchor_id:String, action:String) {
        this.chatroom_id = chatroom_id
        this.anchor_id = anchor_id
        this.action = action
    }
}