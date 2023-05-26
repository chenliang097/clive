package com.rongtuoyouxuan.chatlive.live.view.dialog

import android.app.Dialog
import android.content.Context
import com.rongtuoyouxuan.chatlive.stream.R
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import com.rongtuoyouxuan.chatlive.crtimage.ImgLoader
import com.rongtuoyouxuan.chatlive.crtutil.util.UIUtils
import kotlinx.android.synthetic.main.qf_stream_dialog_audience_exit.*

class AudienceExitDialog: Dialog{

    private var mContext:Context? = null
    private var mAudienceExitDialogListener:AudienceExitDialogListener? = null
    private var avatar:String? = ""
    private var isFollow:Boolean? = false
    private var streamType:String? = ""
//    private var imLiveViewModel:IMLiveViewModel? = null
    private var isFirst:Boolean? = true

    constructor(mContext: Context, avatar:String?, isFollow:Boolean?, streamType:String, mAudienceExitDialogListener:AudienceExitDialogListener):super(mContext, R.style.commenDialogStyle){
        this.mContext = mContext
        this.mAudienceExitDialogListener = mAudienceExitDialogListener
        this.avatar = avatar
        this.isFollow = isFollow
        this.streamType = streamType
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.qf_stream_dialog_audience_exit)
        setWindowLocation()
        initView()
        initListener();
        initObserver()
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
        ImgLoader.with(context).load(avatar).into(liveAudienceExitAvatar)
    }

    private fun initListener(){
        findViewById<TextView>(R.id.liveAudienceExitAndFollowTxt)?.setOnClickListener {
//            if(isFollow == true){
                mAudienceExitDialogListener?.onAudienceExitAndFollowListener()
                dismiss()
//            }else {
//                imLiveViewModel?.addFollow()
//            }
        }
        findViewById<TextView>(R.id.liveAudienceExitTxt)?.setOnClickListener {
            mAudienceExitDialogListener?.onAudienceExitListener()
            dismiss()
        }

        findViewById<ImageView>(R.id.liveAudienceCloseImg)?.setOnClickListener {
            dismiss()
        }


    }

    fun initObserver(){
//        imLiveViewModel = ViewModelUtils.getLive(IMLiveViewModel::class.java)
//        imLiveViewModel?.followStateLiveData?.observe(mContext as FragmentActivity){
//            if (isFirst == true){
//                isFirst = false
//                return@observe
//            }
//            mAudienceExitDialogListener?.onAudienceExitListener()
//            dismiss()
//        }
    }

    fun setAudienceExitDialogListener(mAudienceExitDialogListener:AudienceExitDialogListener){
        this.mAudienceExitDialogListener = mAudienceExitDialogListener
    }

    interface AudienceExitDialogListener{
        fun onAudienceExitAndFollowListener()
        fun onAudienceExitListener()
        fun onFollowListener()
    }
}