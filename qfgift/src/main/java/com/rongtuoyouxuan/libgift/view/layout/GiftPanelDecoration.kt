package com.rongtuoyouxuan.libgift.view.layout

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * 
 * date:2022/8/1-11:43
 * des: 礼物面板分割线
 */
class GiftPanelDecoration(val left: Int, val top: Int, val right: Int = 0, val bottom: Int = 0) :
    RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.set(left, top, right, bottom)
    }
}