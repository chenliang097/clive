package com.rongtuoyouxuan.chatlive.crtbiz2.model.im.msg.textmsg

import com.rongtuoyouxuan.chatlive.crtbiz2.model.im.BaseRoomMessage

class RTLiveEndMsg: BaseRoomMessage() {


    override val itemType: Int
        get() = TYPE_LIVE_END
}