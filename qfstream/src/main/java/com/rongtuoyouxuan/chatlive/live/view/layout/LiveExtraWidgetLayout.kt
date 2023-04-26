package com.rongtuoyouxuan.chatlive.live.view.layout

import android.widget.RelativeLayout
import com.rongtuoyouxuan.chatlive.util.DisplayUtil
import com.rongtuoyouxuan.chatlive.biz2.model.stream.AdsBean
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import com.rongtuoyouxuan.chatlive.base.viewmodel.IMLiveViewModel
import com.rongtuoyouxuan.chatlive.util.KeyBoardUtils.OnSoftKeyboardStateChangedListener
import com.rongtuoyouxuan.chatlive.base.utils.ViewModelUtils
import com.rongtuoyouxuan.chatlive.stream.R
import com.rongtuoyouxuan.chatlive.util.KeyBoardUtils
import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import com.rongtuoyouxuan.chatlive.stream.view.activity.StreamActivity
import java.util.ArrayList

/**
 * 直播间 广告
 */
class LiveExtraWidgetLayout : RelativeLayout {
    protected var BOTTOM_HEIGHT = DisplayUtil.dipToPixels(context, 40f)
    private var livebanner: LiveBanner? = null
    private var mActlist: List<AdsBean> = ArrayList()
    private var onGlobalLayoutListener: OnGlobalLayoutListener? = null
    private var mIMLiveViewModel: IMLiveViewModel? = null

    constructor(context: Context?) : super(context) {
        initView()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        initView()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initView()
    }

    var onSoftKeyboardStateChangedListener: OnSoftKeyboardStateChangedListener =
        object : OnSoftKeyboardStateChangedListener {
            override fun onSoftKeyboardShow(height: Int) {
                val layoutParams = layoutParams as LayoutParams
                layoutParams.setMargins(0, 0, 0, BOTTOM_HEIGHT)
                setLayoutParams(layoutParams)
            }

            override fun onSoftKeyboardHide(height: Int) {
                val layoutParams = layoutParams as LayoutParams
                layoutParams.setMargins(0, 0, 0, 0)
                setLayoutParams(layoutParams)
            }
        }

    private fun initViewModel(context: Context) {
        if(context as FragmentActivity is StreamActivity){
            mIMLiveViewModel =
                ViewModelUtils.get(context as FragmentActivity, IMLiveViewModel::class.java)
        }else{
            mIMLiveViewModel =
                ViewModelUtils.getLive(IMLiveViewModel::class.java)
        }


    }

    private fun initView() {
        initViewModel(context)
        inflate(context, R.layout.qf_stream_live_layout_extra, this)
        livebanner = findViewById(R.id.ll_livebanner)
        initData(context)
        onGlobalLayoutListener = KeyBoardUtils.setOnGlobalLayoutListener(
            context as Activity,
            onSoftKeyboardStateChangedListener
        )
    }

    private fun initData(context: Context) {
        mIMLiveViewModel!!.adsLiveEvent.observe((context as LifecycleOwner)) { adsBeans ->
            if (adsBeans != null && adsBeans.isNotEmpty()) {
                mActlist = adsBeans
                showElements()
            }
        }
    }

    private fun showElements() {
        if (mActlist.isNotEmpty()) {
            livebanner?.visibility = VISIBLE
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        KeyBoardUtils.removeOnGlobalLayoutListener(
            context as Activity,
            onGlobalLayoutListener,
            onSoftKeyboardStateChangedListener
        )
    }
}