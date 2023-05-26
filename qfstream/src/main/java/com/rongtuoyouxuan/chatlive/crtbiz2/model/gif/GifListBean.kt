package com.rongtuoyouxuan.chatlive.crtbiz2.model.gif

class GifListBean: com.rongtuoyouxuan.chatlive.crtcommonbiz.model.list.BaseListModel<GifListBean.GifItemBean>() {


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