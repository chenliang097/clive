package com.rongtuoyouxuan.chatlive.stream.view.layout

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.rongtuoyouxuan.chatlive.stream.R
import com.rongtuoyouxuan.chatlive.rtstream.qfzego.ZegoStreamView
import kotlinx.android.synthetic.main.qf_stream_layout_game_and_stream.view.*

class StreamAndOtherView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : FrameLayout(context, attrs, defStyleAttr) {

    private var zegoStreamView: ZegoStreamView? = null

    init {
        View.inflate(context, R.layout.qf_stream_layout_game_and_stream, this)
    }

    fun setZegoView(zegoStreamView1: ZegoStreamView){
        if (zegoStreamView1?.parent != null) {
            (zegoStreamView1?.parent as ViewGroup).removeView(zegoStreamView1)
        }
        this.zegoStreamView = zegoStreamView1
        zegoStreamFrameLayout.removeAllViews()
        zegoStreamFrameLayout.addView(zegoStreamView1)
    }
}