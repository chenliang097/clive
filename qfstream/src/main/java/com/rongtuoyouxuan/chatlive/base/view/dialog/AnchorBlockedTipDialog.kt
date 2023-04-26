package com.rongtuoyouxuan.chatlive.base.view.dialog

import android.app.Dialog
import android.content.Context
import com.rongtuoyouxuan.chatlive.stream.R
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.*
import com.rongtuoyouxuan.chatlive.util.UIUtils
import kotlinx.android.synthetic.main.qf_stream_dialog_anchor_blocked.*

class AnchorBlockedTipDialog: Dialog{

    private var mContext:Context? = null
    private var mAnchorBlockedTipDialogListener:AnchorBlockedTipDialogListener? = null
    private var type:Int? = 1

    constructor(mContext: Context, type:Int?,  mAnchorBlockedTipDialogListener:AnchorBlockedTipDialogListener):super(mContext, R.style.commenDialogStyle){
        this.mContext = mContext
        this.mAnchorBlockedTipDialogListener = mAnchorBlockedTipDialogListener
        this.type = type
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.qf_stream_dialog_anchor_blocked)
        setWindowLocation()
        setCanceledOnTouchOutside(false)
        initView()
        initListener()
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
        when(type){
            1->{
                anchorBlockDialogContent.text = context.getString(R.string.stream_anchor_block_content_1)
                anchorBlockDialogContent1.text = context.getString(R.string.stream_anchor_block_content_2)
                anchorBlockDialogBtn.text = context.getString(R.string.stream_anchor_block_btn)
                5?.let {
                    var message = Message()
                    message.arg1 = it
                    handler.sendMessage(message) }
            }
            2->{
                anchorBlockDialogContent.text = context.getString(R.string.stream_anchor_block_content_1_2)
                anchorBlockDialogContent1.text = context.getString(R.string.stream_anchor_block_content_2_2)
                anchorBlockDialogBtn.text = context.getString(R.string.stream_anchor_block_btn_1)
            }
        }
    }

    private var handler: Handler = object : Handler(Looper.getMainLooper()){
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            var time = msg.arg1
            anchorBlockDialogBtn.text = context.getString(R.string.stream_anchor_block_btn, --time)
            if(time == 0){
                dismiss()
                mAnchorBlockedTipDialogListener?.onConfirm()
            }else{
                var message = Message()
                message.arg1 = time
                sendMessageDelayed(message, 1000)
            }
        }
    }

    private fun initListener(){
        anchorBlockDialogBtn.setOnClickListener {
            if(type == 1){//主播
                dismiss()
                mAnchorBlockedTipDialogListener?.onConfirm()
            }else{//观众
                dismiss()
                mAnchorBlockedTipDialogListener?.onConfirm()
            }


        }

    }

    interface AnchorBlockedTipDialogListener{
        fun onConfirm()
    }
}