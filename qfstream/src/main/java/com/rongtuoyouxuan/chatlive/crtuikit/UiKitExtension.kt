@file:JvmName("UiKitExtension")

package com.rongtuoyouxuan.chatlive.crtuikit

import android.animation.Animator
import android.animation.TimeInterpolator
import android.animation.ValueAnimator
import android.content.res.Resources
import android.util.TypedValue
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.viewpager2.widget.ViewPager2

/**
 *  eg.  18.dp
 */
val Float.dp
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this,
        Resources.getSystem().displayMetrics
    )

val Int.dp
    get() = this.toFloat().dp


fun ViewPager2.setCurrentItemFixDuration(
        item: Int,
        duration: Long,
        interpolator: TimeInterpolator = AccelerateDecelerateInterpolator(),
        pagePxWidth: Int = width
) {
    val pxToDrag: Int = pagePxWidth * (item - currentItem)
    val animator = ValueAnimator.ofInt(0, pxToDrag)
    var previousValue = 0
    animator.addUpdateListener { valueAnimator ->
        val currentValue = valueAnimator.animatedValue as Int
        val currentPxToDrag = (currentValue - previousValue).toFloat()
        fakeDragBy(-currentPxToDrag)
        previousValue = currentValue
    }
    animator.addListener(object : Animator.AnimatorListener {

        override fun onAnimationStart(p0: Animator) {
            beginFakeDrag()
        }

        override fun onAnimationEnd(p0: Animator) {
            endFakeDrag()
        }

        override fun onAnimationCancel(p0: Animator) {
        }

        override fun onAnimationRepeat(p0: Animator) {
        }
    })
    animator.interpolator = interpolator
    animator.duration = duration
    animator.start()
}