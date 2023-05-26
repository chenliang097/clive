package com.rongtuoyouxuan.chatlive.base.view.layout

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import com.rongtuoyouxuan.chatlive.base.utils.ViewModelUtils
import com.rongtuoyouxuan.chatlive.base.viewmodel.IMLiveViewModel
import com.rongtuoyouxuan.chatlive.stream.R
import com.makeramen.roundedimageview.RoundedImageView

open class BaseHostView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr), View.OnClickListener {

    private var mIMViewModel: IMLiveViewModel? = null

    var roundedImageView: RoundedImageView? = null
    var name: TextView? = null
    var tvCurrentDiamond: TextView? = null

    init {
        initViewModel(context)
        init(context)
    }

    private fun init(context: Context) {
        inflate(getContext(), R.layout.qf_stream_view_hostinfo, this)
        initView(context)
        initData()
    }

    private fun initView(context: Context) {
        roundedImageView = findViewById(R.id.iv_room_master_headimg)
        findViewById<LinearLayout>(R.id.ll_master_room_username)?.setOnClickListener {
            roundedImageView?.callOnClick()
        }
        name = findViewById(R.id.tv_master_room_username)
        tvCurrentDiamond = findViewById(R.id.hostCurrentDiamondTxt)
        roundedImageView?.setOnClickListener(this)
    }

    open fun initData() {
        mIMViewModel!!.streamIdLiveEvent.observe((context as LifecycleOwner)) { s ->
            registerObserver(s)
        }

    }

    open fun initViewModel(context: Context?) {
        mIMViewModel = com.rongtuoyouxuan.chatlive.base.utils.ViewModelUtils.get(context as FragmentActivity, IMLiveViewModel::class.java)
    }

    override fun onClick(v: View) {
        if (v.id == R.id.iv_room_master_headimg) {
            val roomUserInfo = mIMViewModel!!.mUserInfo.value
            if (roomUserInfo!!.data.role != null) {
            }
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
//        if (!TextUtils.isEmpty(mIMViewModel!!.streamId)) {
//            registerObserver(mIMViewModel!!.streamId)
//        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        if (!TextUtils.isEmpty(mIMViewModel!!.streamId)) {
            unRegisterObserver(mIMViewModel!!.streamId)
        }
    }

    private fun registerObserver(streamId: String) {

    }

    private fun unRegisterObserver(streamId: String) {
    }
}