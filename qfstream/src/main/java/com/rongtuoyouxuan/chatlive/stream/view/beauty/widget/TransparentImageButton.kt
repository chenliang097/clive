package com.rongtuoyouxuan.chatlive.stream.view.beauty.widget

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.MotionEvent

/**
 * Transparent view for image button and text
 */
class TransparentImageButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : androidx.appcompat.widget.AppCompatImageButton(context, attrs, defStyleAttr) {

    init {
        // 背景设置为透明
        background?.apply {
            alpha = 0
        }
    }

    // 设置点击图标效果
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event != null) {
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    setColorFilter(Color.GRAY)
                }
                MotionEvent.ACTION_UP -> {
                    clearColorFilter()
                    if(isClickable && isInViewArea(event.x,event.y)){
                        performClick()
                    }
                }
            }
            return true
        }
        return false
    }

    /**
     * 判断是否在本View的区域内
     */
    private fun isInViewArea(x:Float, y:Float): Boolean {
        return x >= 0 && x <= width && y >= 0 && y <= height
    }

    override fun performClick(): Boolean {
        return super.performClick()
    }
}