package com.rongtuoyouxuan.qfcommon.widget.level

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.rongtuoyouxuan.qfcommon.R

class LeveUserCell(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {

    override fun onFinishInflate() {
        super.onFinishInflate()
        View.inflate(context, R.layout.cell_leve_user, this)
    }
}