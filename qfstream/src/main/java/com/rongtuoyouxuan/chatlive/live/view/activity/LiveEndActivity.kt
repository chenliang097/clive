package com.rongtuoyouxuan.chatlive.live.view.activity

import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.BarUtils
import com.rongtuoyouxuan.chatlive.crtimage.util.GlideUtils
import com.rongtuoyouxuan.chatlive.crtrouter.constants.RouterConstant
import com.rongtuoyouxuan.chatlive.stream.R
import com.rongtuoyouxuan.chatlive.crtuikit.SimpleActivity
import kotlinx.android.synthetic.main.qf_stream_activity_live_end.*
import kotlinx.android.synthetic.main.qf_stream_activity_live_end.coverBg

@Route(path = RouterConstant.PATH_ACTIVITY_LIVE_END)
class LiveEndActivity : SimpleActivity() {

    override fun getLayoutResId(): Int {
        return R.layout.qf_stream_activity_live_end
    }

    override fun initData() {
        var roomId = intent.getStringExtra("roomId")
        var avatar = intent.getStringExtra("avatar")
        var nickName = intent.getStringExtra("nickName")
        GlideUtils.loadImage(this, avatar, liveEndAvatar, R.drawable.rt_default_avatar)
        liveEndNameTxt.text = nickName
        GlideUtils.loadImage(this, avatar, coverBg, R.drawable.rt_default_avatar)

    }

    override fun initListener() {
        liveEndCloseImg?.setOnClickListener{
            finish()
        }
    }

    override fun statusBarSetting() {
        BarUtils.transparentStatusBar(this)
        BarUtils.setStatusBarLightMode(this, true)
    }

}