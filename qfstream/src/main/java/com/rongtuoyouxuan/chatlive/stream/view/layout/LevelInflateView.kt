package com.rongtuoyouxuan.chatlive.stream.view.layout

import android.content.Context
import android.view.Gravity
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatTextView
import com.rongtuoyouxuan.chatlive.stream.R
import com.rongtuoyouxuan.chatlive.crtutil.util.UIUtils

class LevelInflateView(context: Context) : AppCompatTextView(context) {
    private fun init(context: Context) {
        gravity = Gravity.CENTER
        textSize = 9f
        setTextColor(context.resources.getColor(R.color.white))
        val padding = UIUtils.dip2px(context, 8f)
        setPadding(padding, 0, padding, 0)
    }

    fun setData(@DrawableRes res: Int, lv: String?) {
        setBackgroundResource(res)
        text = lv
    }

    fun setDataLeave(@DrawableRes res: Int, lv: String?) {
        setData(res, lv)
        val padding = UIUtils.dip2px(context, 7f)
        setPadding(padding * 2, 0, 0, 0)
    }

    init {
        init(context)
    }
}