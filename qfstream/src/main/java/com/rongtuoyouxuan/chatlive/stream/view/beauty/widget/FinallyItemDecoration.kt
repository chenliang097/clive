package com.rongtuoyouxuan.chatlive.stream.view.beauty.widget

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.*


class FinallyItemDecoration(private val heightPx : Int) : ItemDecoration() {


    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {

        parent.adapter?.apply{
            val childCount: Int = this.itemCount -1
            if(childCount == parent.getChildAdapterPosition(view)) {
                (parent.layoutManager as? LinearLayoutManager)?.let {
                    if (it.orientation == HORIZONTAL) {
                        outRect.right = heightPx
                    } else if (it.orientation == VERTICAL) {
                        outRect.bottom = heightPx
                    }
                }
            }
        }



    }

}