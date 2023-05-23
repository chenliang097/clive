package com.rongtuoyouxuan.chatlive.stream.viewmodel

import android.hardware.Camera
import android.net.TrafficStats
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.os.Process
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rongtuoyouxuan.chatlive.arch.LiveEvent
import com.rongtuoyouxuan.chatlive.base.utils.LiveStreamInfo
import com.rongtuoyouxuan.chatlive.base.viewmodel.LiveBaseViewModel
import com.rongtuoyouxuan.chatlive.biz2.model.stream.*
import com.rongtuoyouxuan.chatlive.biz2.model.stream.StreamStartInfoRequest
import com.rongtuoyouxuan.chatlive.biz2.stream.StreamBiz
import com.rongtuoyouxuan.chatlive.databus.DataBus
import com.rongtuoyouxuan.chatlive.log.PLog
import com.rongtuoyouxuan.chatlive.log.upload.ULog
import com.rongtuoyouxuan.chatlive.net2.BaseModel
import com.rongtuoyouxuan.chatlive.net2.RequestListener
import com.rongtuoyouxuan.chatlive.streaming.BaseStreamView
import com.rongtuoyouxuan.chatlive.streaming.IBaseStreaming.StreamNetworkSpeedListener
import com.rongtuoyouxuan.chatlive.streaming.IBaseStreaming.StreamStateChangedListener
import com.rongtuoyouxuan.chatlive.streaming.Sdk.InitParams
import com.rongtuoyouxuan.chatlive.streaming.Sdk.StreamApi
import com.rongtuoyouxuan.chatlive.stream.view.layout.StreamPreviewLayout
import java.io.File
import java.lang.ref.WeakReference

class StreamViewModel(liveStreamInfo: LiveStreamInfo) : LiveBaseViewModel(liveStreamInfo), StreamStateChangedListener, StreamNetworkSpeedListener {

    private val logs = StringBuilder()
    private val TAG = "StreamViewModel"
    var mirrorEvent = LiveEvent(true)
    var cameraId = MutableLiveData<Int>()
    @JvmField
    var muteEvent = LiveEvent(false)
    var netSpeed = MutableLiveData<Int>() //网速
    var playFailLiveEvent = LiveEvent<String>()//主播端拉连麦用户的流 失败
    var playSuccessLiveEvent = LiveEvent<String>()//主播端拉连麦用户的流 成功
    var showReconnectEvent = LiveEvent("")
    var showForbidRoomEvent = LiveEvent<Void>()
    var hasStartStreamEvent = LiveEvent(false)
    @JvmField
    var startStreamEvent = LiveEvent<Void>()
    @JvmField
    var showToast = LiveEvent<Int>()
    @JvmField
    var startStreamModel = LiveEvent<StartStreamInfoBean>() //开播数据
    var isPreviewVisible = MutableLiveData<Boolean>()
    var closeActivityLiveData = MutableLiveData<Boolean>()
    var pushStreamStatusLiveData = MutableLiveData<Int>()
    var pushStreamHeartBeatLiveData = MutableLiveData<StreamHeartBeatBean>()
    var startPushStreamInfoLiveData = LiveEvent<StartStreamInfoBean>() //开播数据


    var setCover = LiveEvent<Void>()
    var mParams: InitParams? = null
    private var mStreamApi: StreamApi? = null
    var startStreamTime: Long = 0
    private val mAppDataFilePath: String
    private var workThread: WorkThread? = null

