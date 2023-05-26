package com.rongtuoyouxuan.chatlive.qfcommon.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.FrameLayout

/**
 * 
 * date:2022/8/24-17:04
 * des:
 */
class UnClickFrameLayout : FrameLayout {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        return true
    }
}