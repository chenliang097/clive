package com.rongtuoyouxuan.chatlive.biz2.model.live

import com.rongtuoyouxuan.chatlive.net2.BaseModel

class StreamOnlineModel : BaseModel() {
    @JvmField
    var data: DataBean = DataBean()

    class DataBean {
        var list: List<OnlineInfo> = ArrayList<OnlineInfo>()
        var page:Int = 0
        var size:Int = 0
        var page_total:Int = 0
        var total:Int = 0
    }

    class OnlineInfo: Comparable<OnlineInfo>{
        var show_id:Long = 0
        var nickname:String = ""
        var gender:Int = 0
        var user_id:Long = 0
        var avatar:String = ""
        var user_level:Int = 0
        var score:Long = 0
        var is_online:Int = 0
        var is_follow:Int = 0
        var permission:Int = 0
        override fun compareTo(other: OnlineInfo): Int {
            val result: Long = other.score - score
            return if (result > 0) {
                1
            } else if (result == 0L) {
                0
            } else {
                -1
            }
        }
    }


}