    private val mHandler: Handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                NETSPEED_MSG -> onNetworkSpeed(msg.arg1)
            }
            super.handleMessage(msg)
        }
    }

    companion object {
        private const val NETSPEED_MSG = 100
        var CAMERA_BACK = Camera.CameraInfo.CAMERA_FACING_BACK
        var CAMERA_FRONT = Camera.CameraInfo.CAMERA_FACING_FRONT
    }

    init {
//        cameraId.value = CAMERA_FRONT
        netSpeed.value = 0
        isPreviewVisible.value = true
        mAppDataFilePath = application.applicationContext.getExternalFilesDir(null).toString() + File.separator
    }

    fun onCreate() {
        if (mStreamApi == null) {
            return
        }
        try {
            addUploadCrashLogs("StreamViewModel onCreate")
            mStreamApi!!.onCreate()
        } catch (e: Exception) {
            uploadExceptionAndLog(e)
            throw e
        }
    }

    private fun startStreaming(streamId:String, token:String, roomId:String) {
        if (mStreamApi == null) {
            return
        }
        mStreamApi!!.setPushUrl(publishUrl)
        mStreamApi?.setStreamId(streamId)
        mStreamApi?.setRoomInfo(roomId, token, anchorId);
//        publishUrl = rtmpAddress
        try {
            mStreamApi!!.startStreaming()
        } catch (e: Exception) {
            uploadExceptionAndLog(e)
            throw e
        }
        if (startStreamTime == 0L) {
            startStreamTime = System.currentTimeMillis()
            hasStartStreamEvent.value = true
        }
        mHandler.postDelayed({ mStreamApi!!.setEncodingMirror(mirrorEvent.value) }, 1000)
        if (workThread == null) {
            workThread = WorkThread(mHandler)
            workThread!!.start()
        }
    }

    fun resetStreamApi(streamApi: BaseStreamView?) {
        if (streamApi == null) {
            return
        }
        addUploadCrashLogs("StreamViewModel resetStreamApi")
        mStreamApi = streamApi
        mParams = streamApi.initParams
        mStreamApi?.setStreamNetworkSpeedListener(this)
        mStreamApi?.setStreamStateListener(this)
    }

    //静音开关
    fun mute() {
        if (mStreamApi == null) {
            return
        }
        val mute = !muteEvent.value
        muteEvent.value = mute
        mStreamApi!!.mute(mute)
    }

    override fun onPlaySuccess(streamId: String) {
        playSuccessLiveEvent.value = streamId
    }

    override fun onPlayError() {
        playFailLiveEvent.postCall()
    }


    override fun onNetworkSpeed(`val`: Int) {
        netSpeed.postValue(`val`)

    }

    val networkSpeed: LiveData<Int> get() = netSpeed

    private fun uploadExceptionAndLog(e: Exception) {
        try {
            val logsStr = crashLogs
            val exception: Exception = RuntimeException(
                """${e.message}$logsStr""".trimIndent(), e)
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
    }

    fun restart() {
        if (mStreamApi == null) {
            return
        }
        mStreamApi!!.startStreaming()
    }

    fun takeScreenShot() {
        if (mStreamApi == null) {
            return
        }
    }

    fun setEncodingSize(width: Int, height: Int) {
        if (mStreamApi == null) {
            return
        }
        mStreamApi!!.setEncodingSize(width, height)
    }

    fun getStreamRoomInfo(userId: String?, userName: String?) {
        StreamBiz.startlive(null, userId, userName, object : RequestListener<StartStreamInfoBean?> {
            override fun onFailure(reqId: String, errCode: String, msg: String) {
            }

            override fun onSuccess(reqId: String?, result: StartStreamInfoBean?) {
                startPushStreamInfoLiveData.value = result
            }
        })
    }

    fun startRequestPullUrl(title: String?, coverImg: String?, longitude:Double?, latitude:Double?, location: String?, userId: String?, userName: String?, locationSwitch: Boolean?, isFrontCamera: Boolean?) {
        PLog.d(TAG, "startRequestPullUrl")
        streamType = StreamPreviewLayout.TYPE_LIVE
        var request = StartPushStreamRequest(coverImg, title, location, longitude, latitude,locationSwitch, isFrontCamera)
        StreamBiz.startlive(null, userId, userName, object : RequestListener<StartStreamInfoBean?> {
            override fun onFailure(reqId: String, errCode: String, msg: String) {
                showReconnectEvent.value = msg
            }

            override fun onSuccess(reqId: String?, result: StartStreamInfoBean?) {
                if (result != null) {
                    uploadAnchorInfo(result, request)
                }
            }
        })
    }

    fun uploadAnchorInfo(startStreamBean: StartStreamInfoBean, request:StartPushStreamRequest) {
        PLog.d(TAG, "startRequestPullUrl")
        streamType = StreamPreviewLayout.TYPE_LIVE
        var startPushStreamRequest = request
        startPushStreamRequest.anchor_id_str = startStreamBean.data.anchor_id.toString()
        startPushStreamRequest.scene_id_str = startStreamBean.data.scene_id_str
        startPushStreamRequest.room_id_str = startStreamBean.data.room_id_str
        StreamBiz.uploadAnchorInfo(startPushStreamRequest, object : RequestListener<BaseModel> {
            override fun onFailure(reqId: String, errCode: String, msg: String) {
                showReconnectEvent.value = msg
            }

            override fun onSuccess(reqId: String?, result: BaseModel?) {
                startStreamModel.value = startStreamBean
                startStreamEvent.postCall()
                publishUrl = startStreamBean?.data?.cdn_url
                streamId = startStreamBean?.data?.stream_id
                streamToken = startStreamBean?.data?.token
//                    streamToken = KeyCenter.getInstance()._token
                anchorId = startStreamBean?.data?.anchor_id.toString()
                startStreamBean?.data?.room_id_str?.let { startStreaming(streamId, streamToken, it) }
            }
        })
    }

    fun stopStreaming() {
        if (mStreamApi == null) {
            return
        }
        mStreamApi!!.stopStreaming()
    }

    fun startStreaming() {
        if (mStreamApi == null) {
            return
        }
        mStreamApi!!.setPushUrl(publishUrl)
        mStreamApi!!.setStreamId(streamId)
        mStreamApi?.setRoomInfo(streamId, streamToken, DataBus.instance().uid)
        mStreamApi!!.startStreaming()
    }

    fun setFps(fps: Int) {
        if (mStreamApi == null) {
            return
        }
        mStreamApi!!.setFps(fps)
    }

    fun setBitrate(bitrate: Int) {
        if (mStreamApi == null) {
            return
        }
        mStreamApi!!.setBitrate(bitrate)
    }

    fun switchCamera() {
        if (mStreamApi == null) {
            return
        }
        if (cameraId.value == CAMERA_FRONT){
            mStreamApi?.setUseFrontCamera(true)
            onSwitchCameraEnd(CAMERA_FRONT)
        }else{
            mStreamApi?.setUseFrontCamera(false)
            onSwitchCameraEnd(CAMERA_BACK)
        }
        mStreamApi?.switchCamera()
    }

    public fun onSwitchCameraEnd(cameraId:Int) {
        if (mStreamApi == null) {
            return;
        }
        this.cameraId.value = cameraId
    }

//    fun setUseFrontCamera(useFrontCamera: Boolean) {
//        if (mStreamApi == null) {
//            return
//        }
//        mStreamApi!!.setUseFrontCamera(useFrontCamera)
//    }

    fun removePrePublishCdnUrl(){
        if (mStreamApi == null) {
            return
        }
        mStreamApi!!.removePublishCdnUrl()
    }

    fun onRestart() {
        if (mStreamApi == null) {
            return
        }
        mStreamApi!!.onRestart()
    }

    fun onResume() {
        if (mStreamApi == null) {
            return
        }
        mStreamApi!!.onResume()
    }

    fun onPause() {
        if (mStreamApi == null) {
            return
        }
        mStreamApi!!.onPause()
    }

    private class WorkThread internal constructor(handler: Handler) : Thread() {
        private val TAG = "WorkThread"
        private var mLastSendData: Long = 0
        private var mRunnable: Runnable? = null
        private var workHandler: Handler? = null
        private val mainHandler: WeakReference<Handler>
        override fun run() {
            this.name = TAG
            Looper.prepare()
            workHandler = Handler()
            mLastSendData = TrafficStats.getUidTxBytes(Process.myUid())
            mRunnable = Runnable {
                val sendData = TrafficStats.getUidTxBytes(Process.myUid()) - mLastSendData
                mLastSendData = TrafficStats.getUidTxBytes(Process.myUid())
                val speed = sendData / 1024 / 2
                val theHandler = mainHandler.get()
                if (null == theHandler) {
                    quit()
                    return@Runnable
                }
                theHandler.obtainMessage(NETSPEED_MSG, speed.toInt(), 0).sendToTarget()
                workHandler!!.postDelayed(mRunnable!!, 2000)
            }
            workHandler!!.postDelayed(mRunnable!!, 2000)
            Looper.loop()
        }

        fun quit() {
            if (null != workHandler) {
                workHandler!!.looper.quit()
            }
        }

        init {
            mainHandler = WeakReference(handler)
        }
    }

    override fun onCleared() {
        if (workThread != null) {
            workThread!!.quit()
            workThread = null
        }
        super.onCleared()
    }

    fun finishActivity() {
        closeActivityLiveData.value = true
    }

    fun addUploadCrashLogs(str: String?) {
        PLog.d(TAG, str!!)
        logs.append("\n")
        logs.append(str)
    }

    private val crashLogs: String private get() {
        try {
            return logs.toString()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }

    fun onDestroy() {
        if (mStreamApi == null) {
            return
        }
        mStreamApi!!.onDestroy()
    }

    override fun getPushStreamInfo(logs: String?) {
//        if (logs != null) {
//            ULog.d("clll", logs)
//            val logsAppend = java.lang.StringBuilder()
//            logsAppend.append("userId:" + DataBus.instance().uid + "\n")
//            logsAppend.append(logs)
//            StreamBiz.uploadPushStreamInfo(logsAppend.toString(),object :RequestListener<BaseModel>{
//                override fun onSuccess(reqId: String?, result: BaseModel?) {
//                }
//
//                override fun onFailure(reqId: String?, errCode: String?, msg: String?) {
//                }
//
//            })
//        }
    }

    override fun pushStreamHeartbeat(id: String, msg:String) {
        var bean = startStreamModel.value
        StreamBiz.pushStreamHeartbeat(bean.data.room_id_str, bean.data.scene_id_str, DataBus.instance().USER_ID, object :RequestListener<StreamHeartBeatBean>{
            override fun onSuccess(reqId: String?, result: StreamHeartBeatBean?) {
                pushStreamHeartBeatLiveData.value = result!!
//                ULog.d("clll", "pushStreamHeartbeat----")

            }

            override fun onFailure(reqId: String?, errCode: String?, msg: String?) {
//                ULog.d("clll", "pushStreamHeartbeat1----")
            }

        })
    }

    override fun pushStreamStatus(id: String?) {
        pushStreamStatusLiveData.value = 1
    }
}