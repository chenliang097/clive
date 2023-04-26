package com.rongtuoyouxuan.chatlive.stream.view.layout

import android.content.Context
import android.view.Gravity
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatTextView
import com.rongtuoyouxuan.chatlive.stream.R
import com.rongtuoyouxuan.chatlive.util.UIUtils

class IdentifyInflateView(context: Context) : AppCompatTextView(context) {

    private fun init(context: Context) {
        gravity = Gravity.CENTER
        textSize = 11f
        setTextColor(context.resources.getColor(R.color.white))
        val padding = UIUtils.dip2px(context, 8f)
        setPadding(padding, 0, padding, 0)
    }

    fun setData(@DrawableRes res: Int, role: String?) {
        setBackgroundResource(res)
        text = role
    }

    fun setDataLeave(@DrawableRes res: Int, role: String?) {
        setData(res, role)
        val padding = UIUtils.dip2px(context, 8f)
        setPadding(padding, 0, padding, 0)
    }

    init {
        init(context)
    }
}