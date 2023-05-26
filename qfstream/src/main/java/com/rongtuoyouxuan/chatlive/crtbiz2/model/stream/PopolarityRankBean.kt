package com.rongtuoyouxuan.chatlive.crtbiz2.model.stream

import com.rongtuoyouxuan.chatlive.crtcommonbiz.model.list.BaseListModel

class PopolarityRankBean: BaseListModel<PopolarityRankBean.ItemBean>() {

    override fun getListData(): MutableList<PopolarityRankBean.ItemBean> {
        if(data != null && data?.rank_list != null){
            return data?.rank_list!!
        }
        return super.getListData()
    }

    var data: DataBean? = DataBean()

    class DataBean {
        var count = 0 // 总共条数
        var page = 0  // 当前页数
        var size = 0
        var show = false // true 展示 false不展示
        var rank_list:MutableList<ItemBean> = ArrayList()
        var self_rank:ItemBean = ItemBean()
    }

    class ItemBean{
        var user_id = 0L
        var nick_name = ""
        var avatar = ""
        var degree = 0
        var rank = 0
    }
}