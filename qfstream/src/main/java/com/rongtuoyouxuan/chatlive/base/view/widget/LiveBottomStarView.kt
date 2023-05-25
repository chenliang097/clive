package com.rongtuoyouxuan.chatlive.base.view.widget

import android.animation.*
import android.content.Context
import android.graphics.PointF
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import com.rongtuoyouxuan.chatlive.stream.R
import com.rongtuoyouxuan.libuikit.dp
import kotlin.random.Random

/**
 * 
 * date:2022/8/8-19:04
 * des: 直播间下方-点赞特效
 */
class LiveBottomStarView : RelativeLayout {
    private val imageList: List<Int> = arrayListOf(
        R.drawable.icon_star_anim_1,
        R.drawable.icon_star_anim_2,
        R.drawable.icon_star_anim_3,
        R.drawable.icon_star_anim_4,
        R.drawable.icon_star_anim_5,
        R.drawable.icon_star_anim_6,
        R.drawable.icon_star_anim_7,
        R.drawable.icon_star_anim_8
    )

    private var currentWidth = 0
    private var currentHeight = 0
    private var currentLeft = 0
    private var currentBottom = 0

    private var lpImageView: LayoutParams? = null

    private val imageWH = 25.dp.toInt()

    init {
        lpImageView = LayoutParams(imageWH, imageWH)
        lpImageView?.addRule(CENTER_HORIZONTAL, TRUE)
        lpImageView?.addRule(ALIGN_PARENT_BOTTOM, TRUE)
    }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        currentLeft = l
        currentBottom = b
        currentWidth = width
        currentHeight = height
    }

    fun addChildView() {
        val imageId = imageList.random()
        val imageView = ImageView(context)
        imageView.setImageResource(imageId)
        addView(imageView, lpImageView)
        jitterUserIcon(imageView)
    }

    private fun jitterUserIcon(view: View) {
//        val tranX = Random.nextInt(20).toFloat()
//        val translationX = PropertyValuesHolder.ofKeyframe(
//            View.TRANSLATION_X,
//            Keyframe.ofFloat(0f, 0f),
//            Keyframe.ofFloat(0.1f, 0f),
//            Keyframe.ofFloat(0.2f, tranX),
//            Keyframe.ofFloat(0.3f, -tranX),
//            Keyframe.ofFloat(0.4f, tranX),
//            Keyframe.ofFloat(0.5f, -tranX),
//            Keyframe.ofFloat(0.6f, tranX),
//            Keyframe.ofFloat(0.7f, -tranX),
//            Keyframe.ofFloat(0.8f, tranX),
//            Keyframe.ofFloat(0.9f, -tranX),
//            Keyframe.ofFloat(1.0f, 0f)
//        )
//
//        val translationY = PropertyValuesHolder.ofKeyframe(
//            View.TRANSLATION_Y,
//            Keyframe.ofFloat(0f, 0f),
//            Keyframe.ofFloat(0.1f, -currentHeight * 0.1f),
//            Keyframe.ofFloat(0.2f, -currentHeight * 0.2f),
//            Keyframe.ofFloat(0.3f, -currentHeight * 0.3f),
//            Keyframe.ofFloat(0.4f, -currentHeight * 0.4f),
//            Keyframe.ofFloat(0.5f, -currentHeight * 0.5f),
//            Keyframe.ofFloat(0.6f, -currentHeight * 0.6f),
//            Keyframe.ofFloat(0.7f, -currentHeight * 0.7f),
//            Keyframe.ofFloat(0.8f, -currentHeight * 0.8f),
//            Keyframe.ofFloat(0.9f, -currentHeight * 0.9f),
//            Keyframe.ofFloat(1.0f, -currentHeight.toFloat())
//        )
//
//        val scaleX = PropertyValuesHolder.ofKeyframe(
//            View.SCALE_X,
//            Keyframe.ofFloat(0f, 1f),
//            Keyframe.ofFloat(0.2f, 1.2f),
//            Keyframe.ofFloat(0.4f, 1.4f),
//            Keyframe.ofFloat(0.6f, 1.6f),
//            Keyframe.ofFloat(0.8f, 1.8f),
//            Keyframe.ofFloat(1f, 2f),
//        )
//
//        val scaleY = PropertyValuesHolder.ofKeyframe(
//            View.SCALE_Y,
//            Keyframe.ofFloat(0f, 1f),
//            Keyframe.ofFloat(0.2f, 1.2f),
//            Keyframe.ofFloat(0.4f, 1.4f),
//            Keyframe.ofFloat(0.6f, 1.6f),
//            Keyframe.ofFloat(0.8f, 1.8f),
//            Keyframe.ofFloat(1f, 2f),
//        )
//
//        val alpha = PropertyValuesHolder.ofKeyframe(
//            View.ALPHA,
//            Keyframe.ofFloat(0f, 1f),
//            Keyframe.ofFloat(0.2f, 0.8f),
//            Keyframe.ofFloat(0.4f, 0.6f),
//            Keyframe.ofFloat(0.6f, 0.4f),
//            Keyframe.ofFloat(0.8f, 0.2f),
//            Keyframe.ofFloat(1f, 0f),
//        )
//
//        val objectAnimator =
//            ObjectAnimator.ofPropertyValuesHolder(
//                view,
//                translationX,
//                translationY,
//                scaleX,
//                scaleY,
//                alpha
//            )
//        objectAnimator.duration = 2000
//        objectAnimator.addListener(object : AnimatorListenerAdapter() {
//            override fun onAnimationEnd(animation: Animator) {
//                removeView(view)
//                objectAnimator.removeListener(this)
//            }
//        })
//        objectAnimator.start()


        val scaleXAnim = ObjectAnimator.ofFloat(view, View.SCALE_X, 1f, 2f)
        val scaleYAnim = ObjectAnimator.ofFloat(view, View.SCALE_Y, 1f, 2f)
        val alphaAnim = ObjectAnimator.ofFloat(view, View.ALPHA, 1f, 0.5f)

        val animatorSet = AnimatorSet()

        val centerX = ((width - imageWH) / 2)
        val point0 = PointF(centerX.toFloat() + 25f, height.toFloat())
        val direction = 1 - (2 * Random.nextInt(2))
        val random20 = Random.nextInt(20)
        val point1 = PointF(((direction * centerX) + random20).toFloat(), 0f)
        val point2 = PointF(Random.nextInt(centerX).toFloat() + 10f, 0f)
        val evaluator = BezierEvaluator(point1)
        val animator = ValueAnimator.ofObject(evaluator, point0, point2)
        animator.addUpdateListener(BezierListener(view))

        animatorSet.setTarget(view)
        animatorSet.duration = 2000
        animatorSet.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                removeView(view)
                animatorSet.removeListener(this)
            }
        })
        animatorSet.playTogether(animator, scaleXAnim, scaleYAnim, alphaAnim)
        animatorSet.start()
    }

    inner class BezierEvaluator(private val point1: PointF) :
        TypeEvaluator<PointF> {
        override fun evaluate(t: Float, point0: PointF, point2: PointF): PointF {
            val pointCur = PointF()
            pointCur.x = point0.x * (1 - t) + point1.x * t + point2.x * t
            pointCur.y = point0.y * (1 - t) + point2.y * t
            return pointCur
        }
    }

    inner class BezierListener(private val target: View) : ValueAnimator.AnimatorUpdateListener {

        override fun onAnimationUpdate(animation: ValueAnimator) {
            val pointF = animation.animatedValue as PointF
            target.x = pointF.x
            target.y = pointF.y
        }
    }
}