package com.rongtuoyouxuan.chatlive.live.view

import android.app.Application
import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.blankj.utilcode.util.ToastUtils
import com.rongtuoyouxuan.chatlive.base.utils.ViewModelUtils
import com.rongtuoyouxuan.chatlive.base.viewmodel.IMLiveViewModel
import com.rongtuoyouxuan.chatlive.databus.DataBus
import com.rongtuoyouxuan.chatlive.live.view.layout.BasePlayerView
import com.rongtuoyouxuan.chatlive.live.viewmodel.LiveRoomViewModel
import com.rongtuoyouxuan.chatlive.log.upload.ULog
import com.rongtuoyouxuan.chatlive.stream.R
import com.rongtuoyouxuan.qfcommon.eventbus.MLiveEventBus
import com.rongtuoyouxuan.qfzego.KeyCenter
import im.zego.zegoexpress.ZegoExpressEngine
import im.zego.zegoexpress.callback.IZegoEventHandler
import im.zego.zegoexpress.callback.IZegoRoomLoginCallback
import im.zego.zegoexpress.constants.*
import im.zego.zegoexpress.entity.*
import org.json.JSONObject

open class ZegoLiveplay {

    private val TAG = "ZegoLiveplay"
    private var basePlayerView: BasePlayerView? = null
    private var mZegoExpressEngine: ZegoExpressEngine? = null
    private var mZegoPlayerConfig: ZegoPlayerConfig? = null
    private var streamId1 = ""
    private var roomId = ""
    private var baseUrl = ""
    private var authParams = ""
    private var liveStatus = ""
    private var anchorId = ""
    private var mOnLinkMicPublishListener:OnLinkMicPublishListener? = null
    private var liveRoomViewModel: LiveRoomViewModel? = null
    private var imLiveViewModel: IMLiveViewModel? = null
    var zegoCanvas:ZegoCanvas? = null
    private var retryCount = 0
    private var isFirstEnter = true


    companion object {
        var STREAM_ID = "stream_id"
        var ROOM_ID = "room_id"
        var ANCHORID = "anchor_id"
        var BASE_URL = "base_url"
        var AUTH_PARAMS = "auth_params"
        var FROAM_SOURCE = "from_source"
        var STREAM_TYPE = "stream_type"
        val instance by lazy { ZegoLiveplay() }
        private const val MAX_RETRY = 10

    }

