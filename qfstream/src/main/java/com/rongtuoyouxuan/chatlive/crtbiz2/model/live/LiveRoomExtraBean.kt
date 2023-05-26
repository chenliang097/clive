package com.rongtuoyouxuan.chatlive.crtbiz2.model.live

import com.rongtuoyouxuan.chatlive.crtbiz2.model.stream.AdsBean

class LiveRoomExtraBean : com.rongtuoyouxuan.chatlive.crtnet.BaseModel() {

    var data = DataBean()

    data class DataBean(
        var ads:List<AdsBean> = ArrayList(),
        var diamond_total:Long = 0,
        var like_total:Int = 0,
        var live_convention:String = "",
        var fans_club:FansClubBean = FansClubBean(),
        var share_url:String = "",
        var contribution_top_diamond_total:Int = 0,
        var apply_total:Int = 0
    )

    data class FansClubBean(
        var id:Long = 0,
        var owner_user_id:Long = 0,
        var name:String = ""
    )
}