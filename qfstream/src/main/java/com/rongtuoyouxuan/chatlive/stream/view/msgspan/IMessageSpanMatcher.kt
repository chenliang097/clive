package com.rongtuoyouxuan.chatlive.stream.view.msgspan

import android.widget.ImageView
import android.widget.TextView
import com.rongtuoyouxuan.chatlive.crtbiz2.model.im.BaseRoomMessage

interface IMessageSpanMatcher {

    fun loadSpan(textView: TextView, message: BaseRoomMessage)
    fun loadSpan(textView: TextView, img:ImageView, message: BaseRoomMessage)
    fun setItemClickCallBack(callBack: MessageCallBack)
}