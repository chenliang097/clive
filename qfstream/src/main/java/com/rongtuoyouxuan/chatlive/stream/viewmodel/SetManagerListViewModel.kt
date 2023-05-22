package com.rongtuoyouxuan.chatlive.stream.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.rongtuoyouxuan.chatlive.biz2.model.list.LoadEvent
import com.rongtuoyouxuan.chatlive.biz2.model.stream.AnchorRoomSettingRequest
import com.rongtuoyouxuan.chatlive.biz2.model.stream.RoomManagerListBean
import com.rongtuoyouxuan.chatlive.biz2.stream.StreamBiz
import com.rongtuoyouxuan.chatlive.net2.BaseModel
import com.rongtuoyouxuan.chatlive.net2.RequestListener
import com.rongtuoyouxuan.chatlive.util.LaToastUtil
import com.rongtuoyouxuan.libuikit.viewmodel.BaseListFragmentViewModel

open class SetManagerListViewModel(application: Application):BaseListFragmentViewModel<RoomManagerListBean>(application) {

    private var roomId:String = ""
    var delManagerLiveData: MutableLiveData<Int> = MutableLiveData<Int>()

    fun setRoomId(roomId:String){
        this.roomId = roomId
    }
    override fun doLoadData(mPage: Int, event: LoadEvent?) {
        StreamBiz.getRoomManagerList(roomId, mPage, 100, object :RequestListener<RoomManagerListBean>{
            override fun onSuccess(reqId: String?, result: RoomManagerListBean?) {
                result?.event = event
                _getData().value = result
            }

            override fun onFailure(reqId: String?, errCode: String?, msg: String?) {

            }

        })
    }

    fun deleteRoomManagerList(audience_id:String, position:Int){
        var request = AnchorRoomSettingRequest(roomId, audience_id)
        StreamBiz.deleteRoomManagerList(request, object :RequestListener<BaseModel>{
            override fun onSuccess(reqId: String?, result: BaseModel?) {
                if (result?.errCode == 0){
                    delManagerLiveData.value = position
                }
            }

            override fun onFailure(reqId: String?, errCode: String?, msg: String?) {
                LaToastUtil.showShort(msg)
            }

        })
    }
}