package com.rongtuoyouxuan.chatlive.crtbiz2.model.im.msg

import com.rongtuoyouxuan.chatlive.crtbiz2.model.im.BaseRoomMessage

class RTAnchorLiveEndMsg: BaseRoomMessage() {

    override val itemType: Int
        get() = TYPE_ANCHOR_LIVE_END
}