package com.rongtuoyouxuan.chatlive.crtuikit.widget.zbutton

import android.graphics.drawable.Drawable

interface ZButtonRender {
    fun setIcon(drawable: Drawable?)

    fun setIconResource(res: Int)

    fun setText(text: CharSequence)

    fun setTextResource(res: Int)

    fun setNormalTextStyle(normalTextStyle: Int)

    fun setDisableTextStyle(disableTextStyle: Int)
}