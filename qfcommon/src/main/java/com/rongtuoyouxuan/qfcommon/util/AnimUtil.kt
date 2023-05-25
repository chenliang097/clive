package com.rongtuoyouxuan.qfcommon.util

import android.animation.*
import android.view.View
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.widget.ImageView


/**
 *
 * date:2022/8/1-11:43
 * des:
 */
object AnimUtil {

    //放大
    fun viewScaleLarge(
        v: View,
        value: Float,
        duration: Long = 200L,
        onCallAnimationEnd: () -> Unit
    ) {

        val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 1f, value, 1f)
        val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 1f, value, 1f)
        val objectAnimator = ObjectAnimator.ofPropertyValuesHolder(v, scaleX, scaleY)
        val animatorSet = AnimatorSet()
        animatorSet.play(objectAnimator)
        animatorSet.duration = duration
        animatorSet.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(p0: Animator) {
            }

            override fun onAnimationEnd(p0: Animator) {
                onCallAnimationEnd()
                animatorSet.removeListener(this)
            }

            override fun onAnimationCancel(p0: Animator) {
            }

            override fun onAnimationRepeat(p0: Animator) {
            }

        })
        animatorSet.start()
    }

    //缩小
    fun viewScaleSmall(v: View, value: Float) {
        val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 1f, value, 1f)
        val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 1f, value, 1f)
        val objectAnimator = ObjectAnimator.ofPropertyValuesHolder(v, scaleX, scaleY)
        val animatorSet = AnimatorSet()
        animatorSet.play(objectAnimator)
        animatorSet.duration = 400L
        animatorSet.start()
    }

    //礼物通道进入-左到右
    fun startInAnimation(view: View, width: Float, end: () -> Unit): ObjectAnimator {
        val animator = ObjectAnimator.ofFloat(
            view,
            View.TRANSLATION_X,
            -width, 0f
        )
        animator.duration = 500L
        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                end()
                animator.removeListener(this)
            }
        })
        animator.start()
        return animator
    }

    //礼物通道出去-右到左
    fun startOutAnimation(view: View, width: Float, end: () -> Unit): ObjectAnimator {
        val animator = ObjectAnimator.ofFloat(
            view,
            View.TRANSLATION_X,
            0f, -width
        )
        animator.duration = 500L
        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                end()
                animator.removeListener(this)
            }
        })
        animator.start()
        return animator
    }

    //座驾-从右到左
    fun startCarInAnimation(view: View, width: Float, end: () -> Unit): ObjectAnimator {
        val animator = ObjectAnimator.ofFloat(
            view,
            View.TRANSLATION_X,
            width, 0f
        )
        animator.duration = 600L
        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                end()
                animator.removeListener(this)
            }
        })
        animator.start()
        return animator
    }

    //座驾-从右到左出去
    fun startCarOutAnimation(view: View, width: Float, end: () -> Unit): ObjectAnimator {
        val animator = ObjectAnimator.ofFloat(
            view,
            View.TRANSLATION_X,
            0f, -width
        )
        animator.duration = 600L
        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                end()
                animator.removeListener(this)
            }
        })
        animator.start()
        return animator
    }

    //礼物通道-数字-X1
    fun startNumberAnimation(view: View): ObjectAnimator {
        val translationY = PropertyValuesHolder.ofFloat(View.SCALE_X, 1f, 1.8f, 0.6f, 1.2f, 1.0f)
        val translationX = PropertyValuesHolder.ofFloat(View.SCALE_Y, 1f, 1.8f, 0.6f, 1.2f, 1.0f)
        val numberAnim =
            ObjectAnimator.ofPropertyValuesHolder(view, translationY, translationX)
        numberAnim.duration = 500L
        numberAnim.startDelay = 100L
        numberAnim.start()
        return numberAnim
    }

    //动画Listener监听
    fun startAnim(animator: Animation?, end: () -> Unit) {
        animator?.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {

            }

            override fun onAnimationEnd(animation: Animation?) {
                end()
            }

            override fun onAnimationRepeat(animation: Animation?) {
            }
        })
    }

    fun startAnim(animator: ObjectAnimator?, end: () -> Unit) {
        animator?.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                end()
            }
        })
    }

    //view无限旋转
    fun infiniteRotation(view: ImageView): ObjectAnimator {
        val objectAnimator: ObjectAnimator = ObjectAnimator.ofFloat(view, View.ROTATION, 0f, 359f)
        objectAnimator.repeatCount = ValueAnimator.INFINITE
        objectAnimator.duration = 1000L
        objectAnimator.interpolator = LinearInterpolator()
        objectAnimator.start()
        return objectAnimator
    }
}