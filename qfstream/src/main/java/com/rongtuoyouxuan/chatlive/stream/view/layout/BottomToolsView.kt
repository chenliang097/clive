package com.rongtuoyouxuan.chatlive.stream.view.layout

import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import kotlin.jvm.JvmOverloads
import android.widget.LinearLayout
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import com.rongtuoyouxuan.chatlive.stream.viewmodel.StreamViewModel
import com.rongtuoyouxuan.chatlive.base.viewmodel.IMLiveViewModel
import com.rongtuoyouxuan.chatlive.stream.view.dialog.ToolsDialog
import com.rongtuoyouxuan.chatlive.stream.R
import kotlinx.android.synthetic.main.qf_stream_layout_bottom_tools.view.*

class BottomToolsView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr),
    com.rongtuoyouxuan.chatlive.stream.view.layout.BackPressListener {
    private var messageImg: ImageView? = null
    private var mStreamViewModel: StreamViewModel? = null
    private var mControllerViewModel: com.rongtuoyouxuan.chatlive.stream.viewmodel.StreamControllerViewModel? = null
    private var mIMViewModel: IMLiveViewModel? = null
    private var toolsDialog: ToolsDialog? = null
    private var shareUrl: String? = ""

    init {
        init()
    }

    private fun init() {
        clipChildren = false
        initViewModel()
        inflate(context, R.layout.qf_stream_layout_bottom_tools, this)
        initView()
        initListener()
    }

    private fun initView() {
        messageImg = findViewById(R.id.liveBottomMessageImg)

    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){
        toolsDialog?.onActivityResult(requestCode,resultCode,data)
    }

    private fun initListener() {
        messageImg!!.setOnClickListener { v: View -> onMessageClick(v) }
        streamCloseBtn?.setOnClickListener {
            mControllerViewModel?.mOutDialog?.call()
        }
        streamSettingBtn?.setOnClickListener {
            mControllerViewModel?.anchorSettingLiveEvent?.call()
        }

    }

    private fun initViewModel() {
        mControllerViewModel = com.rongtuoyouxuan.chatlive.base.utils.ViewModelUtils.get(context as FragmentActivity, com.rongtuoyouxuan.chatlive.stream.viewmodel.StreamControllerViewModel::class.java)
        mStreamViewModel = com.rongtuoyouxuan.chatlive.base.utils.ViewModelUtils.get(context as FragmentActivity, StreamViewModel::class.java)
        mIMViewModel = com.rongtuoyouxuan.chatlive.base.utils.ViewModelUtils.get(context as FragmentActivity, IMLiveViewModel::class.java)

        mIMViewModel?.streamIdLiveEvent?.observe((context as LifecycleOwner)
        ) { t ->
            visibility = VISIBLE
            registerObserver(t)
        }
        mIMViewModel?.shareUrlLiveEvent?.observe((context as LifecycleOwner)){
            shareUrl = it
        }

    }

    private fun onMessageClick(v: View) {
        mControllerViewModel!!.mMessageButton.value =
            com.rongtuoyouxuan.chatlive.base.view.model.SendEvent(
                com.rongtuoyouxuan.chatlive.base.view.model.SendEvent.TYPE_MESSAGE,
                null
            )
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        if(!TextUtils.isEmpty(mIMViewModel?.streamId)){
            unRegisterObserver(mIMViewModel?.streamId)
        }
    }

    override fun onBackPress(): Boolean {
        return if (visibility == VISIBLE) {
            mControllerViewModel?.mOutDialog?.call()
            true
        } else {
            false
        }
    }

    private fun registerObserver(streamId: String?) {
    }

    private fun unRegisterObserver(streamId: String?) {
    }
}