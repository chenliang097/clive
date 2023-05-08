package com.rongtuoyouxuan.chatlive.live.view.activity

import android.text.TextUtils
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.BarUtils
import com.rongtuoyouxuan.chatlive.image.ImgLoader
import com.rongtuoyouxuan.chatlive.image.util.GlideUtils
import com.rongtuoyouxuan.chatlive.router.constants.RouterConstant
import com.rongtuoyouxuan.chatlive.stream.R
import com.rongtuoyouxuan.libuikit.SimpleActivity
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
        ImgLoader.with(this).load(avatar).into(liveEndAvatar)
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