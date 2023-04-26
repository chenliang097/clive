package com.rongtuoyouxuan.chatlive.base.view.activity

import android.os.Bundle
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import com.rongtuoyouxuan.chatlive.base.utils.LiveStreamInfo
import com.rongtuoyouxuan.libuikit.LanguageActivity
import com.rongtuoyouxuan.libuikit.TransferLoadingUtil

open class BaseLiveStreamActivity : LanguageActivity() {
    public var liveStreamInfo:LiveStreamInfo?= LiveStreamInfo()
    public var anchorId:String? = ""
    public var streamID:String? = ""
    public var streamType:String? = ""
    public var roomID:String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        liveStreamInfo?.application = application
    }

    fun updateLiveRoomID(roomId:String){
        liveStreamInfo?.roomId = roomId
        this.roomID = liveStreamInfo?.roomId
    }

    fun updateLiveStreamID(streamId:String){
        liveStreamInfo?.streamId = streamId
        this.streamID = liveStreamInfo?.streamId
    }

    fun updateAnchorId(anchorId:String){
        liveStreamInfo?.anchorId = anchorId
        this.anchorId = liveStreamInfo?.anchorId
    }

    fun updateStreamType(streamType: String?) {
        liveStreamInfo?.streamType = streamType
        this.streamType = liveStreamInfo?.streamType
    }

    protected open fun showDialogLoading() {
        TransferLoadingUtil.showDialogLoading(this)
    }

    protected open fun dismissDialogLoading() {
        TransferLoadingUtil.dismissDialogLoading(this)
    }

    protected open fun <T : AndroidViewModel?> getViewModel(tClass: Class<T>): T {
        return ViewModelProvider(this).get(tClass)
    }
}