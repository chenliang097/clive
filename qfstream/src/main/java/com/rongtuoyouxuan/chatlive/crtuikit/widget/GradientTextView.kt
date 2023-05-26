package com.rongtuoyouxuan.chatlive.crtuikit.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.LinearGradient
import android.graphics.Shader
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import com.rongtuoyouxuan.chatlive.stream.R

/**
 * 
 * date:2022/7/28-09:52
 * des: 2个color渐变的TextView--用于首页tab颜色渐变
 */
class GradientTextView : androidx.appcompat.widget.AppCompatTextView {
    private var startColor: Int = 0
    private var endColor: Int = 0

    private var defaultShader: Shader? = null
    private var linearGradient: LinearGradient? = null

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initAttrs(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        defaultShader = paint.shader
        initAttrs(context, attrs)
    }

    @SuppressLint("CustomViewStyleable")
    private fun initAttrs(context: Context, attrs: AttributeSet) {
        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.widget_GradientTextView)
        startColor = typedArray.getColor(
            R.styleable.widget_GradientTextView_wgt_startColor,
            ContextCompat.getColor(context, android.R.color.transparent)
        )
        endColor = typedArray.getColor(
            R.styleable.widget_GradientTextView_wgt_endColor,
            ContextCompat.getColor(context, android.R.color.transparent)
        )
        typedArray.recycle()
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (changed) {
            drawGradient((right - left).toFloat())
        }
    }

    fun setGradientColor(startColor: Int, endColor: Int) {
        this.startColor = ContextCompat.getColor(context, startColor)
        this.endColor = ContextCompat.getColor(context, endColor)
        drawGradient(x + width)
    }

    fun clearGradient() {
        startColor = 0
        endColor = 0
        linearGradient = null
        paint.shader = defaultShader
    }

    private fun drawGradient(x1: Float) {
        if (startColor == endColor) {
            return
        }
        linearGradient =
            LinearGradient(
                0f,
                0f,
                x1,
                0f,
                startColor,
                endColor,
                Shader.TileMode.CLAMP
            )
        paint.shader = linearGradient
    }
}