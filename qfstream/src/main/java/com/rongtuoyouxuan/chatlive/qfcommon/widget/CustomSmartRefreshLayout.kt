package com.rongtuoyouxuan.chatlive.qfcommon.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewConfiguration
import com.scwang.smart.refresh.layout.SmartRefreshLayout

/**
 *
 * date:2022/9/3-15:34
 * des: fix ViewPager与SmartRefreshLayout 左右下拉倾斜问题-灵敏度
 */
class CustomSmartRefreshLayout : SmartRefreshLayout {
    private var mInitialDownY = 0f

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        mTouchSlop = ViewConfiguration.get(context).scaledTouchSlop
        setTouchSlop(4)
    }

    override fun onInterceptHoverEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            mInitialDownY = event.y
        } else if (event.action == MotionEvent.ACTION_MOVE) {
            val yDiff = event.y - mInitialDownY
            if (yDiff < mTouchSlop) {
                return false
            }
        }
        return super.onInterceptHoverEvent(event)
    }

    fun getTouchSlop(): Int {
        return mTouchSlop
    }

    fun setTouchSlop(mTouchSlop: Int) {
        this.mTouchSlop = mTouchSlop
    }
}