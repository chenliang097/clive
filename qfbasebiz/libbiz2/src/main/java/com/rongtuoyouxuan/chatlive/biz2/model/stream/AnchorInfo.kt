package com.rongtuoyouxuan.chatlive.biz2.model.stream

import com.google.gson.annotations.SerializedName

class AnchorInfo{
    var avatar = "" //
    var name = ""//主播名字
    var nickname = ""//主播昵称
    @SerializedName("id")
    var anchor_id = 0L//主播id
    var cover = ""//设置的封面 本地用
    var show_id = ""
    var likeNum = 0
}