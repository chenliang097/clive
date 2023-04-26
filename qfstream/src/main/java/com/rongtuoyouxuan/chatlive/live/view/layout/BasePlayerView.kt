package com.rongtuoyouxuan.chatlive.live.view.layout

import android.content.Context
import android.util.AttributeSet
import android.view.TextureView

class BasePlayerView : TextureView {
    constructor(context: Context?) : super(context!!) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(
        context!!, attrs
    ) {
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context!!, attrs, defStyleAttr
    ) {
    }
}