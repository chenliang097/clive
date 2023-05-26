package com.rongtuoyouxuan.chatlive.crtuikit.widget.zbutton

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import com.rongtuoyouxuan.chatlive.crtuikit.dp
import com.rongtuoyouxuan.chatlive.crtuikit.widget.ZButton
import com.rongtuoyouxuan.chatlive.stream.R

class Attributes {
    var textStr: CharSequence = ""
    var textRes = 0
    var normalTextAppearance = 0
    var disableTextAppearance = 0
    var iconDrawable: Drawable? = null
    var isEnabled = true
    var iconMarginEnd = 0
    var layoutType = ZButton.TYPE_LAYOUT_CENTER

    fun readTextAppearance(context: Context, appearance: TypedArray) {
        val n: Int = appearance.indexCount
        for (i in 0 until n) {
            when (val attr = appearance.getIndex(i)) {
                R.styleable.ZButton_icon -> {
                    iconDrawable = appearance.getDrawable(attr)
                }
                R.styleable.ZButton_text -> {
                    textRes = appearance.getResourceId(attr, 0)
                    textStr = appearance.getText(attr) ?: ""
                }
                R.styleable.ZButton_normalTextStyle -> {
                    normalTextAppearance = appearance.getResourceId(attr, 0)
                }
                R.styleable.ZButton_disableTextStyle -> {
                    disableTextAppearance = appearance.getResourceId(attr, 0)
                }
                R.styleable.ZButton_enabled -> {
                    isEnabled = appearance.getBoolean(attr, true)
                }
                R.styleable.ZButton_iconMarginEnd -> {
                    iconMarginEnd = appearance.getDimensionPixelSize(attr, 6F.dp.toInt())
                }
                R.styleable.ZButton_layoutType -> {
                    layoutType = appearance.getInt(attr, ZButton.TYPE_LAYOUT_CENTER)
                }
            }
        }
    }
}