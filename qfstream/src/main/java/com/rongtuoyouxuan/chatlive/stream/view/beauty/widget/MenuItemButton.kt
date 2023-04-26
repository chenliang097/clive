package com.rongtuoyouxuan.chatlive.stream.view.beauty.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.graphics.RadialGradient
import android.graphics.Shader
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import com.rongtuoyouxuan.chatlive.stream.R
/**
 * author: qingyingliu
 * date: 3/8/21
 */
class MenuItemButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : androidx.appcompat.widget.AppCompatImageButton(context, attrs, defStyleAttr) {

    private val thinness = resources.getDimension(R.dimen.dp_2)
    private val borderRadius = resources.getDimension(R.dimen.dp_4)

    var neededDrawBoarder = false
    var isCircle = false

    private val paint = Paint().also {
        it.alpha = 255 / 2
        //it.color = Color.RED
        //it.shader = gradient
        it.style = Paint.Style.STROKE
        it.strokeWidth = thinness
        it.flags = ANTI_ALIAS_FLAG
        it.isAntiAlias = true
        it.alpha = 255
    }

    private val startColor = ContextCompat.getColor(context, R.color.item_start_color)
    private val endColor = ContextCompat.getColor(context, R.color.item_end_color)


    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {

        super.onDraw(canvas)

        val radius = width.toFloat().coerceAtMost(height.toFloat()) / 2 - thinness / 2

        val gradient = RadialGradient(
            width.toFloat() / 2,
            height.toFloat() / 2,
            radius,
            startColor,
            endColor,
            Shader.TileMode.CLAMP
        )

        paint.shader = gradient
        //paint.color = endColor

        if (isCircle) {
            if (neededDrawBoarder) {
                canvas?.drawCircle(width.toFloat() / 2, height.toFloat() / 2, radius, paint)
            }
        } else {
            if (neededDrawBoarder) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    canvas?.drawRoundRect(
                        thinness / 2,
                        thinness / 2,
                        width.toFloat() - thinness / 2,
                        height.toFloat() - thinness / 2,
                        borderRadius,
                        borderRadius,
                        paint
                    )
                } else {
                    canvas?.drawRect(
                        thinness / 2,
                        thinness / 2,
                        width.toFloat() - thinness / 2,
                        height.toFloat() - thinness / 2,
                        paint
                    )
                }
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }
}