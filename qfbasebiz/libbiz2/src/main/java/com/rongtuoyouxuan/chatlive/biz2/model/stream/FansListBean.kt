package com.rongtuoyouxuan.chatlive.biz2.model.stream

import com.rongtuoyouxuan.chatlive.biz2.model.list.BaseListModel

class FansListBean:BaseListModel<FansListBean.ItemBean>() {

    override fun getListData(): List<FansListBean.ItemBean?>? {
        return if (null != data && data?.fans_list != null) {
            data?.fans_list
        } else java.util.ArrayList<ItemBean?>()
    }

    var data: DataBean? = DataBean()

    class DataBean {
        var is_self = false //  是否是自己
        var total = 0 // 总共条数
        var page = 0  // 当前页数
        var size = 0
        var fans_list:List<ItemBean> = ArrayList()
    }

    class ItemBean{
        var id = ""
        var nick_name = ""
        var avatar = ""
        var status = false  // 关注状态
    }
}