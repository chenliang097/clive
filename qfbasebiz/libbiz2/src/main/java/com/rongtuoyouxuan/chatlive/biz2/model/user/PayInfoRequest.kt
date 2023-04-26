package com.rongtuoyouxuan.chatlive.biz2.model.user

class PayInfoRequest {
    var pay_type = 0
    var amount = 0
    var user_id = 0
    var device_type = 0

    constructor(pay_type: Int,amount: Int, user_id:Int, device_type:Int){
        this.pay_type = pay_type
        this.amount = amount
        this.user_id = user_id
        this.device_type = device_type
    }
}