package com.rongtuoyouxuan.chatlive.crtgift.view.layout

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.LinearLayout
import androidx.viewpager2.widget.ViewPager2

/**
 * 
 * date:2022/10/8-17:34
 * des:采用内部拦截法解决礼物ViewPager2嵌套ViewPager2--连续滑动
 */
class GiftLLayout : LinearLayout {

    private var vp: ViewPager2? = null

    private var downX = 0f
    private var moveX = 0f

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun onFinishInflate() {
        super.onFinishInflate()
        for (i in 0 until childCount) {
            val childView = getChildAt(i)
            if (childView is ViewPager2) {
                vp = childView
                break
            }
        }
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        val isIntercept = (null != vp && vp!!.adapter != null
                && vp!!.adapter!!.itemCount <= 1)
        if (isIntercept) {
            return super.onInterceptTouchEvent(event)
        }

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                downX = event.x
                parent.requestDisallowInterceptTouchEvent(false)
            }
            MotionEvent.ACTION_MOVE -> {
                moveX = event.x

                val currentItem = vp?.currentItem ?: 0
                val itemCount = vp?.adapter?.itemCount ?: 0

                if (downX < moveX) {
                    if (currentItem == 0) {
                        parent.requestDisallowInterceptTouchEvent(false)
                    } else {
                        parent.requestDisallowInterceptTouchEvent(true)
                    }
                } else {
                    if (currentItem == itemCount - 1) {
                        parent.requestDisallowInterceptTouchEvent(false)
                    } else {
                        parent.requestDisallowInterceptTouchEvent(true)
                    }
                }
            }
            else -> {
                downX = 0f
                moveX = 0f
                parent.requestDisallowInterceptTouchEvent(false)
            }
        }
        return super.onInterceptTouchEvent(event)
    }
}