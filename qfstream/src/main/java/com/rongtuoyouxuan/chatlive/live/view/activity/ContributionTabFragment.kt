package com.rongtuoyouxuan.chatlive.live.view.activity

import android.os.Bundle
import com.rongtuoyouxuan.chatlive.stream.R
import com.rongtuoyouxuan.chatlive.crtuikit.SimpleFragment
import kotlinx.android.synthetic.main.fragment_contribution_tab.*

/**
 * 
 * date:2022/8/9-19:28
 * des: 贡献榜--日榜，月榜，总榜
 */
class ContributionTabFragment : SimpleFragment() {
    private val fragments = arrayListOf<SimpleFragment>()

    companion object {
        fun newInstance(anchorId: Long = 0, userId: Long) = ContributionTabFragment().apply {
            arguments = Bundle().apply {
                putLong("anchorId", anchorId)
                putLong("userId", userId)
            }
        }
    }

    override fun getLayoutResId(): Int = R.layout.fragment_contribution_tab

    override fun initData() {
        tvDay?.isSelected = true
        tvMonth?.isSelected = false
        tvTotal?.isSelected = false

        addFragment()
        showFragment(0)
    }

    override fun initListener() {
        tvDay?.setOnClickListener {
            tvDay?.isSelected = true
            tvMonth?.isSelected = false
            tvTotal?.isSelected = false

            showFragment(0)
        }
        tvMonth?.setOnClickListener {
            tvDay?.isSelected = false
            tvMonth?.isSelected = true
            tvTotal?.isSelected = false

            showFragment(1)
        }

        tvTotal?.setOnClickListener {
            tvDay?.isSelected = false
            tvMonth?.isSelected = false
            tvTotal?.isSelected = true

            showFragment(2)
        }
    }

    private fun addFragment() {
        val anchorId = arguments?.getLong("anchorId", 0L) ?: 0L
        val userId = arguments?.getLong("userId", 0L) ?: 0L
        fragments.add(ContributionFragment.newInstance(0, anchorId, userId))
        fragments.add(ContributionFragment.newInstance(1, anchorId, userId))
        fragments.add(ContributionFragment.newInstance(2, anchorId, userId))
        val transaction = childFragmentManager.beginTransaction()
        fragments.forEach {
            if (!it.isAdded) {
                transaction.add(R.id.flContainer, it)
                    .hide(it)
            }
        }
        transaction.commitAllowingStateLoss()
    }

    private fun showFragment(position: Int) {
        val transaction = childFragmentManager.beginTransaction()
        fragments.forEachIndexed { index, fragment ->
            if (position == index) {
                transaction.show(fragment)
            } else {
                transaction.hide(fragment)
            }
        }
        transaction.commitAllowingStateLoss()
    }
}