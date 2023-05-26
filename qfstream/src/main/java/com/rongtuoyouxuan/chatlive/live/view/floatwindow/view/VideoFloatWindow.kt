package com.rongtuoyouxuan.chatlive.live.view.floatwindow.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.rongtuoyouxuan.chatlive.crtbiz2.model.im.msg.cmdMsg.LiveEndMsg
import com.rongtuoyouxuan.chatlive.live.view.ZegoLiveplay
import com.rongtuoyouxuan.chatlive.live.view.floatwindow.FloatWindowManager
import com.rongtuoyouxuan.chatlive.live.view.layout.BasePlayerView
import com.rongtuoyouxuan.chatlive.stream.R
import kotlinx.android.synthetic.main.qf_stream_layout_float_window.view.*

@SuppressLint("ViewConstructor")
class VideoFloatWindow(context: Context,
                       param: Intent,
                       private val exitFloatWindow: (() -> Unit))
    : AbstractFloatWindowView(context, param, exitFloatWindow) {
    companion object {
        private const val TAG = "VideoFloatWindow"
    }

    private var contentWrapper: ViewGroup
    private val setupVideo = object : ZegoLiveplay.ISetupVideo {

        override fun setupRemoteVideo(view: BasePlayerView?) {
            setVideoPlayerView(view)
        }
    }

    init {
        View.inflate(context, R.layout.qf_stream_layout_float_window, this)
        contentWrapper = findViewById(R.id.cardView)

        ZegoLiveplay.instance.setISetupVideo(setupVideo)
        setVideoPlayerView(ZegoLiveplay.instance.getPlayerView())
        setOnClickListener { onClick() }
        floatWindowColseImg.setOnClickListener {
            ZegoLiveplay.instance.onFloatViewDestroy()
            FloatWindowManager.removeFloatWindowView()
            exit()
        }
    }

    var liveEndObserver:Observer<LiveEndMsg> = Observer {
        ZegoLiveplay.instance.onFloatViewDestroy()
        FloatWindowManager.removeFloatWindowView()
        exit()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
//        IMSocketImpl.getInstance().chatRoom(streamId).liveEndCallback.observe(liveEndObserver)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
//        IMSocketImpl.getInstance().chatRoom(streamId).liveEndCallback.removeObserver(liveEndObserver)
    }

    override fun getLogTag() = TAG

    private fun setVideoPlayerView(videoView: BasePlayerView?) {
        videoView?.apply {
            if (parent != null) {
                (parent as ViewGroup).removeView(this)
            }
            contentWrapper.addView(this)
        }
    }

    fun onClick() {
        exit()
        // TODO: 直播界面
//        Router.toLiveRoomActivity(streamId, anchorId, ISource.FROM_LIVE_ROOM, true)
    }
}