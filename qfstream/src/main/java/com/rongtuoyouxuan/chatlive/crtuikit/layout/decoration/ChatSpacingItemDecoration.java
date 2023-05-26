package com.rongtuoyouxuan.chatlive.crtuikit.layout.decoration;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * @author ASnow
 * @date 2018/9/27
 * 文件   ChatLive
 */
public class ChatSpacingItemDecoration extends RecyclerView.ItemDecoration {

    private int spanCount;
    private int spacing;
    /**
     * @param spanCount  每行显示的个数
     * @param spacing  每个矩形之间的间距
     */
    public ChatSpacingItemDecoration(int spanCount, int spacing) {
        this.spanCount = spanCount;
        this.spacing = spacing;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        int position = parent.getChildAdapterPosition(view); // item position

        if (position >= 0) {
            int column = position % spanCount; // item column
            outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
            outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
            if (position >= spanCount) {
                outRect.top = spacing; // item top
            }
            if(parent.getChildPosition(view) == 0){
                outRect.top = spacing;
            }
        } else {
            outRect.left = 0;
            outRect.right = 0;
            outRect.top = 0;
            outRect.bottom = 0;
        }

    }
}