package com.rongtuoyouxuan.chatlive.crtbiz2.model.stream

class FansListBean:
    com.rongtuoyouxuan.chatlive.crtcommonbiz.model.list.BaseListModel<FansListBean.ItemBean>() {

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
        var id = 0
        var nick_name = ""
        var avatar = ""
        var status = false  // 关注状态
//        var isClick = false  // 是否点击
        var isFollow = false  // 是否关注
    }
}