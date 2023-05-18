package com.rongtuoyouxuan.chatlive.biz2.model.stream

import com.rongtuoyouxuan.chatlive.biz2.model.list.BaseListModel
import com.rongtuoyouxuan.chatlive.net2.BaseModel

class RoomManagerListBean: BaseListModel<RoomManagerListBean.ItemBean>() {
    var data: DataBean? = DataBean()

    override fun getListData(): MutableList<ItemBean> {
        if(data != null && data?.room_admin_list != null){
            return data?.room_admin_list!!
        }
        return super.getListData()
    }

    class DataBean{
        var total = 0
        var room_admin_list:MutableList<ItemBean> = ArrayList()
    }

    class ItemBean {
        var user_id = ""
        var nick_name = ""
        var avatar = ""
    }
}