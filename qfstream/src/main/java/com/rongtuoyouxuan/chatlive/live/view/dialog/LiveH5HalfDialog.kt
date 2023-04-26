package com.rongtuoyouxuan.chatlive.live.view.dialog

import android.annotation.SuppressLint
import androidx.fragment.app.FragmentActivity
import com.rongtuoyouxuan.chatlive.databus.DataBus
import com.rongtuoyouxuan.chatlive.log.upload.ULog
import com.rongtuoyouxuan.chatlive.stream.R
import com.rongtuoyouxuan.chatlive.util.UIUtils
import com.rongtuoyouxuan.qfcommon.util.APIEnvironment
import com.lxj.xpopup.core.BottomPopupView
import kotlinx.android.synthetic.main.qf_stream_layout_live_h5_half_screen.view.*

/**
 * 
 * date:2022/11/26-12:05
 * des: 半屏H5--
 */
@SuppressLint("ViewConstructor")
class LiveH5HalfDialog(
    private val activity: FragmentActivity,
    val path: String,
    val anchorShowId: String,
    val streamId: String
) : BottomPopupView(activity) {
    override fun getImplLayoutId(): Int {
        return R.layout.qf_stream_layout_live_h5_half_screen
    }

    override fun getPopupHeight(): Int {
        return UIUtils.screenHeight(context) / 2
    }

    override fun onCreate() {
        super.onCreate()
        val token = DataBus.instance().userInfo.value?.token
    }
}