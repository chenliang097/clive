package com.rongtuoyouxuan.chatlive.stream.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.rongtuoyouxuan.chatlive.crtbiz2.model.im.request.MuteRequest
import com.rongtuoyouxuan.chatlive.crtcommonbiz.model.list.LoadEvent
import com.rongtuoyouxuan.chatlive.crtbiz2.model.stream.RoomManagerMuteListBean
import com.rongtuoyouxuan.chatlive.crtbiz2.stream.StreamBiz
import com.rongtuoyouxuan.chatlive.crtnet.BaseModel
import com.rongtuoyouxuan.chatlive.crtnet.RequestListener
import com.rongtuoyouxuan.chatlive.crtutil.util.LaToastUtil
import com.rongtuoyouxuan.chatlive.crtuikit.viewmodel.BaseListFragmentViewModel

open class AnchorManagerMuteListViewModel(application: Application):
    BaseListFragmentViewModel<RoomManagerMuteListBean>(application) {

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
        StreamBiz.getRoomMuteList(null, sceneId, roomId, mPage, 30, object :
            RequestListener<RoomManagerMuteListBean> {
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
        StreamBiz.relieveRoomMute(request, object :
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