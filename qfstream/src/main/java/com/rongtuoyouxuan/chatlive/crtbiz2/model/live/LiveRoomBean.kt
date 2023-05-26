package com.rongtuoyouxuan.chatlive.crtbiz2.model.live

import com.rongtuoyouxuan.chatlive.crtbiz2.model.stream.PullStreamUrlBean
import com.rongtuoyouxuan.chatlive.crtbiz2.model.stream.AnchorInfo
import com.google.gson.annotations.SerializedName

class LiveRoomBean : com.rongtuoyouxuan.chatlive.crtnet.BaseModel() {

    var data = DataBean()

    class DataBean{
        var cdn_sdk = LiveCDNSdkBean()
        var live = LiveLiveBean()
        var anchor = AnchorInfo()
        var live_links = ArrayList<LiveLinks>()
        @SerializedName("is_follow")
        var followstatus = false
        var like_total:Long = 0
        var permissions:Permissions = Permissions()
    }

    data class Permissions(
        var room_forbidden_speaking:Boolean = false,
        var forbidden_speaking:Boolean  = false
    )

    data class PayLive(
        var pay_type:String = "",
        var payment_amount:Int  = 0,
        var preview_time:Int  = 0,
        var isPayedOnce: Boolean? = false,
        var anchorCoverImg: String? = ""

    )

    class LiveCDNSdkBean{
        var play_urls: PullStreamUrlBean = PullStreamUrlBean()//拉流地址
        var stream_id = ""//
    }

    class LiveLiveBean{
        var anchor_id = 0L //
        var id = 0L//直播id
        var pic = ""//直播背景
        var status = ""//直播状态
        var title = ""//直播标题
        var live_type = ""//直播类型
    }

    data class LiveLinks(
        var avatar:String = "",
        var nick_name:String = "",
        var id:Int = 0,
        var link_id:Long = 0,
        var live_id:Long = 0,
        var anchor_id:Long = 0L,
        var audience_id:Long = 0L,
        var camera:Int = 0,
        var mic:Int = 0,
        var play_urls: PullStreamUrlBean = PullStreamUrlBean()
    )

}