package com.rongtuoyouxuan.chatlive.biz2.model.gif

import com.rongtuoyouxuan.chatlive.biz2.model.list.BaseListModel

class GifListBean: BaseListModel<GifListBean.GifItemBean>() {


    override fun getListData(): MutableList<GifItemBean> {
        if(data != null && data.list != null){
            return data.list
        }
        return super.getListData()
    }

    var data:Databean = Databean()

    data class Databean(
        var list:MutableList<GifItemBean> = ArrayList(),
        var page:Int = 0,
        var page_total:Int = 0,
        var size:Int = 0
    )

    data class GifItemBean(
        var gif_id:Int = 0,
        var gif_url:String = "",
        var targetId:String?,
    )
}