package com.rongtuoyouxuan.chatlive.crtgift.widget

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.rongtuoyouxuan.chatlive.stream.R

/**
 * 
 * date:2022/8/2-15:02
 * des: 礼物通道,可自定义通道数量
 */
class GiftSidebarLinearLayout : LinearLayout {

    //通道数量
    private var sidebarNum = 2

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
            context.obtainStyledAttributes(attrs, R.styleable.widget_GiftSidebarLaneLayout)
        sidebarNum = typedArray.getInt(R.styleable.widget_GiftSidebarLaneLayout_wgl_side_num, 2)

        typedArray.recycle()
    }

    fun getSideNum() = sidebarNum

    fun releaseAll() {
        removeAllViews()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        releaseAll()
    }
}