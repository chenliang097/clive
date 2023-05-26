package com.rongtuoyouxuan.chatlive.qfcommon.widget.level

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.rongtuoyouxuan.chatlive.stream.R

/**
 * 
 * date:2022/8/19-17:56
 * des: 个人资料卡-主播等级或者用户等级
 */
class UserCardLevelLayout : FrameLayout {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    private var viewBg: View? = null
    private var ivImage: ImageView? = null
    private var tvLevel: TextView? = null
    private var tvTitle: TextView? = null

    init {
        val layout = inflate(context, R.layout.layout_user_card_level, this)
        viewBg = layout.findViewById(R.id.viewBg)
        ivImage = layout.findViewById(R.id.ivImage)
        tvLevel = layout.findViewById(R.id.tvLevel)
        tvTitle = layout.findViewById(R.id.tvTitle)
    }
}