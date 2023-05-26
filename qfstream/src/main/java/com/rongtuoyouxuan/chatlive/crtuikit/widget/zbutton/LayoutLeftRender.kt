package com.rongtuoyouxuan.chatlive.crtuikit.widget.zbutton

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible

class LayoutLeftRender(
    override val context: Context,
    override val iconImageView: ImageView,
    override val textTextView: TextView,
    override val attributes: Attributes
) : DefaultRender(context, iconImageView, textTextView, attributes) {
    override fun setIcon(drawable: Drawable?) {
        iconImageView.setImageDrawable(drawable)
        iconImageView.isVisible = drawable != null
    }
}