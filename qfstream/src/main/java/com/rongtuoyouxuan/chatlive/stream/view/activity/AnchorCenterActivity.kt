package com.rongtuoyouxuan.chatlive.stream.view.activity

import android.os.Bundle
import android.view.Gravity
import androidx.viewpager.widget.ViewPager
import com.alibaba.android.arouter.facade.annotation.Route
import com.app.hubert.guide.listener.OnPageChangedListener
import com.blankj.utilcode.util.BarUtils
import com.rongtuoyouxuan.chatlive.router.Router
import com.rongtuoyouxuan.chatlive.router.constants.RouterConstant
import com.rongtuoyouxuan.chatlive.stream.R
import com.rongtuoyouxuan.chatlive.stream.view.fragment.AnchorCenterFragment
import com.rongtuoyouxuan.libuikit.LanguageActivity
import com.rongtuoyouxuan.libuikit.SimpleActivity
import com.rongtuoyouxuan.libuikit.fragment.CommonFragmentInfo
import com.rongtuoyouxuan.libuikit.fragment.CommonMagicIndicatorHelper
import com.rongtuoyouxuan.libuikit.fragment.CommonViewPagerAdapter
import kotlinx.android.synthetic.main.qf_stream_activity_anchor_center.*
import kotlinx.android.synthetic.main.qf_stream_activity_end.*
import kotlinx.android.synthetic.main.qf_stream_activity_end.streamEndCloseImg
import net.lucode.hackware.magicindicator.MagicIndicator

@Route(path = RouterConstant.PATH_ANHOR_CENTER)
class AnchorCenterActivity: SimpleActivity() {
    private val fragments: MutableList<CommonFragmentInfo> = mutableListOf()
    private var streamId:String? = null
    private var anchorId:String? = null

    override fun getLayoutResId(): Int {
        return R.layout.qf_stream_activity_anchor_center
    }

    override fun initListener() {
        streamEndCloseImg.setOnClickListener {
            finish()
        }

        anchorIdToStartTxt.setOnClickListener {
            Router.toStreamActivity()
        }

    }

    override fun initData() {
        streamId = intent.getStringExtra("streamId")
        anchorId = intent.getStringExtra("anchorId")
        fragments.add(CommonFragmentInfo(getString(R.string.anchor_center_d), AnchorCenterFragment.newInstance(1), "AnchorCenterFragment"))
        fragments.add(CommonFragmentInfo(getString(R.string.anchor_center_w), AnchorCenterFragment.newInstance(7), "AnchorCenterFragment"))
        fragments.add(CommonFragmentInfo(getString(R.string.anchor_center_m), AnchorCenterFragment.newInstance(30), "AnchorCenterFragment"))
        anchorCenterViewpager.adapter = CommonViewPagerAdapter(this.supportFragmentManager, fragments)
        anchorCenterViewpager.addOnPageChangeListener(object:ViewPager.OnPageChangeListener{
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {

            }

            override fun onPageScrollStateChanged(state: Int) {
            }

        })
        CommonMagicIndicatorHelper
            .Builder(this, fragments, anchorCenterViewpager, anchorCenterMagicindicator)
            .build()
            .show(true)
    }

    override fun statusBarSetting() {
        BarUtils.transparentStatusBar(this)
        BarUtils.setStatusBarLightMode(this, true)
    }

}