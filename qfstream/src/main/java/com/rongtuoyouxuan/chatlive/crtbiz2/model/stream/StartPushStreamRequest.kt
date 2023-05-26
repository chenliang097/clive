package com.rongtuoyouxuan.chatlive.crtbiz2.model.stream

class StartPushStreamRequest {
    var room_id_str:String? = ""
    var scene_id_str: String?= ""
    var anchor_id_str: String? = ""
    var medium_cover_image:String? = ""
    var scene_title:String? = ""
    var city: String?= ""
    var longitude:Double? = 0.00
    var latitude:Double? = 0.00
    var position_switch:Boolean? = false
    var back_video:Boolean? = false
    constructor(room_id_str: String?, scene_id_str: String?, anchor_id_str: String?,
                medium_cover_image: String?, scene_title: String?, city: String?,longitude:Double?, latitude:Double?){
        this.room_id_str = room_id_str
        this.scene_id_str = scene_id_str
        this.anchor_id_str = anchor_id_str
        this.medium_cover_image = medium_cover_image
        this.scene_title = scene_title
        this.city = city
        this.longitude = longitude
        this.latitude = latitude
    }

    constructor(medium_cover_image: String?, scene_title: String?, city: String?,longitude:Double?, latitude:Double?, position_switch:Boolean?, back_video:Boolean?){
        this.medium_cover_image = medium_cover_image
        this.scene_title = scene_title
        this.city = city
        this.longitude = longitude
        this.latitude = latitude
        this.position_switch = position_switch
        this.back_video = back_video
    }

    constructor(){
    }
}