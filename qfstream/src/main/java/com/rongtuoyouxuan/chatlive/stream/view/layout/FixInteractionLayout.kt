package com.rongtuoyouxuan.chatlive.stream.view.layout

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import com.rongtuoyouxuan.chatlive.base.utils.RoomDegreeUtils
import com.rongtuoyouxuan.chatlive.base.utils.ViewModelUtils
import com.rongtuoyouxuan.chatlive.base.viewmodel.IMLiveViewModel
import com.rongtuoyouxuan.chatlive.biz2.model.im.msg.textmsg.RTHotChangeMsg
import com.rongtuoyouxuan.chatlive.biz2.model.im.msg.textmsg.RTLikeMsg
import com.rongtuoyouxuan.chatlive.router.Router
import com.rongtuoyouxuan.chatlive.stream.R
import com.rongtuoyouxuan.chatlive.stream.viewmodel.StreamControllerViewModel
import com.rongtuoyouxuan.chatlive.util.LaToastUtil
import com.rongtuoyouxuan.libsocket.base.IMSocketBase
import kotlinx.android.synthetic.main.item_layout_online.view.*
import kotlinx.android.synthetic.main.qf_stream_layout_intercation_fix.view.*

class FixInteractionLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LockableScrollView(context, attrs, defStyleAttr) {
    private var fixLayout: View? = null
    private var imViewModel:IMLiveViewModel? = null
    var mControllerViewModel: StreamControllerViewModel? = null
    private var roomId: String? = null


    var observer: Observer<RTHotChangeMsg> = Observer<RTHotChangeMsg> {
        if (it.roomIdStr == mControllerViewModel?.roomId) {
            tvOnline4?.text = RoomDegreeUtils.getDegree(it.userCount)
        }
    }

    var likeObserver: Observer<RTLikeMsg> = Observer<RTLikeMsg> {
        if (it.roomIdStr == mControllerViewModel?.roomId) {
            rl_master_room_info?.setCurrentDiamond(it.likeCount.toInt())
        }
    }
    init {
        init(context)
    }

    private fun init(context: Context) {
        initViewModel(context)
        inflate(context, R.layout.qf_stream_layout_intercation_fix, this)
        fixLayout = findViewById(R.id.fix_layout)
        initView(context)
        initData(context)
    }

    private fun initView(context: Context) {
        hostGoodsLayout.setOnClickListener {
            LaToastUtil.showShort("带货榜")
        }
        hostPersonLayout?.setOnClickListener {
            Router.toPopularityRankActivity(imViewModel?.anchorId)
        }

    }

    private fun initData(context: Context) {
    }

    fun setParams(width: Int, height: Int) {
        val layoutParams = fixLayout!!.layoutParams
        layoutParams.width = width
        layoutParams.height = height
        fixLayout!!.layoutParams = layoutParams
    }

    private fun initViewModel(context: Context) {
        imViewModel =
            ViewModelUtils.get(context as FragmentActivity, IMLiveViewModel::class.java)
        mControllerViewModel = ViewModelUtils.get(context as FragmentActivity?, StreamControllerViewModel::class.java)
        imViewModel?.roomInfoExtraLiveData?.observe(context as FragmentActivity){
            livegiftview.setHostId(imViewModel?.roomId)
            tvOnline4.text = "" + it?.data?.scene_user_count
            roomId = it?.data?.room_id_str
            it?.data?.room_id_str?.let { it1 -> registerObserver(it1) }
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