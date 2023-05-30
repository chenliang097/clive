package com.rongtuoyouxuan.chatlive.stream.view.layout

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import com.rongtuoyouxuan.chatlive.base.utils.RoomDegreeUtils
import com.rongtuoyouxuan.chatlive.base.view.layout.BaseHostView
import com.rongtuoyouxuan.chatlive.crtimage.util.GlideUtils
import com.rongtuoyouxuan.chatlive.stream.R
import kotlinx.android.synthetic.main.qf_stream_view_hostinfo.view.*

class StreamHostView : BaseHostView {

    public var mControllerViewModel: com.rongtuoyouxuan.chatlive.stream.viewmodel.StreamControllerViewModel? = null

    constructor(context: Context?) : super(context!!) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(
        context!!, attrs)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context!!, attrs, defStyleAttr)

    override fun initViewModel(context: Context?) {
        super.initViewModel(context)
        mControllerViewModel = com.rongtuoyouxuan.chatlive.base.utils.ViewModelUtils.get(context as FragmentActivity?, com.rongtuoyouxuan.chatlive.stream.viewmodel.StreamControllerViewModel::class.java)
    }

    override fun initData() {
        super.initData()
        mControllerViewModel?.mHostInfo?.observe((context as LifecycleOwner)) { anchorInfo ->
            if(TextUtils.isEmpty(anchorInfo.nickname)){
                name?.text = anchorInfo.nickname
            }else {
                name?.text = anchorInfo.nickname
            }
            val avatar = anchorInfo.avatar
            GlideUtils.loadImage(context, avatar, iv_room_master_headimg, R.drawable.rt_icon_default)
            setCurrentDiamond(anchorInfo.likeNum)
        }

    }

    fun setCurrentDiamond(likeNum: Int) {
        tvCurrentDiamond?.text = context.getString(R.string.stream_host_click_zan_num, RoomDegreeUtils.getDegree(likeNum))
    }
}