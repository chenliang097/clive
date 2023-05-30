package com.rongtuoyouxuan.chatlive.crtbiz2.model.stream

class RoomManagerBlackListBean: com.rongtuoyouxuan.chatlive.crtcommonbiz.model.list.BaseListModel<RoomManagerBlackListBean.ItemBean>() {
    var data: DataBean? = DataBean()

    override fun getListData(): MutableList<ItemBean> {
        if(data != null && data?.black_list != null){
            return data?.black_list!!
        }
        return super.getListData()
    }

    class DataBean{
        var total = 0
        var black_list:MutableList<ItemBean> = ArrayList()
    }

    class ItemBean {
        var user_id = ""
        var nick_name = ""
        var avatar = ""
    }
}