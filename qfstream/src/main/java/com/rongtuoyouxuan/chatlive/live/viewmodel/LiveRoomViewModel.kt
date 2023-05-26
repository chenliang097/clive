package com.rongtuoyouxuan.chatlive.live.viewmodel

import android.content.Context
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.MutableLiveData
import com.rongtuoyouxuan.chatlive.crtutil.arch.LiveEvent
import com.rongtuoyouxuan.chatlive.crtbiz2.stream.StreamBiz
import com.rongtuoyouxuan.chatlive.live.view.ZegoLiveplay
import com.rongtuoyouxuan.chatlive.live.view.layout.BasePlayerView
import com.rongtuoyouxuan.chatlive.crtlog.upload.ULog
import com.rongtuoyouxuan.chatlive.crtnet.BaseModel
import com.rongtuoyouxuan.chatlive.crtnet.RequestListener

class LiveRoomViewModel(liveStreamInfo: com.rongtuoyouxuan.chatlive.base.utils.LiveStreamInfo) : com.rongtuoyouxuan.chatlive.base.viewmodel.LiveBaseViewModel(liveStreamInfo) {

    var linkMicPushStreamLiveData = MutableLiveData<Boolean>()//观众推流成功
    var anchorMixStreamSucLiveData = MutableLiveData<Int>()//主播混流成功
    var exitLinkMicLiveData = MutableLiveData<Int>()//被踢下麦/主动下麦
    var audienceStreamIdLinkMicLiveData = MutableLiveData<Long>()//观众推流id
    var audienceStopLinkMicLiveData = MutableLiveData<Long>()//观众停止连麦
    var audienceGoneLinkMicImgLiveData = MutableLiveData<Boolean>()//观众隐藏直播连麦按钮
    var liveEndLiveData = MutableLiveData<Boolean>()//观众隐藏直播连麦按钮

    //播放错误
    val playerError = LiveEvent<Void>()
    //播放完成
    val playerComplete = LiveEvent<Void>()
    private val handler = Handler(Looper.getMainLooper())
    var liveStatus = ""

    fun onCreate(basePlayerView: BasePlayerView?, activity:Context, isScroll:Boolean) {
        /** 初始化拉流  */
        ZegoLiveplay.instance.onCreate(basePlayerView, application, activity, isScroll)
    }

    fun audienceExitRoom(type:Int){
        var linkId = audienceStreamIdLinkMicLiveData.value
        if(linkId == null || type == 1){
            linkId = 0
        }
        StreamBiz.audienceExitRoom(streamId, "0", object :
            RequestListener<BaseModel> {
            override fun onSuccess(reqId: String?, result: BaseModel?) {
                liveEndLiveData.value = true
            }

            override fun onFailure(reqId: String?, errCode: String?, msg: String?) {
                ULog.d("clll", "audienceExitRoom:onFailure")
            }

        })
    }


    fun preparePlayer(baseUrl: String, authParams: String, streamId: String, roomId: String) {
        ZegoLiveplay.instance.preparePlayer(baseUrl, authParams, streamId, roomId)
        ZegoLiveplay.instance.updateAnchorId(anchorId)
    }

    fun setStatus(liveStatus:String){
        this.liveStatus = liveStatus
       ZegoLiveplay.instance.setStatus(liveStatus)
    }

    fun onDestroy(roomId: String, streamId: String) {
        ZegoLiveplay.instance.onDestroy(roomId, streamId)
    }

    fun onStop() {}

    override fun onCleared() {
        super.onCleared()
        handler.removeCallbacksAndMessages(null)
    }
}