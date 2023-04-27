package com.rongtuoyouxuan.chatlive.biz2.model.stream

class LiveRoomVisibleRangeRequest {
    var rule_type = 1      // 0 全部公开；1 给谁看；2 不让谁看
    var user_id = ""       // 主播ID
    var scene_id = ""      //  场次ID
    var user_id_list:MutableList<String>? = null   //  场次ID
    constructor(rule_type: Int,user_id: String, scene_id: String, user_id_list:MutableList<String>){
        this.rule_type = rule_type
        this.user_id = user_id
        this.scene_id = scene_id
        this.user_id_list = user_id_list
    }
}