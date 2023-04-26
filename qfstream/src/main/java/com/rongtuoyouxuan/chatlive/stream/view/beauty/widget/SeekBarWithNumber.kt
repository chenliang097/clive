package com.rongtuoyouxuan.chatlive.stream.view.beauty.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import com.rongtuoyouxuan.chatlive.stream.R
import kotlin.math.ceil


/**
 * 带有数字和背景图的seekBar
 * author: qingyingliu
 * date: 3/11/21
 */
class SeekBarWithNumber @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : androidx.appcompat.widget.AppCompatSeekBar(context, attrs, defStyleAttr) {

    // SeekBar数值文字颜色
    private var textColor: Int = 0

    // 数值大小
    private var textSize: Float = 0.0f

    // 数据存储Key
    private var key: String = ""
    private lateinit var listener : OnSeekBarChangeListener;
    // 数值文字背景
    private lateinit var background: Bitmap

    // 背景宽度和高度
    private var bgWidth: Double = 0.0
    private var bgHeight: Double = 0.0

    // 文字方向
    private var orientation: Int = 0

    // 文字宽度
    private var textWidth: Float = 0.0f

    // 数值文字baseline的Y坐标
    private var textBaseLineY: Float = 0.0f

    //偏移大小
    private var offsetValue: Int = 0

    companion object {
        // 文字方向
        private const val ORIENTATION_TOP = 1
        private const val ORIENTATION_BOTTOM = 2
    }


    private val paint = Paint()

    init {
        val typedArray = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.SeekBarWithNumber,
            defStyleAttr,
            0
        )

        // 读取每个自定义属性
        val cnt = typedArray.indexCount
        for (i in 0 until cnt) {
            when (val index = typedArray.getIndex(i)) {
                R.styleable.SeekBarWithNumber_textColor -> textColor = typedArray.getColor(
                    index,
                    Color.WHITE
                )
                R.styleable.SeekBarWithNumber_key -> key = (typedArray.getString(index).toString())
                R.styleable.SeekBarWithNumber_textSize -> textSize =
                    typedArray.getDimension(index, 15f)
                R.styleable.SeekBarWithNumber_textBackground -> {
                    val backgroundResourceId =
                        typedArray.getResourceId(index, R.mipmap.icon_indicator)
                    background =
                        BitmapFactory.decodeResource(resources, backgroundResourceId).also {
                            bgWidth = it.width.toDouble()
                            bgHeight = it.height.toDouble()
                        }
                }
                R.styleable.SeekBarWithNumber_textOrientation -> orientation = typedArray.getInt(
                    index,
                    ORIENTATION_TOP
                )
            }
        }



        typedArray.recycle()
        //  progress = PreferenceUtil.instance?.getIntValue(key, 50)!!

        // 配置画笔
        paint.apply {
            isAntiAlias = true
            color = textColor
            textSize = this@SeekBarWithNumber.textSize
        }

        // 设置文字方向
        val halfBgWidth = ceil((bgWidth / 2)).toInt()
        val halfBgHeight = ceil(bgHeight).toInt() + 5
        if (orientation == ORIENTATION_TOP) {
            setPadding(
                halfBgWidth,
                halfBgHeight,
                halfBgWidth,
                0
            )
        } else { // bottom
            setPadding(
                halfBgWidth,
                0,
                halfBgWidth,
                halfBgHeight
            )
            background = rotateBitmap(background, 180f)
        }
//
//        super.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
//            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
//                listener.onProgressChanged(seekBar, progress, fromUser)
//            }
//
//            override fun onStartTrackingTouch(seekBar: SeekBar?) {
//                listener.onStartTrackingTouch(seekBar)
//            }
//
//            override fun onStopTrackingTouch(seekBar: SeekBar?) {
//                if (seekBar != null) {
//                    PreferenceUtil.instance!!.setIntValue(key, seekBar.progress)
//                }
//                listener.onStopTrackingTouch(seekBar)
//            }
//        })
    }

//
//    override fun setOnSeekBarChangeListener(l: OnSeekBarChangeListener?) {
//        if (l != null) {
//            listener = l
//        }
//    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        getTextLocation()

        val bgRect = progressDrawable.bounds
        val bgX: Double = bgRect.width() * (getProgress2() - offsetValue).toDouble() / max
        var bgY = 0.0

        if (orientation == ORIENTATION_BOTTOM) {
            bgY = bgRect.height().toDouble() + 6.0
        }

        val textX: Double = bgX + (bgWidth - textWidth) / 2
        val textY: Double = textBaseLineY + bgY + (0.16 * bgHeight / 2) - 10

        // 绘制文字和背景
        canvas?.apply {
            drawBitmap(background, bgX.toFloat(), bgY.toFloat(), paint)
            drawText((getProgress2() ).toString(), textX.toFloat(), textY.toFloat(), paint)
        }
    }

    // 计算SeekBar数值文字的显示位置
    private fun getTextLocation() {
        val fm = paint.fontMetrics
        val text = getProgress2().toString()
        textWidth = paint.measureText(text)

        textBaseLineY = (bgHeight / 2 - fm.descent + (fm.descent - fm.ascent) / 2).toFloat()
    }

    /**
     * 选择变换
     *
     * @param origin 原图
     * @param alpha  旋转角度，可正可负
     * @return 旋转后的图片
     */
    private fun rotateBitmap(origin: Bitmap, alpha: Float): Bitmap {
        val width = origin.width
        val height = origin.height
        val matrix = Matrix()
        matrix.setRotate(alpha)
        // 围绕原地进行旋转
        val newBM = Bitmap.createBitmap(origin, 0, 0, width, height, matrix, false)
        if (newBM == origin) {
            return newBM
        }
        origin.recycle()
        return newBM
    }




    override fun setProgress(progress:Int)
    {

        super.setProgress(progress - offsetValue)
    }

    override fun getProgress(): Int {

        return super.getProgress()
    }

    public fun getProgress2(): Int {
        return super.getProgress()+ offsetValue
    }

    fun setOffsetValue(offset  : Int)
    {
        offsetValue = offset
        postInvalidate()
    }

    fun getOffsetValue():Int
    {
        return offsetValue
    }

}