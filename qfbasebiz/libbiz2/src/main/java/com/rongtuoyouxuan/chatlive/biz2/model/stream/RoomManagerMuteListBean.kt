package com.rongtuoyouxuan.chatlive.biz2.model.stream

import com.rongtuoyouxuan.chatlive.biz2.model.list.BaseListModel
import com.rongtuoyouxuan.chatlive.net2.BaseModel

class RoomManagerMuteListBean: BaseListModel<RoomManagerMuteListBean.ItemBean>() {
    var data: DataBean? = DataBean()

    override fun getListData(): MutableList<ItemBean> {
        if(data != null && data?.forbid_speak_list != null){
            return data?.forbid_speak_list!!
        }
        return super.getListData()
    }

    class DataBean{
        var forbid_speak_list:MutableList<ItemBean> = ArrayList()
    }

    class ItemBean {
        var user_id = ""
        var nick_name = 0
        var avatar = ""
    }
}