    private val mIZegoEventHandler: IZegoEventHandler = object : IZegoEventHandler() {
        override fun onPlayerStateUpdate(streamID: String, state: ZegoPlayerState, errorCode: Int, jsonObject: JSONObject) {
            super.onPlayerStateUpdate(streamID, state, errorCode, jsonObject)
            if (errorCode != 0 && state == ZegoPlayerState.NO_PLAY) {
                if(errorCode == 1002050){
                    imLiveViewModel?.zegoUserRepeatLiveData?.value = true
                }
                if (retryCount < MAX_RETRY) {
                    retryCount++
                    mZegoExpressEngine?.startPlayingStream(streamId1, zegoCanvas, mZegoPlayerConfig)
                }else{
                    mZegoExpressEngine?.stopPlayingStream(streamID)
                }
//                ToastUtils.showLong(R.string.stream_live_fail)
            } else {
                if(errorCode == 0 && state == ZegoPlayerState.PLAYING){
                    if(isFirstEnter) {
                        ZegoLiveplay.instance.setupVideo?.setupRemoteVideo(basePlayerView)
                        isFirstEnter = false
                    }
                }

            }
//            if(errorCode == 1004020){
//                if (retryCount < MAX_RETRY) {
//                    retryCount++
//                }else{
//                    mZegoExpressEngine?.stopPlayingStream(streamID)
//                }
//            }
            liveRoomViewModel?.playerComplete?.value = null
            ULog.d("clll", "streamId:$streamID--errodCode：--$errorCode +---状态： ${state.value()}")
        }

        override fun onRoomStateChanged(roomID: String, reason: ZegoRoomStateChangedReason, errorCode: Int, extendedData: JSONObject) {
            ULog.d(TAG, "mIZegoEventHandler:roomID:$roomID-errorCode:$errorCode")
        }

        override fun onPublisherStateUpdate(
            streamID: String?,
            state: ZegoPublisherState?,
            errorCode: Int,
            p3: JSONObject?
        ) {
            super.onPublisherStateUpdate(streamID, state, errorCode, p3)
            if (state == ZegoPublisherState.PUBLISHING) {
//                mOnLinkMicPublishListener?.onPublishSuc()
                liveRoomViewModel?.linkMicPushStreamLiveData?.value = true
                ULog.d(TAG, "mIZegoEventHandler:" + "onPublisherStateUpdate:开始推流")
            } else if (state == ZegoPublisherState.NO_PUBLISH) {
//                mOnLinkMicPublishListener?.onPublishFail()
                if(errorCode != 0){
                    liveRoomViewModel?.linkMicPushStreamLiveData?.value = false
                    ULog.d(TAG, "mIZegoEventHandler:" + "onPublisherStateUpdate:推流失败")
                }else{
                    ULog.d(TAG, "mIZegoEventHandler:" + "onPublisherStateUpdate:停止推流")
                }

            }
            ULog.d("clll", "onPublisherStateUpdate:streamId:$streamID--errodCode：--$errorCode +---状态： ${state?.value()}")

        }

        override fun onPublisherRelayCDNStateUpdate(
            streamID: String,
            arrayList: ArrayList<ZegoStreamRelayCDNInfo?>?
        ) {
            super.onPublisherRelayCDNStateUpdate(streamID, arrayList)
            ULog.d(TAG, "onPublisherRelayCDNStateUpdate:streamID:$streamID")
        }
    }

    fun preparePlayer(baseUrl: String, authParams: String, streamId: String, roomId: String) {
        this.baseUrl = baseUrl
        this.authParams = authParams
        this.streamId1 = streamId
        this.roomId = roomId
    }

    fun updateAnchorId(anchorId:String){
        this.anchorId = anchorId
    }

    fun onCreate(basePlayerView: BasePlayerView?, application:Application, activity: Context, isScroll:Boolean){
        this.basePlayerView = basePlayerView
        val profile = ZegoEngineProfile()
        profile.appID = KeyCenter.getInstance().appID
        profile.appSign = KeyCenter.getInstance().appSign
        profile.scenario = ZegoScenario.BROADCAST
        profile.application = application
//        val config = ZegoCDNConfig()
//        config.url = baseUrl
//        config.authParam = authParams
        mZegoPlayerConfig = ZegoPlayerConfig()
        mZegoPlayerConfig?.resourceMode = ZegoStreamResourceMode.ONLY_CDN
//        mZegoPlayerConfig!!.cdnConfig = config
        zegoCanvas = ZegoCanvas(basePlayerView)
        zegoCanvas?.viewMode = ZegoViewMode.ASPECT_FILL
        mZegoExpressEngine = ZegoExpressEngine.createEngine(profile, mIZegoEventHandler)
//        mZegoExpressEngine?.setVideoMirrorMode(ZegoVideoMirrorMode.ONLY_PUBLISH_MIRROR)
        val zegoUser = ZegoUser(DataBus.instance().USER_ID)
        mZegoExpressEngine?.startEffectsEnv();
        if(isScroll || isResetOpen){
            mZegoExpressEngine?.logoutRoom()
            isResetOpen = false
        }
        mZegoExpressEngine?.loginRoom(roomId, zegoUser, null, IZegoRoomLoginCallback { code, _ ->
            ULog.d("clll", "onRoomLoginResult:$code")
            if (code == 0) {
            }
        })
        mZegoExpressEngine?.startPlayingStream(streamId1, zegoCanvas, mZegoPlayerConfig)
        initObserver(activity)
    }

