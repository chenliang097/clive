package com.rongtuoyouxuan.chatlive.biz2.model.im.msg

import com.google.gson.annotations.SerializedName
import com.rongtuoyouxuan.chatlive.biz2.model.im.BaseRoomMessage

class RTAnchorLiveEndMsg: BaseRoomMessage() {

    override val itemType: Int
        get() = TYPE_ANCHOR_LIVE_END
}