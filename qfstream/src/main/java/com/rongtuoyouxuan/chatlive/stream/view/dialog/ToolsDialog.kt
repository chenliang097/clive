package com.rongtuoyouxuan.chatlive.stream.view.dialog

import android.app.Dialog
import android.content.Context
import android.content.Intent
import com.rongtuoyouxuan.chatlive.stream.R
import com.rongtuoyouxuan.chatlive.stream.viewmodel.StreamViewModel
import com.rongtuoyouxuan.chatlive.base.viewmodel.IMLiveViewModel
import android.os.Bundle
import android.os.Handler
import android.view.*
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import com.rongtuoyouxuan.chatlive.crtutil.util.UIUtils
import com.rongtuoyouxuan.chatlive.base.utils.LiveRoomHelper
import com.rongtuoyouxuan.chatlive.qfcommon.dialog.ShareDialog
import kotlinx.android.synthetic.main.qf_stream_dialog_stream_tools.*

class ToolsDialog: Dialog{

    private var mStreamViewModel: StreamViewModel? = null
    private lateinit var mControllerViewModel: com.rongtuoyouxuan.chatlive.stream.viewmodel.StreamControllerViewModel
    private lateinit var mIMViewModel: IMLiveViewModel

    private var settingLayout:LinearLayout? = null
    private var toolLayout:LinearLayout? = null
    private var title:TextView? = null

    private var mContext:Context? = null
    private var shareDialog: ShareDialog? = null
    private var shareUrl:String? = ""
    /**
     * type 1：设置；2：工具
     */

    constructor(mContext: Context, shareUrl:String?):super(mContext, R.style.commenDialogStyle){
        this.mContext = mContext
        this.shareUrl = shareUrl
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.qf_stream_dialog_stream_tools)
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
//        win?.setWindowAnimations(R.style.CommonDialogStyleAnimation)
    }

    private fun initView() {
        title = findViewById(R.id.streamBottomDialogLayout)
        settingLayout = findViewById(R.id.streamSettingLayout)
        toolLayout = findViewById(R.id.streamToolLayout)

        mStreamViewModel?.muteEvent?.value?.let { updateMicStatus(it) }
        initListener()
    }

    private fun initListener(){
        findViewById<View>(R.id.bottomToolPasterLayout).setOnClickListener {
            LiveRoomHelper.openStickDialogVM.post(1)
            dismiss()
        }
        findViewById<View>(R.id.bottomToolMusicLayout).setOnClickListener { dismiss()
            Handler().postDelayed({
                mControllerViewModel.mMessageButton.value =
                    com.rongtuoyouxuan.chatlive.base.view.model.SendEvent(
                        com.rongtuoyouxuan.chatlive.base.view.model.SendEvent.TYPE_NOTICE,
                        null
                    )
            }, 50) //延迟调用，防止软键盘无法弹出
        }
        //设置
        findViewById<View>(R.id.bottomToolCameraLayout).setOnClickListener {
            //相机翻转
            mStreamViewModel?.switchCamera()
            dismiss()
        }
        findViewById<View>(R.id.bottomToolBeautyLayout).setOnClickListener {
            //美颜
            mControllerViewModel?.beautySettingVisibility.value = true
            dismiss()
        }
        findViewById<View>(R.id.bottomToolMicLayout).setOnClickListener {
            //麦克风开关
            mStreamViewModel?.mute()
            dismiss()
        }
        findViewById<View>(R.id.bottomToolMessageLayout).setOnClickListener {
            //聊天室开关
            mIMViewModel.sendBannedMsg(context)
            dismiss()
        }
        bottomToolShareLayout?.setOnClickListener {
            shareTo()
            dismiss()
        }
        bottomToolSpecialLayout?.setOnClickListener {
            //特效
            mIMViewModel.gameSpecialSettingLiveEvent.value = true
            dismiss()
        }
    }

    private fun initViewModel(){
        mStreamViewModel = com.rongtuoyouxuan.chatlive.base.utils.ViewModelUtils.get(mContext as FragmentActivity, StreamViewModel::class.java)
        mControllerViewModel = com.rongtuoyouxuan.chatlive.base.utils.ViewModelUtils.get(mContext as FragmentActivity, com.rongtuoyouxuan.chatlive.stream.viewmodel.StreamControllerViewModel::class.java)
        mIMViewModel = com.rongtuoyouxuan.chatlive.base.utils.ViewModelUtils.get(mContext as FragmentActivity, IMLiveViewModel::class.java)

        mStreamViewModel?.muteEvent?.observe(mContext as FragmentActivity){
            updateMicStatus(it)
        }
        mIMViewModel?.bannedLiveData?.observe(mContext as FragmentActivity){
            updateChatStatus(it)
        }
    }

    private fun updateMicStatus(it:Boolean){
        when(it){
            true-> bottomToolMicImg.setImageResource(R.drawable.qf_stream_icon_bottom_mic_off)
            false->bottomToolMicImg.setImageResource(R.drawable.qf_stream_icon_bottom_mic)
        }
    }

    private fun updateChatStatus(it:Boolean){
        when(it){
            true-> bottomToolMessageImg.setImageResource(R.drawable.qf_stream_icon_bottom_message_off)
            false->bottomToolMessageImg.setImageResource(R.drawable.qf_stream_icon_bottom_message)
        }
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