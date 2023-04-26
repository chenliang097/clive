package com.rongtuoyouxuan.chatlive.stream.view.dialog

import android.app.Dialog
import android.content.Context
import com.rongtuoyouxuan.chatlive.stream.R
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import com.rongtuoyouxuan.chatlive.biz2.model.live.StreamOnlineModel
import com.rongtuoyouxuan.chatlive.biz2.stream.StreamBiz
import com.rongtuoyouxuan.chatlive.image.ImgLoader
import com.rongtuoyouxuan.chatlive.net2.RequestListener
import com.rongtuoyouxuan.chatlive.util.UIUtils
import com.makeramen.roundedimageview.RoundedImageView
import kotlinx.android.synthetic.main.qf_stream_dialog_host_exit.*

class HostExitDialog: Dialog{

    private var closeTxt:TextView? = null
    private var continueTxt:TextView? = null
    private var avatar1:RoundedImageView? = null
    private var avatar2:RoundedImageView? = null
    private var avatar3:RoundedImageView? = null
    private var numTxt:TextView? = null

    private var mContext:Context? = null
    private var mHostExitDialogListener:HostExitDialogListener? = null
    private var streamId:String? = ""
    private var anchorId:String? = ""

    constructor(mContext: Context, streamId:String?, anchorId:String?, mHostExitDialogListener:HostExitDialogListener):super(mContext, R.style.commenDialogStyle){
        this.mContext = mContext
        this.streamId = streamId
        this.anchorId = anchorId
        this.mHostExitDialogListener = mHostExitDialogListener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.qf_stream_dialog_host_exit)
        setWindowLocation()
        initView()
        initListener();
        initData()
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
        streamHostExitCloseTxt?.setOnClickListener {
            mHostExitDialogListener?.onHostExitDialog()
            dismiss()
        }
        streamHostExitContineTxt?.setOnClickListener {
            dismiss()
        }
    }

    fun initData(){
        StreamBiz.getOnlineList(mContext as LifecycleOwner, streamId, anchorId, 1, 5,
            object : RequestListener<StreamOnlineModel> {
                override fun onSuccess(reqId: String, result: StreamOnlineModel) {
                    if (result.data == null || result.data.list == null) {
                        return
                    }
                    updateUI(result)
                }

                override fun onFailure(reqId: String, errCode: String, msg: String) {}
            })
    }

    private fun updateUI(result: StreamOnlineModel){
        if(result!= null && result.data.list != null){
            when(result.data.list.size){
                0 ->{
                    streamHostExitLayout.visibility = View.GONE

                }
                1 ->{
                    streamHostExitLayout.visibility = View.VISIBLE
                    streamHostExitAvatar1.visibility = View.VISIBLE
                    streamHostExitAvatar2.visibility = View.GONE
                    streamHostExitAvatar3.visibility = View.GONE
                    streamHostExitNumTxt.visibility = View.GONE
                    ImgLoader.with(context).load(result.data.list[0].avatar).into(streamHostExitAvatar1)
                }
                2 ->{
                    streamHostExitLayout.visibility = View.VISIBLE
                    streamHostExitAvatar1.visibility = View.VISIBLE
                    streamHostExitAvatar2.visibility = View.VISIBLE
                    streamHostExitAvatar3.visibility = View.GONE
                    streamHostExitNumTxt.visibility = View.GONE
                    ImgLoader.with(context).load(result.data.list[0].avatar).into(streamHostExitAvatar1)
                    ImgLoader.with(context).load(result.data.list[1].avatar).into(streamHostExitAvatar2)
                }
                3 ->{
                    streamHostExitLayout.visibility = View.VISIBLE
                    streamHostExitAvatar1.visibility = View.VISIBLE
                    streamHostExitAvatar2.visibility = View.VISIBLE
                    streamHostExitAvatar3.visibility = View.VISIBLE
                    streamHostExitNumTxt.visibility = View.GONE
                    ImgLoader.with(context).load(result.data.list[0].avatar).into(streamHostExitAvatar1)
                    ImgLoader.with(context).load(result.data.list[1].avatar).into(streamHostExitAvatar2)
                    ImgLoader.with(context).load(result.data.list[2].avatar).into(streamHostExitAvatar2)
                }
                else ->{
                    streamHostExitLayout.visibility = View.VISIBLE
                    streamHostExitAvatar1.visibility = View.VISIBLE
                    streamHostExitAvatar2.visibility = View.VISIBLE
                    streamHostExitAvatar3.visibility = View.VISIBLE
                    streamHostExitNumTxt.visibility = View.VISIBLE
                    ImgLoader.with(context).load(result.data.list[0].avatar).into(streamHostExitAvatar1)
                    ImgLoader.with(context).load(result.data.list[1].avatar).into(streamHostExitAvatar2)
                    ImgLoader.with(context).load(result.data.list[2].avatar).into(streamHostExitAvatar2)
                    streamHostExitNumTxt.text = "" + result.data.total
                }
            }
        }

    }

    fun setHostExitDialogListener(mHostExitDialogListener:HostExitDialogListener){
        this.mHostExitDialogListener = mHostExitDialogListener
    }

    interface HostExitDialogListener{
        fun onHostExitDialog()
    }
}