package com.rongtuoyouxuan.chatlive.stream.view.msgspan

import com.rongtuoyouxuan.chatlive.biz2.model.im.msg.BaseMsg

/*
*Create by {Mr秦} on 2022/10/20
*/
interface MessageCallBack {
    fun itemClick(msg:BaseMsg,type:Int)
}