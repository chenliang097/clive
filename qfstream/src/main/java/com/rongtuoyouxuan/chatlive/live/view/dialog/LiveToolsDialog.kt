package com.rongtuoyouxuan.chatlive.live.view.dialog

import android.app.Dialog
import android.content.Context
import android.content.Intent
import com.rongtuoyouxuan.chatlive.stream.R
import com.rongtuoyouxuan.chatlive.base.viewmodel.IMLiveViewModel
import android.os.Bundle
import android.view.*
import com.rongtuoyouxuan.chatlive.base.utils.ViewModelUtils
import com.rongtuoyouxuan.chatlive.util.UIUtils
import com.rongtuoyouxuan.qfcommon.dialog.ShareDialog
import kotlinx.android.synthetic.main.qf_stream_dialog_live_tools.*
import kotlinx.android.synthetic.main.qf_stream_dialog_stream_tools.bottomToolShareLayout
import kotlinx.android.synthetic.main.qf_stream_dialog_stream_tools.bottomToolSpecialLayout

class LiveToolsDialog: Dialog{

    private lateinit var mIMViewModel: IMLiveViewModel

    private var mContext:Context? = null
    private var shareUrl:String? = ""
    private var shareDialog: ShareDialog? = null
    private var isHideMinimize = false



    constructor(mContext: Context, shareUrl:String?, isHideMinimize:Boolean):super(mContext, R.style.commenDialogStyle){
        this.mContext = mContext
        this.shareUrl = shareUrl
        this.isHideMinimize = isHideMinimize
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.qf_stream_dialog_live_tools)
        setWindowLocation()
        initViewModel()
        initView()
    }

    private fun setWindowLocation() {
        val win = this.window
        win?.decorView?.setPadding(0, 0, 0, 0)
        val lp = win?.attributes
        lp?.width = (UIUtils.screenWidth(mContext))
        lp?.height = WindowManager.LayoutParams.WRAP_CONTENT
        lp?.gravity = Gravity.BOTTOM
        win?.attributes = lp
        win?.decorView?.setBackgroundResource(R.drawable.corner_circle_white_top)
    }

    private fun initView() {
        initListener()
        if(isHideMinimize){
            bottomToolMinImg.visibility = View.GONE
            streamSettingLayout1.visibility = View.GONE
        }else{
            bottomToolMinImg.visibility = View.VISIBLE
            streamSettingLayout1.visibility = View.VISIBLE
        }
    }

    private fun initListener(){
        bottomToolShareLayout?.setOnClickListener {
            shareTo()
            dismiss()
        }
        bottomToolSpecialLayout?.setOnClickListener {
            //特效
            mIMViewModel.gameSpecialSettingLiveEvent.value = true
            dismiss()
        }
        bottomToolMinImg?.setOnClickListener {
            mIMViewModel?.minLiveEvent?.value = true
        }
    }

    private fun initViewModel(){
        mIMViewModel = ViewModelUtils.getLive(IMLiveViewModel::class.java)
    }

    private fun shareTo(){
        shareDialog = mContext?.let {
            ShareDialog.showDialog(
                it,
                null,
                shareUrl,
                object : ShareDialog.CallBack {
                    override fun itemClick(channel: String) {
                        mIMViewModel?.shareSuccess(channel)
                    }

                    override fun dismiss() {

                    }
                })
        }
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){
        shareDialog?.onActivityResult(requestCode,resultCode,data)
    }

}