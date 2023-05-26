package com.rongtuoyouxuan.chatlive.stream.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.rongtuoyouxuan.chatlive.crtcommonbiz.model.list.LoadEvent
import com.rongtuoyouxuan.chatlive.crtbiz2.model.stream.AnchorRoomSettingRequest
import com.rongtuoyouxuan.chatlive.crtbiz2.model.stream.RoomManagerListBean
import com.rongtuoyouxuan.chatlive.crtbiz2.stream.StreamBiz
import com.rongtuoyouxuan.chatlive.crtnet.BaseModel
import com.rongtuoyouxuan.chatlive.crtnet.RequestListener
import com.rongtuoyouxuan.chatlive.crtutil.util.LaToastUtil
import com.rongtuoyouxuan.chatlive.crtuikit.viewmodel.BaseListFragmentViewModel

open class SetManagerListViewModel(application: Application):
    BaseListFragmentViewModel<RoomManagerListBean>(application) {

    private var roomId:String = ""
    var delManagerLiveData: MutableLiveData<Int> = MutableLiveData<Int>()

    fun setRoomId(roomId:String){
        this.roomId = roomId
    }
    override fun doLoadData(mPage: Int, event: LoadEvent?) {
        StreamBiz.getRoomManagerList(roomId, mPage, 100, object :
            RequestListener<RoomManagerListBean> {
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
        StreamBiz.deleteRoomManagerList(request, object :
            RequestListener<BaseModel> {
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