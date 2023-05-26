package com.rongtuoyouxuan.chatlive.crtuikit.layout.decoration;

import android.graphics.Rect;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.rongtuoyouxuan.chatlive.crtuikit.UiKitConfig;

/**
 * @author ASnow
 * @date 2018/9/27
 * 文件   ChatLive
 * 描述  设置GridView每个item间距的辅助类
 */
public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

    private int spanCount;
    private int spacing;
    private boolean includeEdge;
    private int headerNum;

    /**
     *  设置GridView每个item间距的辅助类
     * @param spanCount  每行显示的个数
     * @param spacing  每个矩形之间的间距
     * @param includeEdge  是否有边缘边距
     */
    public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
        this.spanCount = spanCount;
        this.spacing = spacing;
        this.includeEdge = includeEdge;
    }

    public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge, int headerNum) {
        this.spanCount = spanCount;
        this.spacing = spacing;
        this.includeEdge = includeEdge;
        this.headerNum = headerNum;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//        int position = parent.getChildAdapterPosition(view); // item page_position
//        int column = position % spanCount; // item column
//
//        if (includeEdge) {
//            outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
//            outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)
//
//            if (position < spanCount) { // top edge
//                outRect.top = spacing;
//            }
//            outRect.bottom = spacing; // item bottom
//        } else {
//            outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
//            outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
//            if (position >= spanCount) {
//                outRect.top = spacing; // item top
//            }
//        }


        int position = parent.getChildAdapterPosition(view) - headerNum; // item position

        if (position >= 0) {
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                if(UiKitConfig.INSTANCE.isArLanguage().invoke()){
                    outRect.left = spacing - (column + 1) * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                    outRect.right = column * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                }else{
                    outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                    outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                }
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
//                if(parent.getChildPosition(view) == 0){
//                    outRect.top = spacing;
//                }
            }
        } else {
            outRect.left = 0;
            outRect.right = 0;
            outRect.top = 0;
            outRect.bottom = 0;
        }

    }
}