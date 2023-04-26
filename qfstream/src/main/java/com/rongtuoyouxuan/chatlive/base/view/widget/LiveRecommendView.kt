package com.rongtuoyouxuan.chatlive.base.view.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.rongtuoyouxuan.chatlive.stream.R

/*
*Create by {Mr秦} on 2023/1/29
*/
class LiveRecommendView(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {
    override fun onFinishInflate() {
        super.onFinishInflate()
        View.inflate(context, R.layout.layout_recomend, this)
    }

}