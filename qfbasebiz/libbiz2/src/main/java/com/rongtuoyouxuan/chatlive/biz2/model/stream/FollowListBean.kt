package com.rongtuoyouxuan.chatlive.biz2.model.stream

import com.rongtuoyouxuan.chatlive.biz2.model.list.BaseListModel

class FollowListBean:BaseListModel<FansListBean.ItemBean>() {

    override fun getListData(): MutableList<FansListBean.ItemBean> {
        if(data != null && data?.follow_list != null){
            return data?.follow_list!!
        }
        return super.getListData()
    }

    var data: DataBean? = DataBean()

    class DataBean {
        var total = 0 // 总共条数
        var page = 0  // 当前页数
        var size = 0
        var follow_list:MutableList<FansListBean.ItemBean> = ArrayList()
    }

//    class ItemBean{
//        var id = ""
//        var nick_name = ""
//        var avatar = ""
//        var status = false  // 关注状态
//    }
}