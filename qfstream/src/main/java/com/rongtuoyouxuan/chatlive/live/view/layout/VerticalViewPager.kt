package com.rongtuoyouxuan.chatlive.live.view.layout

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager
import com.rongtuoyouxuan.chatlive.crtlog.upload.ULog

/**
 * 
 * date:2022/10/11-17:00
 * des:上下滑动的ViewPager
 */
class VerticalViewPager : ViewPager {

    var canSwipe: Boolean = true

    var helper: TouchEventHelper? = null

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    private fun init() {
        overScrollMode = OVER_SCROLL_NEVER
    }

    var downY = 0F
    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        ULog.d("TouchEvent", ">>>VerticalViewPager onInterceptTouchEvent>>>${ev.action}")
        if (!canSwipe) {
            if (ev.action == MotionEvent.ACTION_DOWN) {
                downY = ev.y
            } else if (ev.action == MotionEvent.ACTION_MOVE) {
                if ((downY - ev.y) > 50) {
                    helper?.canNotSwipeEvent()
                }
            }
            return false
        }
        val intercepted = super.onInterceptTouchEvent(swapXY(ev))
        swapXY(ev)
        return intercepted
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(ev: MotionEvent): Boolean {
        ULog.d("TouchEvent", ">>>VerticalViewPager onTouchEvent>>>${ev.action}")
        if (!canSwipe) return false
        return super.onTouchEvent(swapXY(ev))
    }

    private fun swapXY(event: MotionEvent): MotionEvent {
//        val width = width.toFloat()
//        val height = height.toFloat()
        val newX = event.y
        val newY = event.x
        event.setLocation(newX, newY)
        return event
    }

    interface TouchEventHelper {
        fun canNotSwipeEvent()
    }
}