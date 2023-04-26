package com.rongtuoyouxuan.libuikit.widget

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.rongtuoyouxuan.libuikit.R
import com.rongtuoyouxuan.libuikit.widget.zbutton.*

class ZButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ZBaseButton(context, attrs, defStyleAttr), ZButtonRender {
    companion object {
        const val TYPE_LAYOUT_CENTER = 1
        const val TYPE_LAYOUT_LEFT = 2
    }

    var iconImageView: ImageView
    var textTextView: TextView

    private val attributes = Attributes()
    private val render: ZButtonRender

    init {
        val a = context.theme.obtainStyledAttributes(attrs, R.styleable.ZButton, 0, 0)
        attributes.readTextAppearance(context, a)
        a.recycle()

        View.inflate(
            getContext(), when (attributes.layoutType) {
                TYPE_LAYOUT_LEFT -> R.layout.pl_libutil_button_left
                else -> R.layout.pl_libutil_button_center
            }, this
        )
        iconImageView = findViewById(R.id.icon)
        textTextView = findViewById(R.id.text)

        render = when (attributes.layoutType) {
            TYPE_LAYOUT_LEFT -> LayoutLeftRender(context, iconImageView, textTextView, attributes)
            else -> DefaultRender(context, iconImageView, textTextView, attributes)
        }

        isEnabled = attributes.isEnabled
        if (attributes.textRes != 0) setTextResource(attributes.textRes) else setText(attributes.textStr)
        if (isEnabled) setNormalTextStyle(attributes.normalTextAppearance) else
            setDisableTextStyle(attributes.disableTextAppearance)
        setIcon(attributes.iconDrawable)
    }

    override fun setIcon(drawable: Drawable?) {
        render.setIcon(drawable)
    }

    override fun setIconResource(res: Int) {
        render.setIconResource(res)
    }

    override fun setText(text: CharSequence) {
        render.setText(text)
    }

    override fun setTextResource(res: Int) {
        render.setTextResource(res)
    }

    override fun setNormalTextStyle(normalTextStyle: Int) {
        render.setNormalTextStyle(normalTextStyle)
    }

    override fun setDisableTextStyle(disableTextStyle: Int) {
        render.setDisableTextStyle(disableTextStyle)
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        if (isEnabled) render.setNormalTextStyle(attributes.normalTextAppearance) else
            render.setDisableTextStyle(attributes.disableTextAppearance)
    }
}