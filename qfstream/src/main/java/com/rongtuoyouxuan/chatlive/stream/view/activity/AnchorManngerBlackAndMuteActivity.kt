package com.rongtuoyouxuan.chatlive.stream.view.activity

import android.os.Bundle
import android.view.Gravity
import android.view.WindowManager
import androidx.viewpager.widget.ViewPager
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.BarUtils
import com.rongtuoyouxuan.chatlive.router.constants.RouterConstant
import com.rongtuoyouxuan.chatlive.stream.R
import com.rongtuoyouxuan.chatlive.stream.view.fragment.AnchorManagerBlackListFragment
import com.rongtuoyouxuan.chatlive.stream.view.fragment.AnchorManagerMuteListFragment
import com.rongtuoyouxuan.chatlive.util.UIUtils
import com.rongtuoyouxuan.libuikit.LanguageActivity
import com.rongtuoyouxuan.libuikit.SimpleActivity
import com.rongtuoyouxuan.libuikit.fragment.CommonFragmentInfo
import com.rongtuoyouxuan.libuikit.fragment.CommonMagicIndicatorHelper
import com.rongtuoyouxuan.libuikit.fragment.CommonViewPagerAdapter
import kotlinx.android.synthetic.main.qf_stream_activity_anchor_black_and_mute.*

@Route(path = RouterConstant.PATH_ANHOR_MANANGER_BLACK_MUTE)
class AnchorManngerBlackAndMuteActivity: LanguageActivity() {
    private val fragments: MutableList<CommonFragmentInfo> = mutableListOf()
    private var roomId:String? = null
    private var sceneId:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.qf_stream_activity_anchor_black_and_mute)
        setWindowLocation()
        initListener()
        initData()
    }

    private fun setWindowLocation() {
        try {
            val win = this.window
            win.decorView.setPadding(0, 0, 0, 0)
            val lp = win.attributes
            lp.width = WindowManager.LayoutParams.MATCH_PARENT
            lp.height = (UIUtils.screenHeight(this) * 0.5).toInt()
            lp.gravity = Gravity.BOTTOM //设置对话框底部显示
            win.attributes = lp
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun initListener() {
        anchorManagerBackImg.setOnClickListener {
            finish()
        }

    }

    fun initData() {
        roomId = intent.getStringExtra("roomId")
        sceneId = intent.getStringExtra("sceneId")
        fragments.add(CommonFragmentInfo(getString(R.string.stream_anchor_manager_black), AnchorManagerBlackListFragment.newInstance(roomId, sceneId), "AnchorManagerBlackListFragment"))
        fragments.add(CommonFragmentInfo(getString(R.string.stream_anchor_manager_mute), AnchorManagerMuteListFragment.newInstance(roomId, sceneId), "AnchorManagerMuteListFragment"))
        anchorManagerViewpager.adapter = CommonViewPagerAdapter(this.supportFragmentManager, fragments)
        anchorManagerViewpager.addOnPageChangeListener(object:ViewPager.OnPageChangeListener{
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
            .Builder(this, fragments, anchorManagerViewpager, anchorManagerMagicindicator)
            .build()
            .show(true)
    }

}