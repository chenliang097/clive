package com.rongtuoyouxuan.chatlive.crtbiz2.model.stream

class RoomManagerMuteListBean: com.rongtuoyouxuan.chatlive.crtcommonbiz.model.list.BaseListModel<RoomManagerMuteListBean.ItemBean>() {
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
        var nick_name = ""
        var avatar = ""
    }
}