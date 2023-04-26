package com.rongtuoyouxuan.chatlive.stream.view.dialog

import android.app.Dialog
import android.content.Context
import com.rongtuoyouxuan.chatlive.stream.R
import android.os.Bundle
import android.view.*
import com.rongtuoyouxuan.chatlive.util.UIUtils
import kotlinx.android.synthetic.main.qf_stream_dialog_start_stream_level.*

class StartStreamLevelDialog: Dialog{


    private var mContext:Context? = null
    private var mHostLevelListener:HostLevelListener? = null
    private var streamId:String? = ""
    private var anchorId:String? = ""

    constructor(mContext: Context, mHostLevelListener:HostLevelListener):super(mContext, R.style.commenDialogStyle){
        this.mContext = mContext
        this.mHostLevelListener = mHostLevelListener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.qf_stream_dialog_start_stream_level)
        setWindowLocation()
        initView()
        initListener();
    }

    private fun setWindowLocation() {
        val win = this.window
        win?.decorView?.setPadding(0, 0, 0, 0)
        val lp = win?.attributes
        lp?.width = (UIUtils.screenWidth(mContext) * 0.8).toInt()
        lp?.height = WindowManager.LayoutParams.WRAP_CONTENT
        lp?.gravity = Gravity.CENTER
        win?.attributes = lp
        win?.decorView?.setBackgroundResource(R.drawable.corner_circle_white_bg)
//        win?.setWindowAnimations(R.style.CommonDialogStyleAnimation)
    }

    private fun initView() {
    }

    private fun initListener(){
        streamLiveInsApplyBtn.setOnClickListener {
            mHostLevelListener?.onApplyClick()
            dismiss()
        }
        streamLiveInsUnionBtn.setOnClickListener {
            mHostLevelListener?.onUnionClick()
            dismiss()
        }

        streamLiveInsCloseBtn.setOnClickListener {
            dismiss()
        }
    }

    fun setHostLevelListener(mHostLevelListener:HostLevelListener){
        this.mHostLevelListener = mHostLevelListener
    }

    interface HostLevelListener{
        fun onApplyClick()
        fun onUnionClick()
    }
}