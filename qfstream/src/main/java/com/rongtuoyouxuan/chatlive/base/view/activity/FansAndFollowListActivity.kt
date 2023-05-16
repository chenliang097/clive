package com.rongtuoyouxuan.chatlive.base.view.activity

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
import kotlinx.android.synthetic.main.qf_stream_activity_fans_and_follow_list.*

@Route(path = RouterConstant.PATH_FANS_AND_FOLLOW_LIST)
class FansAndFollowListActivity: LanguageActivity() {
    private val fragments: MutableList<CommonFragmentInfo> = mutableListOf()
    private var userId:String? = null
    private var type = 1;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.qf_stream_activity_fans_and_follow_list)
        initListener()
        initData()
    }

    fun initListener() {
        listBackImg.setOnClickListener {
            finish()
        }

    }

    fun initData() {
        userId = intent.getStringExtra("userId")
        type  = intent.getIntExtra("type", 1)
        fragments.add(CommonFragmentInfo(getString(R.string.stream_center_fans), FansListFragment.newInstance(userId), "FansListFragment"))
        fragments.add(CommonFragmentInfo(getString(R.string.stream_center_follow), FollowListFragment.newInstance(userId), "FollowListFragment"))
        listViewpager.adapter = CommonViewPagerAdapter(this.supportFragmentManager, fragments)
        listViewpager.addOnPageChangeListener(object:ViewPager.OnPageChangeListener{
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
            .Builder(this, fragments, listViewpager, listMagicindicator)
            .build()
            .show(true)
        when(type){
            1->{listViewpager.currentItem = 0}
            2->{listViewpager.currentItem = 1}
        }
    }

}