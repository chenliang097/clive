package com.rongtuoyouxuan.chatlive.crtbiz2.model.face

class FaceIdRequest {
    var name = ""
    var id_no = ""
    var user_id = ""

    constructor(name: String, id_no:String, user_id:String){
        this.name = name
        this.id_no = id_no
        this.user_id = user_id
    }
}