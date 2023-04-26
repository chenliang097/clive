package com.rongtuoyouxuan.chatlive.live.view.layout

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import kotlin.jvm.JvmOverloads
import android.widget.RelativeLayout
import com.rongtuoyouxuan.chatlive.live.viewmodel.LiveControllerViewModel
import com.rongtuoyouxuan.chatlive.base.viewmodel.IMLiveViewModel
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.makeramen.roundedimageview.RoundedImageView
import com.rongtuoyouxuan.chatlive.biz2.model.stream.im.RoomNumber
import com.rongtuoyouxuan.chatlive.stream.R
import com.rongtuoyouxuan.chatlive.base.utils.ViewModelUtils
import com.rongtuoyouxuan.chatlive.biz2.model.stream.EnterRoomBean
import com.rongtuoyouxuan.chatlive.databus.liveeventbus.LiveDataBus
import com.rongtuoyouxuan.chatlive.databus.liveeventbus.constansts.LiveDataBusConstants

class LiveHostView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr), View.OnClickListener {
    var mControllerViewModel: LiveControllerViewModel? = null
    private var mIMViewModel: IMLiveViewModel? = null
    private var follows: TextView? = null
    private var roundedImageView: RoundedImageView? = null
    private var name: TextView? = null
    var observer: Observer<*> = Observer<RoomNumber?> { }
    private var liveRoomBean:EnterRoomBean? = null
    var tvCurrentDiamond: TextView? = null

    init {
        init(context)
    }

    private fun init(context: Context) {
        initViewModel(context)
        inflate(getContext(), R.layout.qf_stream_live_view_hostinfo, this)
        initView(context)
        initData(context)
    }

    fun initViewModel(context: Context?) {
        mControllerViewModel = ViewModelUtils.getLive(LiveControllerViewModel::class.java)
        mIMViewModel = ViewModelUtils.getLive(IMLiveViewModel::class.java)
    }

    private fun initData(context: Context) {
        mControllerViewModel!!.mHostInfo.observe((context as LifecycleOwner)) { it ->
            name?.text = it.nickname
            val coverImg = it.avatar
            setCurrentDiamond(it.likeNum)
        }
        mIMViewModel!!.followStateLiveData.observe((context as LifecycleOwner), Observer { aBoolean ->
            if (aBoolean == null) return@Observer
            updateFollowStatus(aBoolean)
        })

        LiveDataBus.getInstance().with(LiveDataBusConstants.EVENT_KEY_TO_FOLLOW_GONE)
            .observe((context as LifecycleOwner)) {
//                follows!!.visibility = GONE
                updateFollowStatus(true)
            }
    }

    private fun updateFollowStatus(status:Boolean){
        when(status){
            false->{
                follows?.setBackgroundResource(R.drawable.btn_follow_live)
                follows?.visibility = VISIBLE
                follows?.isClickable = true
            }
            true ->{
                follows?.setBackgroundResource(R.drawable.rt_btn_followed)
                follows?.visibility = GONE
                follows?.isClickable = false
            }
        }
    }

    private fun initView(context: Context) {
        roundedImageView = findViewById(R.id.iv_room_master_headimg)
        findViewById<LinearLayout>(R.id.hostInfoInfoNameLayout)?.setOnClickListener {
            roundedImageView?.callOnClick()
        }

        name = findViewById(R.id.tv_master_room_username)
        follows = findViewById(R.id.hostInfoFollowImg)
        name?.isSelected = true
        roundedImageView?.setOnClickListener(this)
        follows?.setOnClickListener(this)
        tvCurrentDiamond = findViewById(R.id.hostCurrentDiamondTxt)

    }

    override fun onClick(v: View) {
        if (v.id == R.id.iv_room_master_headimg) {
            val roomUserInfo = mIMViewModel!!.mUserInfo.value
            if (roomUserInfo!!.data.role != null) {
            }
        } else if (v.id == R.id.hostInfoFollowImg) {
//            mIMViewModel?.addFollow()
            follows!!.isClickable = false
        }
    }

    fun setCurrentDiamond(diamond: Int) {
        tvCurrentDiamond?.text = context.getString(R.string.stream_host_click_zan_num, diamond)
    }
}