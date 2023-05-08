package com.rongtuoyouxuan.chatlive.biz2.model.im.msg.textmsg

import com.google.gson.annotations.SerializedName
import com.rongtuoyouxuan.chatlive.biz2.model.im.BaseRoomMessage

class RTLiveEndMsg: BaseRoomMessage() {


    override val itemType: Int
        get() = TYPE_LIVE_END
}