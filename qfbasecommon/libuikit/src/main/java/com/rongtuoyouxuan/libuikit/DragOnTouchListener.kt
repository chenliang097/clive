package com.rongtuoyouxuan.libuikit

import android.content.Context
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewConfiguration
import android.view.ViewGroup
import kotlin.math.abs

class DragOnTouchListener(context: Context) : OnTouchListener {
    private var x = 0
    private var y = 0
    private var downX = 0f
    private var downY = 0f
    private var upX = 0f
    private var upY = 0f
    private var mClick = false
    private val mSlop = ViewConfiguration.get(context).scaledTouchSlop

    override fun onTouch(view: View, event: MotionEvent): Boolean {
        try {
            val parentView = view.parent as? ViewGroup ?: return false
            val parentWidth = parentView.width
            val parentHeight = parentView.height
            val width = view.width
            val height = view.height

            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    x = event.rawX.toInt()
                    downX = x.toFloat()
                    y = event.rawY.toInt()
                    downY = y.toFloat()
                    mClick = false
                }
                MotionEvent.ACTION_MOVE -> {
                    val nowX = event.rawX.toInt()
                    val nowY = event.rawY.toInt()
                    val movedX = nowX - x
                    val movedY = nowY - y
                    x = nowX
                    y = nowY
                    val left = (view.left + movedX).coerceAtMost(parentWidth - width).coerceAtLeast(0)
                    val top = (view.top + movedY).coerceAtMost(parentHeight - height).coerceAtLeast(0)
                    view.layout(left, top, left + width, top + height)
                }
                MotionEvent.ACTION_UP -> {
                    upX = event.rawX
                    upY = event.rawY
                    mClick = abs(upX - downX) > mSlop || abs(upY - downY) > mSlop
                }
                else -> {
                }
            }
            return mClick
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }
}