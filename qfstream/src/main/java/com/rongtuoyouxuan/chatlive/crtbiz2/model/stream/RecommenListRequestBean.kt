package com.rongtuoyouxuan.chatlive.crtbiz2.model.stream

class RecommenListRequestBean {
    var base_64_scene_ids = ""
    var user_id_str = ""

    constructor(base_64_scene_ids: String, user_id_str:String){
        this.base_64_scene_ids = base_64_scene_ids
        this.user_id_str = user_id_str
    }
}