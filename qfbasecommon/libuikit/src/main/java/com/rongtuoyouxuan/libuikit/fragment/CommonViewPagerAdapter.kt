package com.rongtuoyouxuan.libuikit.fragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
/**
 * 保存fragment状态的adapter
 */
class CommonViewPagerAdapter(fm: FragmentManager, fragmentInfos: List<CommonFragmentInfo>) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private var mFragments = fragmentInfos

    override fun getCount(): Int = mFragments.size

    override fun getItem(position: Int): Fragment {
        return mFragments[position].fragment
    }
}