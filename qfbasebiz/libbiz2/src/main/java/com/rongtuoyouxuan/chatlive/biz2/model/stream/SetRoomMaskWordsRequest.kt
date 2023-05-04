package com.rongtuoyouxuan.chatlive.biz2.model.stream

class SetRoomMaskWordsRequest {
    var room_id = ""
    var user_id = ""
    var word = ""
    constructor(room_id: String,user_id: String,word: String){
        this.room_id = room_id
        this.user_id = user_id
        this.word = word
    }
}