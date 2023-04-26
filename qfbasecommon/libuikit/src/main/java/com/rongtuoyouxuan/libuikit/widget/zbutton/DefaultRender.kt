package com.rongtuoyouxuan.libuikit.widget.zbutton

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible

open class DefaultRender(
    open val context: Context,
    open val iconImageView: ImageView,
    open val textTextView: TextView,
    open val attributes: Attributes
) : ZButtonRender {
    override fun setIcon(drawable: Drawable?) {
        iconImageView.setImageDrawable(drawable)
        iconImageView.isVisible = drawable != null
        (iconImageView.layoutParams as? ViewGroup.MarginLayoutParams)?.apply {
            setMargins(this.leftMargin, this.topMargin, attributes.iconMarginEnd, this.bottomMargin)
            iconImageView.layoutParams = this
        }
    }

    override fun setIconResource(res: Int) {
        iconImageView.setImageResource(res)
    }

    override fun setText(text: CharSequence) {
        textTextView.text = text
    }

    override fun setTextResource(res: Int) {
        textTextView.setText(res)
    }

    override fun setNormalTextStyle(normalTextStyle: Int) {
        textTextView.setTextAppearance(context, normalTextStyle)
    }

    override fun setDisableTextStyle(disableTextStyle: Int) {
        textTextView.setTextAppearance(context, disableTextStyle)
    }
}