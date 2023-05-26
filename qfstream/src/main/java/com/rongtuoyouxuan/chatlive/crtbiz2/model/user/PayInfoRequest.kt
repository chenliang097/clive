package com.rongtuoyouxuan.chatlive.crtbiz2.model.user

class PayInfoRequest {
    var pay_type = 0//支付类型：1.微信 2.支付宝 3.applepay
    var amount = 0
    var user_id = ""
    var device_type = 0//1.web 2.app 3.h5, 4小程序

    constructor(pay_type: Int,amount: Int, user_id:String, device_type:Int){
        this.pay_type = pay_type
        this.amount = amount
        this.user_id = user_id
        this.device_type = device_type
    }
}