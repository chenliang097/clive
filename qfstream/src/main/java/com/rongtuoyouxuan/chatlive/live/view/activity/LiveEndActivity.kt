package com.rongtuoyouxuan.chatlive.live.view.activity

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.BarUtils
import com.rongtuoyouxuan.chatlive.image.ImgLoader
import com.rongtuoyouxuan.chatlive.image.util.GlideUtils
import com.rongtuoyouxuan.chatlive.live.viewmodel.LiveEndViewModel
import com.rongtuoyouxuan.chatlive.net2.BaseModel
import com.rongtuoyouxuan.chatlive.router.constants.RouterConstant
import com.rongtuoyouxuan.chatlive.stream.R
import com.rongtuoyouxuan.libuikit.SimpleActivity
import com.rongtuoyouxuan.chatlive.util.LaToastUtil
import kotlinx.android.synthetic.main.qf_stream_activity_live_end.*
import kotlinx.android.synthetic.main.qf_stream_activity_live_end.coverBg

@Route(path = RouterConstant.PATH_ACTIVITY_LIVE_END)
class LiveEndActivity : SimpleActivity() {

    private var anchorId:Long? = null
    private var liveEndViewModel:LiveEndViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initObserver()
    }

    override fun getLayoutResId(): Int {
        return R.layout.qf_stream_activity_live_end
    }

    override fun initData() {
        var streamId = intent.getStringExtra("streamId")
        anchorId = intent.getLongExtra("anchorId", 0)
        var avatar = intent.getStringExtra("avatar")
        var nickName = intent.getStringExtra("nickName")
        var time = intent.getLongExtra("time", 0)
        var followStatus = intent.getBooleanExtra("follow", false)
        var cover = intent.getStringExtra("pic")
        ImgLoader.with(this).load(avatar).into(liveEndAvatar)
        liveEndNameTxt.text = nickName
        liveEndTimeTxt?.text = getString(R.string.stream_live_time, secondToTime(time))
        when(followStatus){
            true->liveEndFollowTxt?.visibility = View.GONE
            false->liveEndFollowTxt?.visibility = View.VISIBLE
        }
        if(!TextUtils.isEmpty(cover)){
            GlideUtils.loadBlurImage(this, cover, coverBg, 25)
        }else{
            GlideUtils.loadBlurImage(this, cover, coverBg, 25)

        }

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
        liveEndCloseImg?.setOnClickListener{
            finish()
        }
    }

    private fun initObserver(){
        liveEndViewModel = ViewModelProviders.of(this).get(LiveEndViewModel::class.java)
        liveEndViewModel?.followAddLiveData?.observe(this, object :Observer<BaseModel>{
            override fun onChanged(t: BaseModel?) {
                if(t?.errCode == 0){
                    liveEndFollowTxt.text = resources.getString(R.string.stream_user_card_delete_folowed)
                }else{
                    LaToastUtil.showShort(t?.errMsg)
                }
            }

        })
    }

    override fun statusBarSetting() {
        BarUtils.transparentStatusBar(this)
        BarUtils.setStatusBarLightMode(this, true)
    }

}