package com.rongtuoyouxuan.chatlive.crtbiz2.model.user

class ReportRequest{

//    "user_id": "1",                       //   举报人ID
//    "barrage_user_id": "11",      //   被举报人ID
//    "barrage": "饿了",                //   弹幕
//    "report_type": 1,                  // 举报类型
//    "note": "饿了",                      // 举报描述 （暂无）
//    "user_name":"asdfasdf",     // 举报人名称
//    "barrage_user_name":"asdfasd", // 被举报人名称
//    "room_id":"231234"              // 房间ID

    var user_id = ""
    var barrage_user_id = ""
    var barrage = ""
    var report_type = 0
    var note = ""
    var user_name = ""
    var barrage_user_name = ""
    var room_id = ""

    constructor(user_id: String, barrage_user_id: String, barrage: String, report_type: Int,
                note: String, user_name: String, barrage_user_name: String, room_id: String){
        this.user_id = user_id
        this.barrage_user_id = barrage_user_id
        this.barrage = barrage
        this.report_type = report_type
        this.note = note
        this.user_name = user_name
        this.barrage_user_name = barrage_user_name
        this.room_id = room_id
    }
}