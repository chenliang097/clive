package com.rongtuoyouxuan.chatlive.stream.view.activity

import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.BarUtils
import com.rongtuoyouxuan.chatlive.biz2.model.stream.StreamEndBean
import com.rongtuoyouxuan.chatlive.router.constants.RouterConstant
import com.rongtuoyouxuan.chatlive.stream.R
import com.rongtuoyouxuan.chatlive.stream.viewmodel.StreamEndViewModel
import com.rongtuoyouxuan.chatlive.util.LaToastUtil
import com.rongtuoyouxuan.libuikit.SimpleActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.qf_stream_activity_end.*

@Route(path = RouterConstant.PATH_ACTIVITY_STREAM_END)
class StreamEndActivity : SimpleActivity(), View.OnClickListener {

    private var mStreamEndViewModel:StreamEndViewModel? = null
    private var streamId:String? = ""
    private var streamType:String? = ""

    override fun getLayoutResId(): Int {
        return R.layout.qf_stream_activity_end
    }

    override fun initData() {
        mStreamEndViewModel = getViewModel(StreamEndViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        streamId = intent.getStringExtra("streamId");
        streamType = intent.getStringExtra("streamType")
        initObserver()
    }

    private fun initObserver(){
        streamId?.let {
            mStreamEndViewModel?.streamEndLive(it)
        }
        mStreamEndViewModel?.liveEndLiveData?.observe(this) {
            updateData(it)
        }
    }

    private fun updateData(streamEndBean: StreamEndBean){
        streamEndBean.data?.prev_live?.living_time?.toLong()
            ?.let { streamEndTimeTxt?.text = getString(R.string.stream_end_time, secondToTime(it)) }
        streamEndHotTxt?.text = streamEndBean.data?.anchor?.nickname
        streamEndFansIncreaseNumTxt?.text = streamEndBean.data?.now_live?.audience_num.toString()
        streamEndSeePerNumTxt?.text = streamEndBean.data?.now_live?.increment_fans.toString()
        streamEndSeeFansNumTxt?.text = streamEndBean.data?.now_live?.diamond_total.toString()
        streamEndZanNumTxt?.text = streamEndBean.data?.now_live?.send_gift_people.toString()
        streamEndGiftNumTxt?.text = streamEndBean.data?.prev_live?.audience_num.toString()
        streamEndCoinTxt?.text = streamEndBean.data?.prev_live?.increment_fans.toString()

        var cover = streamEndBean?.data?.now_live?.pic!!
        Glide.with(this).load(cover).into(coverBg)
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
            LaToastUtil.showShort("主播中心")
        }
    }

    override fun onClick(v: View?) {
    }

    override fun statusBarSetting() {
        BarUtils.transparentStatusBar(this)
        BarUtils.setStatusBarLightMode(this, true)
    }
}