package com.rongtuoyouxuan.libuikit.layout.decoration;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class VerticalItemDecoration extends RecyclerView.ItemDecoration {
    private int space;

    public VerticalItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
        int lastPosition = state.getItemCount() - 1;
        // Add top margin only for the first item to avoid double space between items
        if (parent.getChildPosition(view) == 0) {
            outRect.top = 0;
            outRect.bottom = space / 2;
        } else if (parent.getChildLayoutPosition(view) == lastPosition) {
            outRect.top = space / 2;
            outRect.bottom = 0;
        } else {
            outRect.top = space / 2;
            outRect.bottom = space / 2;
        }
    }
}
