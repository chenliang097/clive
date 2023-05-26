package com.rongtuoyouxuan.chatlive.live.view.layout

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.rongtuoyouxuan.chatlive.base.utils.RoomDegreeUtils
import com.rongtuoyouxuan.chatlive.base.view.dialog.RecommendDialog
import com.rongtuoyouxuan.chatlive.base.viewmodel.IMLiveViewModel
import com.rongtuoyouxuan.chatlive.crtbiz2.model.im.msg.textmsg.RTHotChangeMsg
import com.rongtuoyouxuan.chatlive.crtbiz2.model.im.msg.textmsg.RTLikeMsg
import com.rongtuoyouxuan.chatlive.live.viewmodel.LiveControllerViewModel
import com.rongtuoyouxuan.chatlive.crtrouter.Router
import com.rongtuoyouxuan.chatlive.stream.R
import com.rongtuoyouxuan.chatlive.crtutil.util.LaToastUtil
import com.rongtuoyouxuan.chatlive.libsocket.base.IMSocketBase
import kotlinx.android.synthetic.main.item_layout_online.view.*
import kotlinx.android.synthetic.main.qf_stream_live_layout_intercation_fix.view.*

//观众端
class LiveFixInteractionLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : com.rongtuoyouxuan.chatlive.stream.view.layout.LockableScrollView(context, attrs, defStyleAttr) {
    private var fix_layout: View? = null

    private var mControllerViewModel: LiveControllerViewModel? = null
    private var mIMLiveViewModel: IMLiveViewModel? = null
    private var anchorName: String? = null
    private var roomId: String? = null

    private var liveRoomInfoAnchorLayout:LiveHostView? = null

    var observer: Observer<RTHotChangeMsg> = Observer<RTHotChangeMsg> {
        if (it.roomIdStr == mControllerViewModel?.roomId) {
            tvOnline4?.text = RoomDegreeUtils.getDegree(it.userCount)
        }
    }
    var likeObserver: Observer<RTLikeMsg> = Observer<RTLikeMsg> {
        if (it.roomIdStr == mControllerViewModel?.roomId) {
            liveRoomInfoAnchorLayout?.setCurrentDiamond(it.likeCount.toInt())
        }
    }

    init {
        init(context)
    }

    private fun init(context: Context) {
        initViewModel(context)
        inflate(context, R.layout.qf_stream_live_layout_intercation_fix, this)
        fix_layout = findViewById(R.id.fix_layout)
        livegiftview.setHostId(mIMLiveViewModel?.roomId)
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
            RecommendDialog.showDialog(context)
        }
        liveRoomInfoAnchorLayout = findViewById(R.id.liveRoomInfoAnchorLayout)
    }

    private fun initViewModel(context: Context) {
        mControllerViewModel = com.rongtuoyouxuan.chatlive.base.utils.ViewModelUtils.getLive(LiveControllerViewModel::class.java)
        mIMLiveViewModel = com.rongtuoyouxuan.chatlive.base.utils.ViewModelUtils.getLive(IMLiveViewModel::class.java)
    }

    private fun initData(context: Context) {
        mControllerViewModel?.roomInfoLiveData?.observe((context as LifecycleOwner)) {
            visibility = VISIBLE
            anchorName = it?.data?.anchor_name
            roomId = it?.data?.room_id_str
            it?.data?.room_id_str?.let { it1 -> registerObserver(it1) }
            it?.data?.scene_id_str?.let { it1 -> liveRoomInfoAnchorLayout?.setSceneId(it1) }
        }
        mControllerViewModel?.roomInfoExtraLiveData?.observe((context as LifecycleOwner)) {
//            liveRoomInfoAnchorLayout?.setCurrentDiamond(it.data.diamond_total)
            tvOnline4.text = "" + it?.data?.scene_user_count
            mControllerViewModel?.streamId?.let { it1 -> registerObserver(it1) }

        }
    }

    private fun initListener(context: Context) {
        liveRoomInfoPersonTxt.setOnClickListener {
            Router.toPopularityRankActivity(mIMLiveViewModel?.anchorId)
        }
        liveRoomInfoGoodsLayout.setOnClickListener {
            LaToastUtil.showShort("带货榜")
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
    }

    private fun registerObserver(roomId: String) {
        IMSocketBase.instance().room(roomId).hotChangeMsg.observe(observer)
        IMSocketBase.instance().room(roomId).likeMsg.observe(likeObserver)
    }

    override fun onDetachedFromWindow() {
        IMSocketBase.instance().room(roomId).hotChangeMsg.removeObserver(observer)
        IMSocketBase.instance().room(roomId).likeMsg.removeObserver(likeObserver)
        super.onDetachedFromWindow()
    }



}