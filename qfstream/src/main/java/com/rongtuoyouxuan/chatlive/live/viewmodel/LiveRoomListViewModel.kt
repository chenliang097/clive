package com.rongtuoyouxuan.chatlive.live.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.rongtuoyouxuan.chatlive.biz2.model.stream.LiveRoomListBean
import com.rongtuoyouxuan.chatlive.biz2.stream.StreamBiz
import com.rongtuoyouxuan.chatlive.net2.RequestListener

class LiveRoomListViewModel(application: Application) : AndroidViewModel(application) {
    val liveVM: MutableLiveData<LiveRoomListBean> = MutableLiveData()

    fun getLiveRoomLists(userId:String, secenId:String) {
        StreamBiz.getLiveRoomLists(userId, secenId, object : RequestListener<LiveRoomListBean>{
            override fun onSuccess(reqId: String?, result: LiveRoomListBean?) {
                liveVM.value = result
            }

            override fun onFailure(reqId: String?, errCode: String?, msg: String?) {
                try {
                    var model = LiveRoomListBean()
                    model.errCode = errCode?.toInt()!!
                    model.errMsg = msg
                    liveVM.value = model
                }catch (e:Exception){
                    e.stackTrace
                }
            }

        })
    }
}