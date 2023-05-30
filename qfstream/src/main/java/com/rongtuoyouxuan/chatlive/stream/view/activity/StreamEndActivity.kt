package com.rongtuoyouxuan.chatlive.stream.view.activity

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.BarUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.rongtuoyouxuan.chatlive.crtbiz2.model.stream.StreamEndBean
import com.rongtuoyouxuan.chatlive.crtrouter.constants.RouterConstant
import com.rongtuoyouxuan.chatlive.stream.R
import com.rongtuoyouxuan.chatlive.stream.viewmodel.StreamEndViewModel
import com.rongtuoyouxuan.chatlive.crtuikit.SimpleActivity
import com.rongtuoyouxuan.chatlive.crtimage.util.GlideUtils
import com.rongtuoyouxuan.chatlive.crtrouter.Router
import com.rongtuoyouxuan.chatlive.crtutil.util.ChatTimeUtil
import com.rongtuoyouxuan.chatlive.crtutil.util.LaToastUtil
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlinx.android.synthetic.main.qf_stream_activity_end.*

@Route(path = RouterConstant.PATH_ACTIVITY_STREAM_END)
class StreamEndActivity : SimpleActivity(), View.OnClickListener {

    private var mStreamEndViewModel:StreamEndViewModel? = null
    private var roomId:String? = ""
    private var sceneId:String? = ""
    private var coverUrl:String? = ""
    private var type = 1

    override fun getLayoutResId(): Int {
        return R.layout.qf_stream_activity_end
    }

    override fun initData() {
        mStreamEndViewModel = getViewModel(StreamEndViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        roomId = intent.getStringExtra("roomId")
        sceneId = intent.getStringExtra("sceneId")
        coverUrl = intent.getStringExtra("coverUrl")
        type = intent.getIntExtra("type", 1)
        initObserver()
    }

    private fun initObserver(){
        roomId?.let {
            sceneId?.let { it1 -> mStreamEndViewModel?.getStreamEnd(it, it1, type) }
        }
        mStreamEndViewModel?.liveEndLiveData?.observe(this) {
            updateData(it)
        }
    }

    private fun updateData(streamEndBean: StreamEndBean){
        coverUrl?.let { updateCoverLayout(it, coverBg) };
//        GlideUtils.loadImage(this, coverUrl, coverBg, R.drawable.rt_default_avatar)
        streamEndHotTxt.text = "" + streamEndBean.data?.max_fire
        streamEndFansIncreaseNumTxt.text = "" + streamEndBean.data?.scene_fans
        streamEndSeePerNumTxt.text = "" + streamEndBean.data?.max_user_count
        streamEndSeeFansNumTxt.text = "" + streamEndBean.data?.medium_fans_view_count
        streamEndZanNumTxt.text = "" + streamEndBean.data?.liking_count
        streamEndGiftNumTxt.text = "" + streamEndBean.data?.income
        streamEndTimeTxt.text =
            streamEndBean.data?.living_start_time?.toLong()
                ?.let { ChatTimeUtil.getHourAndMin(it*1000).plus("~")
                    .plus(streamEndBean.data?.living_off_time?.toLong()
                        ?.let { it1 -> ChatTimeUtil.getHourAndMin(it1*1000) })
                    .plus(getString(R.string.stream_end_time_min, streamEndBean.data?.living_time_minutes)) }
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
//            Router.toAnchorCenterActivity()
        }
    }

    override fun onClick(v: View?) {
    }

    override fun statusBarSetting() {
        BarUtils.transparentStatusBar(this)
        BarUtils.setStatusBarLightMode(this, true)
    }

    private fun updateCoverLayout(url: String, coverLayout: ImageView?) {
        Glide.with(this).asBitmap().load(url).placeholder(R.drawable.rt_icon_default)
            .error(R.drawable.rt_icon_default).apply(
                RequestOptions().transform(
                    BlurTransformation(25, 1)
                )
            ).into(object :
                SimpleTarget<Bitmap>() {
                override fun onLoadStarted(placeholder: Drawable?) {
                    super.onLoadStarted(placeholder)
                    coverLayout!!.setImageResource(R.drawable.rt_icon_default)
                }

                override fun onLoadFailed(errorDrawable: Drawable?) {
                    super.onLoadFailed(errorDrawable)
                    coverLayout!!.setImageResource(R.drawable.rt_icon_default)
                }

                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    coverLayout!!.setImageBitmap(resource)
                }

            })
    }
}