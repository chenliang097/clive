package com.rongtuoyouxuan.chatlive.stream.view.activity

import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.TimeUtils
import com.rongtuoyouxuan.chatlive.biz2.model.stream.StreamEndBean
import com.rongtuoyouxuan.chatlive.router.constants.RouterConstant
import com.rongtuoyouxuan.chatlive.stream.R
import com.rongtuoyouxuan.chatlive.stream.viewmodel.StreamEndViewModel
import com.rongtuoyouxuan.chatlive.util.LaToastUtil
import com.rongtuoyouxuan.libuikit.SimpleActivity
import com.bumptech.glide.Glide
import com.rongtuoyouxuan.chatlive.databus.DataBus
import com.rongtuoyouxuan.chatlive.image.util.GlideUtils
import com.rongtuoyouxuan.chatlive.router.Router
import com.rongtuoyouxuan.chatlive.util.ChatTimeUtil
import com.rongtuoyouxuan.chatlive.util.GsonUtils
import kotlinx.android.synthetic.main.qf_stream_activity_end.*

@Route(path = RouterConstant.PATH_ACTIVITY_STREAM_END)
class StreamEndActivity : SimpleActivity(), View.OnClickListener {

    private var mStreamEndViewModel:StreamEndViewModel? = null
    private var roomId:String? = ""
    private var sceneId:String? = ""
    private var coverUrl:String? = ""

    override fun getLayoutResId(): Int {
        return R.layout.qf_stream_activity_end
    }

    override fun initData() {
        mStreamEndViewModel = getViewModel(StreamEndViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        roomId = intent.getStringExtra("roomId");
        sceneId = intent.getStringExtra("sceneId");
        coverUrl = intent.getStringExtra("coverUrl");
        initObserver()
    }

    private fun initObserver(){
        roomId?.let {
            sceneId?.let { it1 -> mStreamEndViewModel?.getStreamEnd(it, it1) }
        }
        mStreamEndViewModel?.liveEndLiveData?.observe(this) {
            updateData(it)
        }
    }

    private fun updateData(streamEndBean: StreamEndBean){
        GlideUtils.loadImage(this, coverUrl, coverBg, R.drawable.rt_default_avatar)
        streamEndHotTxt.text = "" + streamEndBean.data?.max_fire
        streamEndFansIncreaseNumTxt.text = "" + streamEndBean.data?.fans_count
        streamEndSeePerNumTxt.text = "" + streamEndBean.data?.view_count
        streamEndSeeFansNumTxt.text = "" + streamEndBean.data?.medium_fans_view_count
        streamEndZanNumTxt.text = "" + streamEndBean.data?.liking_count
        streamEndGiftNumTxt.text = "" + streamEndBean.data?.gift_income
        streamEndTimeTxt.text =
            streamEndBean.data?.living_start_time?.toLong()
                ?.let { ChatTimeUtil.getHourAndMin(it*1000).plus("~")
                    .plus(streamEndBean.data?.living_off_time?.toLong()
                        ?.let { it1 -> ChatTimeUtil.getHourAndMin(it1*1000) })
                    .plus(getString(R.string.stream_end_time_min, streamEndBean.data?.living_time_minutes)) }
        streamEndHotTxt.text = "" + "0"
    }

    fun secondToTime(second: Long): String? {
        var second = second
        val hours = second / 3600 //转换小时数
        second %= 3600 //剩余秒数
        val minutes = second / 60 //转换分钟
        second %= 60 //剩余秒数
        return if (hours > 0) {
            unitFormat(hours) + ":" + unitFormat(minutes) + ":" + unitFormat(second)
        } else {
            unitFormat(minutes) + ":" + unitFormat(second)
        }
    }

    private fun unitFormat(i: Long): String {
        return if (i in 0..9) "0$i" else "" + i
    }

    override fun initListener() {
        streamEndCloseImg?.setOnClickListener {
            finish()
        }
        streamEndAnchorCenterTxt?.setOnClickListener {
            Router.toAnchorCenterActivity()
        }
    }

    override fun onClick(v: View?) {
    }

    override fun statusBarSetting() {
        BarUtils.transparentStatusBar(this)
        BarUtils.setStatusBarLightMode(this, true)
    }
}