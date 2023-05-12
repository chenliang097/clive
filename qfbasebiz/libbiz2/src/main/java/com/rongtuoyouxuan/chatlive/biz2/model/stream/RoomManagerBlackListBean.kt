package com.rongtuoyouxuan.chatlive.biz2.model.stream

import com.rongtuoyouxuan.chatlive.biz2.model.list.BaseListModel
import com.rongtuoyouxuan.chatlive.net2.BaseModel

class RoomManagerBlackListBean: BaseListModel<RoomManagerBlackListBean.ItemBean>() {
    var data: DataBean? = DataBean()

    override fun getListData(): MutableList<ItemBean> {
        if(data != null && data?.black_list != null){
            return data?.black_list!!
        }
        return super.getListData()
    }

    class DataBean{
        var black_list:MutableList<ItemBean> = ArrayList()
    }

    class ItemBean {
        var user_id = ""
        var nick_name = ""
        var avatar = ""
    }
}