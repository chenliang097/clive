package com.rongtuoyouxuan.chatlive.live.view.dialog

import android.annotation.SuppressLint
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.rongtuoyouxuan.chatlive.base.utils.LiveRoomHelper
import com.rongtuoyouxuan.chatlive.crtdatabus.DataBus
import com.rongtuoyouxuan.chatlive.live.view.activity.ContributionTabFragment
import com.rongtuoyouxuan.chatlive.live.view.activity.MemberFragment
import com.rongtuoyouxuan.chatlive.stream.R
import com.google.android.material.tabs.TabLayoutMediator
import com.lxj.xpopup.core.BottomPopupView
import kotlinx.android.synthetic.main.dialog_contribution_member_layout.view.*

/**
 * 
 * date:2022/8/9-19:15
 * des: 贡献榜/成员列表
 */
@SuppressLint("ViewConstructor")
class ContributionMemberDialog(
    val activity: FragmentActivity,
    val liveId: Long,
    val anchorId: String? = "",
    val num: Long = 0,
    val position: Int = 0
) : BottomPopupView(activity) {

    private val tabs = arrayListOf(
        R.string.live_dialog_contribution_tab,
        R.string.live_dialog_memeber_tab,
    )

    override fun getImplLayoutId(): Int = R.layout.dialog_contribution_member_layout

    override fun onCreate() {
        super.onCreate()
        val loginUID = DataBus.instance().userInfo.value?.user_info?.userId ?: 0L
        vpLayout?.apply {
            adapter = object : FragmentStateAdapter(activity) {
                override fun getItemCount() = tabs.size

                override fun createFragment(position: Int): Fragment {
                    return if (position == 0) {
                        ContributionTabFragment.newInstance(
                            anchorId?.toLongOrNull() ?: 0L,
                            loginUID
                        )
                    } else {
                        MemberFragment.newInstance("$liveId", anchorId, loginUID)
                    }
                }
            }
            TabLayoutMediator(rootView.tabLayout, this) { tab, position ->
                if (position == 0) {
                    tab.text = activity.getString(tabs[position])
                } else {
                    tab.text = activity.getString(tabs[position], if (num > 999) "999+" else "$num")
                }
            }.attach()

            offscreenPageLimit = tabs.size
            currentItem = position
        }

        LiveRoomHelper.cmDismissVM.observe(activity) {
            dismiss()
        }

        LiveRoomHelper.cmMemberTabVM.observe(activity) {
            tabLayout?.getTabAt(1)?.text =
                activity.getString(tabs[1], if (num > 999) "999+" else "$it")
        }
    }
}