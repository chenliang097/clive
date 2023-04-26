package com.rongtuoyouxuan.chatlive.live.view.layout

import kotlin.jvm.JvmOverloads
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.AttributeSet
import com.rongtuoyouxuan.chatlive.stream.view.layout.LockableScrollView
import android.view.View
import android.widget.TextView
import com.rongtuoyouxuan.chatlive.live.viewmodel.LiveControllerViewModel
import com.rongtuoyouxuan.chatlive.stream.R
import com.rongtuoyouxuan.chatlive.base.utils.ViewModelUtils
import android.text.TextUtils
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.rongtuoyouxuan.chatlive.base.view.dialog.RecommendDialog
import com.rongtuoyouxuan.chatlive.base.viewmodel.IMLiveViewModel
import com.rongtuoyouxuan.chatlive.biz2.model.im.msg.cmdMsg.LiveHotMsg
import com.rongtuoyouxuan.chatlive.biz2.model.live.LiveRoomBean
import com.rongtuoyouxuan.chatlive.stream.view.layout.StreamPreviewLayout
import kotlinx.android.synthetic.main.item_layout_online.view.*
import kotlinx.android.synthetic.main.qf_stream_live_layout_intercation_fix.view.*

//观众端
class LiveFixInteractionLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : LockableScrollView(context, attrs, defStyleAttr) {
    private var fix_layout: View? = null

    private var mControllerViewModel: LiveControllerViewModel? = null
    private var mIMLiveViewModel: IMLiveViewModel? = null
    private var anchorName: String? = null
    private var liveId: String? = null

    private var liveRoomInfoAnchorLayout:LiveHostView? = null

    var observer: Observer<LiveHotMsg> = Observer<LiveHotMsg> {
        if (it.roomId == mControllerViewModel?.streamId?.toLong()) {
//            liveRoomInfoAnchorLayout?.setCurrentDiamond(it.currentHotNum)
        }
    }

    init {
        init(context)
    }

    private fun init(context: Context) {
        initViewModel(context)
        inflate(context, R.layout.qf_stream_live_layout_intercation_fix, this)
        fix_layout = findViewById(R.id.fix_layout)
        initView()
        initListener(context)
        initData(context)
    }

    fun setParams(width: Int, height: Int) {
        val layoutParams = fix_layout!!.layoutParams
        layoutParams.width = width
        layoutParams.height = height
        fix_layout!!.layoutParams = layoutParams
    }

    private fun initView() {
        sh_intercation?.setOnClickListener {
            liveId?.let { it1 -> RecommendDialog.showDialog(context, it1) }
        }
        liveRoomInfoAnchorLayout = findViewById(R.id.liveRoomInfoAnchorLayout)
    }

    private fun initViewModel(context: Context) {
        mControllerViewModel = ViewModelUtils.getLive(LiveControllerViewModel::class.java)
        mIMLiveViewModel = ViewModelUtils.getLive(IMLiveViewModel::class.java)
    }

    private fun initData(context: Context) {
        mControllerViewModel?.roomInfoLiveData?.observe((context as LifecycleOwner)) {
            anchorName = it?.data?.anchor_name
            liveId = it?.data?.stream_id
        }
        mControllerViewModel?.roomInfoExtraLiveData?.observe((context as LifecycleOwner)) {
//            liveRoomInfoAnchorLayout?.setCurrentDiamond(it.data.diamond_total)
            tvOnline4.text = "" + it?.data?.scene_user_count
            mControllerViewModel?.streamId?.let { it1 -> registerObserver(it1) }
        }
    }

    private fun initListener(context: Context) {}

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
    }

    private fun registerObserver(streamId: String) {
//        IMSocketImpl.getInstance().chatRoom(streamId).liveHotMsgLiveCallback.observe(observer)
    }

    override fun onDetachedFromWindow() {
//        IMSocketImpl.getInstance()
//            .chatRoom(mControllerViewModel?.streamId).liveHotMsgLiveCallback.removeObserver(observer)
        super.onDetachedFromWindow()
    }



}