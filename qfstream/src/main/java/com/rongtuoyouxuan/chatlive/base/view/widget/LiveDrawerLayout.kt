package com.rongtuoyouxuan.chatlive.base.view.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.drawerlayout.widget.DrawerLayout
import com.rongtuoyouxuan.chatlive.crtlog.upload.ULog

/**
 *
 * date:2022/10/8-17:34
 * des:解决观众端点赞冲突
 */
class LiveDrawerLayout : DrawerLayout {
    private var callBack: (() -> Unit)? = null
    private var downX = 0f
    private var downY = 0f
    private var moveX = 0f

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        ULog.d(
            "LiveDrawerLayout",
            ">>>dispatchTouchEvent>>>${event.action}>>>"
        )
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                downX = event.x
                downY = event.y
            }
            MotionEvent.ACTION_MOVE -> {
                moveX = event.x
            }
            MotionEvent.ACTION_UP -> {
                if (moveX > 0f) {
                    val moveDiff = downX - moveX
                    if (moveDiff > 250f) {
                        //左滑推荐
                        callBack?.invoke()
                    }
                }

                moveX = 0f
                downX = 0f
                downY = 0f
            }
        }
        return super.dispatchTouchEvent(event)
    }

    fun setRightToLeftCallBack(callBack: (() -> Unit)?) {
        this.callBack = callBack
    }
}