    fun initObserver(activity: Context){
        liveRoomViewModel = ViewModelUtils.getLive(LiveRoomViewModel::class.java)
        imLiveViewModel = ViewModelUtils.getLive(IMLiveViewModel::class.java)

        liveRoomViewModel?.anchorMixStreamSucLiveData?.observe(activity as LifecycleOwner
        ) {
//            LaToastUtil.showShort("拉cdn转rtc")
            mZegoExpressEngine?.stopPlayingStream(streamId1)
            mZegoExpressEngine?.startPlayingStream(streamId1, zegoCanvas)
        }
        liveRoomViewModel?.exitLinkMicLiveData?.observe(activity as LifecycleOwner
        ) {
//            LaToastUtil.showShort("-----拉RTC转CDN")
            mZegoExpressEngine?.stopPlayingStream(streamId1)
//            val zegoCanvas1 = ZegoCanvas(basePlayerView)
//            zegoCanvas1.viewMode = ZegoViewMode.SCALE_TO_FILL
            mZegoExpressEngine?.startPlayingStream(streamId1, zegoCanvas, mZegoPlayerConfig)
        }
    }

//    public fun playStream(playerView:BasePlayerView, streamId: String?){
//        val zegoCanvas = ZegoCanvas(playerView)
//        zegoCanvas.viewMode = ZegoViewMode.SCALE_TO_FILL
//        ZegoExpressEngine.getEngine().startPlayingStream(streamId,zegoCanvas)
//    }

    public fun playCDNStream(){
//        LaToastUtil.showShort("-----拉RTC转CDN")
        mZegoExpressEngine?.stopPlayingStream(streamId1)
//            val zegoCanvas1 = ZegoCanvas(basePlayerView)
//            zegoCanvas1.viewMode = ZegoViewMode.SCALE_TO_FILL
        mZegoExpressEngine?.startPlayingStream(streamId1, zegoCanvas, mZegoPlayerConfig)
    }

    fun getPlayerView(): BasePlayerView? {
        return basePlayerView
    }

    fun setStatus(liveStatus:String){
        this.liveStatus = liveStatus
    }

    interface ISetupVideo {
        fun setupRemoteVideo(view: BasePlayerView?)
    }

    public var setupVideo: ISetupVideo? = null

    fun setISetupVideo(setupVideo: ISetupVideo) {
        this.setupVideo = setupVideo
    }

    fun onDestroy(roomIdPre: String, streamIdPre: String) {
        ULog.d("clll", "zegoliveplay----------onDestroy--$streamId1")
        if (liveStatus == "floatWindow"){
            ULog.d("clll", "floatWindow")
        }else{
            if (mZegoExpressEngine != null && streamIdPre == streamId1) {
                mZegoExpressEngine?.logoutRoom(roomId)
                mZegoExpressEngine?.stopPlayingStream(streamId1)
                ZegoExpressEngine.destroyEngine(null)
            }else{
                if(mZegoExpressEngine != null ) {
                    mZegoExpressEngine?.logoutRoom(roomIdPre)
                    mZegoExpressEngine?.stopPlayingStream(streamIdPre)
                }
            }

            ULog.d("clll", "zegoliveplay----------onDestroy1")
        }
        isFirstEnter = true
    }

    var isResetOpen = false
    fun onIsResetOpen(isResetOpen: Boolean){
        this.isResetOpen = isResetOpen
    }

    fun onFloatViewDestroy(){
        if (mZegoExpressEngine != null) {
            mZegoExpressEngine?.logoutRoom(streamId1)
            mZegoExpressEngine?.stopPlayingStream(streamId1)
        }
        ZegoExpressEngine.destroyEngine(null)
        if(imLiveViewModel != null){
            imLiveViewModel?.onDestroy()
        }
        isFirstEnter = true
    }



    interface OnLinkMicPublishListener{
        fun onPublishSuc()
        fun onPublishFail()
    }


}