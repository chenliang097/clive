package com.rongtuoyouxuan.chatlive.stream.view.layout

import android.content.Context
import android.util.AttributeSet
import kotlin.jvm.JvmOverloads
import android.widget.RelativeLayout
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import com.rongtuoyouxuan.chatlive.stream.viewmodel.StreamControllerViewModel
import com.rongtuoyouxuan.chatlive.stream.viewmodel.StreamViewModel
import com.rongtuoyouxuan.chatlive.stream.R
import com.rongtuoyouxuan.chatlive.base.utils.ViewModelUtils

class InteractionLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {
    private val mControllerViewModel: StreamControllerViewModel
    private val mStreamViewModel: StreamViewModel
    private var fixInteractionLayout: FixInteractionLayout? = null

    init {
        mControllerViewModel = ViewModelUtils.get(context as FragmentActivity, StreamControllerViewModel::class.java)
        mStreamViewModel = ViewModelUtils.get(context, StreamViewModel::class.java)
        init(context)
    }

    private fun init(context: Context) {
        inflate(getContext(), R.layout.qf_stream_layout_intercation, this)
        initView()
        initVisibility(getContext())
        initListener(context as LifecycleOwner)
    }

    private fun initView() {
        fixInteractionLayout = findViewById(R.id.lc_fixinteraction)
    }


    private fun initListener(context: LifecycleOwner) {
        mStreamViewModel.startStreamEvent.observe(context) {
            mControllerViewModel.interactionVisibility.value = true
            mControllerViewModel.mNetworkSpeedVisibility.value = true
        }
        mControllerViewModel.interactionVisibility.observeOnce(context) { aBoolean ->
            visibility = if (aBoolean!!) {
                VISIBLE
            } else {
                GONE
            }
        }
    }

    private fun initVisibility(context: Context) {
        val value = mControllerViewModel.interactionVisibility.value
        visibility = if (value) {
            VISIBLE
        } else {
            GONE
        }
    }


    fun setParams(w: Int, h: Int) {
        fixInteractionLayout?.setParams(w, h)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
    }
}