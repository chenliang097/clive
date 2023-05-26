package com.rongtuoyouxuan.chatlive.crtuikit.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import androidx.annotation.Dimension
import androidx.core.content.ContextCompat
import com.google.android.material.tabs.TabLayout
import com.rongtuoyouxuan.chatlive.stream.R

/**
 * 
 * date:2022/7/28-09:52
 * des: tab样式自定义，用于首页tab/游戏tab
 */
class MainCustomTabLayout : TabLayout {
    private var defaultPosition = -1

    @Dimension(unit = Dimension.DP)
    private var unSelectedSize = 18f
    private var unSelectedColor = 0

    @Dimension(unit = Dimension.DP)
    private var selectedSize = 22f
    private var selectedColor = 0

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initAttrs(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initAttrs(context, attrs)
    }

    @SuppressLint("CustomViewStyleable")
    private fun initAttrs(context: Context, attrs: AttributeSet) {
        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.widget_MainCustomTabLayout)
        defaultPosition =
            typedArray.getInt(R.styleable.widget_MainCustomTabLayout_wmt_default_position, -1)
        unSelectedSize =
            typedArray.getDimension(R.styleable.widget_MainCustomTabLayout_wmt_unSelected_size, 18f)
        unSelectedColor = typedArray.getColor(
            R.styleable.widget_MainCustomTabLayout_wmt_unSelected_color,
            ContextCompat.getColor(context, android.R.color.black)
        )
        selectedSize =
            typedArray.getDimension(R.styleable.widget_MainCustomTabLayout_wmt_selected_size, 22f)
        selectedColor = typedArray.getColor(
            R.styleable.widget_MainCustomTabLayout_wmt_selected_color,
            ContextCompat.getColor(context, android.R.color.black)
        )
        typedArray.recycle()

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
        tab.setCustomView(R.layout.main_layout_tab_item)
        changeNormalView(tab)
        if (tab.position == defaultPosition) {
            tab.select()
        }
        return tab
    }

    private fun changeSelectedView(tab: Tab?) {
        tab?.apply {
            customView?.findViewById<GradientTextView>(android.R.id.text1)?.apply {
                textSize = selectedSize
//                setTextColor(selectedColor)
                setGradientColor(
                    R.color.c_stream_permisson_allow_end,
                    R.color.c_stream_permisson_allow_start
                )
                typeface = Typeface.DEFAULT_BOLD
            }
            customView?.findViewById<ImageView>(R.id.ivImage)?.visibility = View.VISIBLE
        }
    }

    private fun changeNormalView(tab: Tab?) {
        tab?.apply {
            customView?.findViewById<GradientTextView>(android.R.id.text1)?.apply {
                textSize = unSelectedSize
                setTextColor(unSelectedColor)
                clearGradient()
                typeface = Typeface.DEFAULT
            }
            customView?.findViewById<ImageView>(R.id.ivImage)?.visibility = View.INVISIBLE
        }
    }
}