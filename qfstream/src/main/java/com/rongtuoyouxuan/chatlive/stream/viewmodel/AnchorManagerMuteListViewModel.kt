package com.rongtuoyouxuan.chatlive.stream.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.rongtuoyouxuan.chatlive.biz2.model.im.request.MuteRequest
import com.rongtuoyouxuan.chatlive.biz2.model.list.LoadEvent
import com.rongtuoyouxuan.chatlive.biz2.model.stream.AnchorRoomSettingRequest
import com.rongtuoyouxuan.chatlive.biz2.model.stream.RoomManagerMuteListBean
import com.rongtuoyouxuan.chatlive.biz2.stream.StreamBiz
import com.rongtuoyouxuan.chatlive.net2.BaseModel
import com.rongtuoyouxuan.chatlive.net2.RequestListener
import com.rongtuoyouxuan.chatlive.util.LaToastUtil
import com.rongtuoyouxuan.libuikit.viewmodel.BaseListFragmentViewModel

open class AnchorManagerMuteListViewModel(application: Application):BaseListFragmentViewModel<RoomManagerMuteListBean>(application) {

    private var roomId:String = ""
    private var sceneId:String = ""
    var delManagerLiveData: MutableLiveData<Int> = MutableLiveData<Int>()

    fun setRoomId(roomId:String){
        this.roomId = roomId
    }

    fun setSceneId(sceneId:String){
        this.sceneId = sceneId
    }
    override fun doLoadData(mPage: Int, event: LoadEvent?) {
        StreamBiz.getRoomMuteList(null, sceneId, roomId, mPage, 30, object :RequestListener<RoomManagerMuteListBean>{
            override fun onSuccess(reqId: String?, result: RoomManagerMuteListBean?) {
                result?.event = event
                _getData().value = result
            }

            override fun onFailure(reqId: String?, errCode: String?, msg: String?) {

            }

        })
    }

    fun deleteRoomMute(fUserId:String, tUserId:String, position:Int){
        var request = MuteRequest(fUserId, tUserId, roomId, sceneId)
        StreamBiz.relieveRoomMute(request, object :RequestListener<BaseModel>{
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