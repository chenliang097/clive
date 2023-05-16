package com.rongtuoyouxuan.chatlive.live.view.layout

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.*
import com.rongtuoyouxuan.chatlive.base.utils.ViewModelUtils
import com.rongtuoyouxuan.chatlive.base.view.model.SendEvent
import com.rongtuoyouxuan.chatlive.base.viewmodel.IMLiveViewModel
import com.rongtuoyouxuan.chatlive.biz2.model.im.msg.ntfmsg.BannedMsg
import com.rongtuoyouxuan.chatlive.biz2.model.im.msg.textmsg.RTRoomManagerAddMsg
import com.rongtuoyouxuan.chatlive.biz2.model.im.msg.textmsg.RTRoomManagerRelieveMsg
import com.rongtuoyouxuan.chatlive.databus.DataBus
import com.rongtuoyouxuan.chatlive.databus.liveeventbus.LiveDataBus
import com.rongtuoyouxuan.chatlive.databus.liveeventbus.constansts.LiveDataBusConstants
import com.rongtuoyouxuan.chatlive.live.view.dialog.LiveToolsDialog
import com.rongtuoyouxuan.chatlive.live.viewmodel.LiveControllerViewModel
import com.rongtuoyouxuan.chatlive.live.viewmodel.LiveRoomViewModel
import com.rongtuoyouxuan.chatlive.stream.R
import com.rongtuoyouxuan.libsocket.base.IMSocketBase
import kotlinx.android.synthetic.main.qf_stream_live_layout_bottom_tools.view.*

class LiveBottomToolsView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : RelativeLayout(context, attrs, defStyleAttr), LifecycleObserver {
    private var mLiveControllerViewModel: LiveControllerViewModel? = null
    private var mIMViewModel: IMLiveViewModel? = null
    private var bottomLayout: RelativeLayout? = null
    private var mLiveRoomViewModel: LiveRoomViewModel? = null
    private var shareUrl: String? = ""
    private var liveToolsDialog: LiveToolsDialog? = null

    private fun initViewModel(context: Context) {
        mLiveControllerViewModel =
            ViewModelUtils.getLive(LiveControllerViewModel::class.java)
        mIMViewModel = ViewModelUtils.getLive(IMLiveViewModel::class.java)
        mLiveRoomViewModel = ViewModelUtils.getLive(LiveRoomViewModel::class.java)
    }

    fun init(context: Context) {
        inflate(context, R.layout.qf_stream_live_layout_bottom_tools, this)
        initView(context)
        initData(context)
        initObServer()
    }

    private fun initData(context: Context) {
        mLiveControllerViewModel!!.mGiftDialog.observe((context as LifecycleOwner)) { }
        mIMViewModel!!.showGiftDialog.observeOnce((context as LifecycleOwner)) { }
    }

    private fun initView(context: Context) {
        val giftImg = findViewById<ImageView>(R.id.liveRoomBottomGiftImg)
        val zanImg = findViewById<ImageView>(R.id.liveRoomBottomZanImg)
        bottomLayout = findViewById(R.id.liveRoomBottomLayout)

        liveRoomSettingBtn.setOnClickListener {
            mLiveControllerViewModel?.anchorSettingLiveEvent?.call()
        }

        liveRoomBottomChatLayout?.setOnClickListener{
            //聊天
            if (DataBus.instance().isVisitor) {
                LiveDataBus.getInstance()
                    .with(LiveDataBusConstants.EVENT_KEY_TO_SHOW_LOGIN_DIALOG).value = true
                return@setOnClickListener
            }
            mLiveControllerViewModel!!.mMessageButton.setValue(
                SendEvent(SendEvent.TYPE_MESSAGE, null)
            )
        }

        mIMViewModel?.giftCallClickEvent?.observe(getContext() as FragmentActivity) {
            giftImg.callOnClick()
        }

        mIMViewModel?.streamIdLiveEvent?.observe((getContext() as LifecycleOwner)
        ) { t ->
            registerObserver(t)
        }

        mLiveControllerViewModel?.roomInfoLiveData?.observe((context as LifecycleOwner)) {
            if (it != null) {
                visibility = VISIBLE
                if(it.data?.is_room_admin == true){
                    liveRoomSettingBtn.visibility = View.VISIBLE
                }else{
                    liveRoomSettingBtn.visibility = View.GONE
                }
            }
        }

        mIMViewModel?.shareUrlLiveEvent?.observe(getContext() as LifecycleOwner) {
            shareUrl = it
        }

    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        if (!TextUtils.isEmpty(mIMViewModel?.streamId)) {
            unRegisterObserver(mIMViewModel?.streamId)
        }

    }

    private fun initObServer() {
        val activity = context as Activity as? AppCompatActivity ?: return
        activity.lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        liveToolsDialog?.onActivityResult(requestCode, resultCode, data)
    }

    init {
        initViewModel(context)
        init(context)
    }

    private var addManagerObserver: Observer<RTRoomManagerAddMsg> = Observer {
        liveRoomSettingBtn.visibility = View.VISIBLE
    }

    private var relieveManagerObserver: Observer<RTRoomManagerRelieveMsg> = Observer {
        liveRoomSettingBtn.visibility = View.GONE
    }

    private fun registerObserver(roomId: String?) {
        var userId = DataBus.instance().USER_ID
        IMSocketBase.instance().room(userId).roomManagerAddMsg.observe(addManagerObserver)
        IMSocketBase.instance().room(userId).roomManagerRelieveMsg.observe(relieveManagerObserver)

    }

    private fun unRegisterObserver(roomId: String?) {
        var userId = DataBus.instance().USER_ID
        IMSocketBase.instance().room(userId).roomManagerAddMsg.removeObserver(addManagerObserver)
        IMSocketBase.instance().room(userId).roomManagerRelieveMsg.removeObserver(relieveManagerObserver)

    }


    private fun messageBanned(isBanned:Boolean){
        when(isBanned){
            true->{
                liveRoomBottomChatLayout.setBackgroundResource(R.drawable.rt_stream_icon_bottom_input_bg)
                liveRoomBottomChatLayout.isEnabled = true
                liveRoomBottomChatTxt.text = context.getString(R.string.stream_bottom_input_hint)
            }
            false->{

                liveRoomBottomChatLayout.setBackgroundResource(R.drawable.bg_input_banned_bg)
                liveRoomBottomChatLayout.isEnabled = false
                liveRoomBottomChatTxt.text = context.getString(R.string.stream_bottom_input_banned_hint)
            }
        }
    }
}