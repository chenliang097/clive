package com.rongtuoyouxuan.chatlive.biz2.model.stream

class StreamStartInfoRequest {
//    var classify_id:Int? = 1;
//    var title: String?= ""
//    var pic: String? = ""
//    var longitude:Double? = 0.00
//    var latitude:Double? = 0.00
//    constructor(classify_id: Int?, title: String?, pic: String?, longitude:Double?, latitude:Double?){
//        this.classify_id = classify_id
//        this.title = title
//        this.pic = pic
//        this.longitude = longitude
//        this.latitude = latitude
//    }

    var user_id_str: String?= ""
    var user_name: String? = ""
    constructor(userId: String?, userName: String?){
        this.user_id_str = userId
        this.user_name = userName
    }
}