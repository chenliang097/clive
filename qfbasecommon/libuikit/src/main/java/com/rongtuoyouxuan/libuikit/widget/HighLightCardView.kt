package com.rongtuoyouxuan.libuikit.widget

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewTreeObserver
import androidx.cardview.widget.CardView
import com.rongtuoyouxuan.libuikit.R

class HighLightCardView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr) {

    var animator: ObjectAnimator? = null

    private val onGlobalLayoutListener = ViewTreeObserver.OnGlobalLayoutListener {
        val highLight: View = findViewById(R.id.high_light)
        animator = ObjectAnimator.ofFloat(highLight, "translationX", -highLight.width.toFloat(), width.toFloat())
        animator?.duration = 1000
        animator?.addListener(object : Animator.AnimatorListener{
            override fun onAnimationStart(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
                animator?.start()
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationRepeat(animation: Animator?) {
            }

        })
        animator?.start()
        animator?.startDelay = 3000
        removeOnGlobalLayoutListener()
    }

    init {
        viewTreeObserver.addOnGlobalLayoutListener(onGlobalLayoutListener)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        animator?.cancel()
        animator?.removeAllListeners()
    }

    private fun removeOnGlobalLayoutListener() {
        viewTreeObserver.removeOnGlobalLayoutListener(onGlobalLayoutListener)
    }

}