package com.rongtuoyouxuan.libuikit.widget

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.rongtuoyouxuan.libuikit.R
import com.google.android.material.tabs.TabLayout

/**
 * 
 * date:2022/7/28-09:52
 * des: tab样式自定义，用于贡献榜/成员列表
 */
class CmTabLayout : TabLayout {
    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initAttrs()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initAttrs()
    }

    @SuppressLint("CustomViewStyleable")
    private fun initAttrs() {
        addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: Tab?) {
                changeSelectedView(tab)
            }

            override fun onTabUnselected(tab: Tab?) {
                changeNormalView(tab)
            }

            override fun onTabReselected(tab: Tab?) {
            }
        })
    }

    override fun newTab(): Tab {
        val tab = super.newTab()
        tab.setCustomView(R.layout.cm_layout_tab_item)
        changeNormalView(tab)
        return tab
    }

    private fun changeSelectedView(tab: Tab?) {
        tab?.apply {
            customView?.findViewById<View>(R.id.ivImage)?.visibility = View.VISIBLE
        }
    }

    private fun changeNormalView(tab: Tab?) {
        tab?.apply {
            customView?.findViewById<View>(R.id.ivImage)?.visibility = View.INVISIBLE
        }
    }
}