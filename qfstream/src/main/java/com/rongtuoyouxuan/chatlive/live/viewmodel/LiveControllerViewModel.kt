package com.rongtuoyouxuan.chatlive.live.viewmodel

import androidx.lifecycle.MutableLiveData
import com.rongtuoyouxuan.chatlive.arch.LiveEvent
import com.rongtuoyouxuan.chatlive.base.utils.LiveStreamInfo
import com.rongtuoyouxuan.chatlive.base.viewmodel.ControllerViewModel
import com.rongtuoyouxuan.chatlive.biz2.model.stream.EnterRoomBean
import com.rongtuoyouxuan.chatlive.biz2.model.stream.RoomInfoExtraBean
import com.rongtuoyouxuan.chatlive.biz2.stream.StreamBiz
import com.rongtuoyouxuan.chatlive.net2.RequestListener

class LiveControllerViewModel(liveStreamInfo: LiveStreamInfo) :
    ControllerViewModel(liveStreamInfo) {
    var mGiftDialog = LiveEvent<Void>()
    var fromSource: String? = null
    var roomInfoLiveData = MutableLiveData<EnterRoomBean?>() //房间信息
    var roomInfoExtraLiveData = MutableLiveData<RoomInfoExtraBean?>() //房间额外信息

    /*

    */
    fun getRoomInfo(roomId:String, sceneId:String, userId:String, is_login:Boolean ){
        StreamBiz.getRoomInfo(roomId, sceneId, userId, object :
            RequestListener<EnterRoomBean> {
            override fun onSuccess(reqId: String?, result: EnterRoomBean?) {
                roomInfoLiveData.value = result
            }

            override fun onFailure(reqId: String?, errCode: String?, msg: String?) {
                var enterRoomBean = EnterRoomBean()
                enterRoomBean.errCode = errCode?.toInt()!!
                enterRoomBean.errMsg = msg
                roomInfoLiveData.value = enterRoomBean
            }

        })
    }

    /*
     */
    fun getRoomInfoExtra(roomId:String, sceneId:String, userId:String, is_login:Boolean ){
        StreamBiz.getRoomInfoExtra(roomId, sceneId, userId, object :
            RequestListener<RoomInfoExtraBean> {
            override fun onSuccess(reqId: String?, result: RoomInfoExtraBean?) {
                roomInfoExtraLiveData.value = result
            }

            override fun onFailure(reqId: String?, errCode: String?, msg: String?) {
                var roomInfoExtraBean = RoomInfoExtraBean()
                roomInfoExtraBean.errCode = errCode?.toInt()!!
                roomInfoExtraBean.errMsg = msg
                roomInfoExtraLiveData.value = roomInfoExtraBean
            }

        })
    }
}